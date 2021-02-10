package com.example.smartcities.smartcitiesgps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import room library
import androidx.room.Room;
//import database classes
import com.example.smartcities.smartcitiesgps.data.User;
import com.example.smartcities.smartcitiesgps.data.UserDao;
import com.example.smartcities.smartcitiesgps.data.UserDatabase;

//import date and timestamp library
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //group name
    private final static String GROUP_ID = "Ludwig_Dasmit";
    //locationmanager, -listener, and location variables
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;
    //buttons
    Button GPSButton;
    Button mapsButton;
    //textviews
    TextView TextLon, TextLat, TextSpeed, TextHead, TextAccu, TextData;
    //switch for stop and start gps
    Boolean Flag = true;
    //values for gps information
    double latitude, longitude;
    float speed, accuracy, heading;
    //variables for time and distance update intervals
    private static final long MIN_DIST_UPDATE_REQUEST = 1; // meter
    private static final long TIME_BETWEEN_UPDATES = 2000; // ms
    //tag for logging activities
    private static final String TAG = "Activity";

    //Wakelock - Makes sure phone keeps sending data when screen is off
    PowerManager.WakeLock wakeLock;

    //initializing user, which corresponds to a row in the table
    User user = new User();
    private volatile boolean threadStopped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GPSButton = (Button) findViewById(R.id.GPSButton);
        mapsButton = (Button) findViewById(R.id.mapsButton);
        TextLon = (TextView) findViewById(R.id.TextLon);
        TextLat = (TextView) findViewById(R.id.TextLat);
        TextSpeed = (TextView) findViewById(R.id.TextSpeed);
        TextHead = (TextView) findViewById(R.id.TextHeading);
        TextAccu = (TextView) findViewById(R.id.TextAccuracy);
        TextData = (TextView) findViewById(R.id.dataDisplay);

        TextLon.setText("Longitude : " + longitude);
        TextLat.setText("Latitude : " + latitude);
        TextSpeed.setText("Speed : " + speed);
        TextHead.setText("Heading : " + heading);
        TextAccu.setText("Accuracy : " + accuracy);
        //make a new textview to display database, for testing & verification

        // show location button click event
        GPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                toggleGpsListener();
            }
        });

        //Open maps activity
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });


    }

    //toggle between gps active or inactive
    private void toggleGpsListener() {
        if (Flag) {
            //Wakelock
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");
            //Makes sure that there is an active wakelock
            if (!wakeLock.isHeld())
                wakeLock.acquire();

            //changes button text
            GPSButton.setText("STOP GPS");
            Flag = false;

            //starts thread and logs it
            threadStopped = false;
            thread = new Thread(thread);
            thread.start();
            Log.i(TAG, "Started background thread");

            //initialize location manager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //initialize location listener
            locationListener = new LocationListener() {
                @Override
                //on location change, update values, textviews and database insertion
                public void onLocationChanged(Location location) {
                    //update values
                    user = new User();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    speed = location.getSpeed();
                    accuracy = location.getAccuracy();
                    heading = location.getBearing();

                    //update textviews
                    TextLat.setText("Latitude : " + latitude);
                    TextLon.setText("Longitude : " + longitude);
                    TextSpeed.setText("Speed : " + speed);
                    TextHead.setText("Heading : " + heading);
                    TextAccu.setText("Accuracy : " + accuracy);

                    //set values in the instance of user
                    user.setGid(GROUP_ID); //group id
                    user.setLat(latitude); //latitude
                    user.setLon(longitude); //longitude
                    user.setAcc(accuracy); //accuracy
                    user.setHead(heading); //heading
                    user.setSpeed(speed); //speed
                    user.setPhone_id("Dasmit"); //name of uploader
                    user.setTs(getTimeStamp()); //get current timestamp

                    //log on every change
                    Log.i(TAG, "Listener position updated");
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                    Log.i(TAG, "GPS provider enabled");
                }

                @Override
                public void onProviderDisabled(String s) {
                    Log.i(TAG, "GPS provider disabled");
                }
            };
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //get last known location, gps provider
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //request location updates based on location listener, with given time/distance update
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIME_BETWEEN_UPDATES
                    , MIN_DIST_UPDATE_REQUEST, locationListener);

        } else {
            //Change the flag, button text and release the wakeLock
            Flag = true;
            GPSButton.setText("START GPS");
            if(wakeLock.isHeld())
                wakeLock.release();

            //Stop GPS-listener
            locationManager.removeUpdates(locationListener); //stop updating position
            threadStopped = true; //stop inserting to database
            thread.interrupt();
            TextData.removeCallbacks(thread); //stop updating textview
            Log.i(TAG, "Stopped listener on user request"); // log it
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //release wakeLock
        if(wakeLock.isHeld())
            wakeLock.release();

        //Stop GPS-listener
        locationManager.removeUpdates(locationListener); //stop updating position
        threadStopped = true; //stop inserting
        thread.interrupt(); //stop thread on destroy
        TextData.removeCallbacks(thread); // stop updating
        Log.i(TAG, "Stopped listener on destroy"); //log it
    }

    //A separate thread used to handle the database queries
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
                //build database
                final UserDatabase db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class,
                        "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                //instance of database dao
                final UserDao userDao = db.userDao();
                //loop
                while(!threadStopped) {
                    //check values on latitude, as we want to insert relevant gps-information to the database only
                    if(latitude != 0) {
                        //insert data row, together with the information on locationchanged()
                        db.userDao().insert(user);
                        Log.i(TAG, "Inserted table into the database");

                        //runOnUIthread to update TextData textview through thread
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(userDao.getAll().size() > 0) {
                                        //display what information is being inserted into the database
                                        TextData.setText("DATABASE LOG" + "\n" + "\n"
                                                + "Latitude insert:  "
                                                + userDao.getAll().get(userDao.getAll().size() - 1).getLat()
                                                + "\n" + "Longitude insert:  "
                                                + userDao.getAll().get(userDao.getAll().size() - 1).getLon()
                                                + "\n" + "Number of tables:  "
                                                + userDao.getAll().size());
                                    }
                                }
                            });
                            //sleep for 20 seconds
                            Thread.sleep(20000);
                            //catch thread interrupt and log it
                        } catch (InterruptedException e) {
                            Log.e(TAG, "Thread interrupt on sleep");
                        }
                    }
                }
        }
    });

    //make a timestamp instance for the database
    //this way every time timestamp is requested, current timestamp is received
    private String getTimeStamp() {

        Date date = new Date();
        long tms = date.getTime();
        Timestamp ts = new Timestamp(tms);

        return ts.toString();
    }
}

