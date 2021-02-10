package com.example.smartcities.smartcitiesgps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CrystalRangeSeekbar mSeekBarDate; //http://codingsignals.com/crystal-range-seekbar-in-android/
    private CrystalSeekbar mSeekBarTimeLength, mSeekBarTime, mSeekBarTransparency;
    private Spinner mModeSpinner;
    private CheckBox mCheckBox;
    private TextView tvMinTime, tvMaxTime, tvTransparency, tvMinDate, tvMaxDate, tvMaxDates;
    private LocationDbHelper mDbHelper; //Database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Initialize database helper
        mDbHelper = new LocationDbHelper(this);

        mModeSpinner = (Spinner) findViewById(R.id.spinner);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mSeekBarTime = (CrystalSeekbar) findViewById(R.id.rangeSeekbarTime);
        mSeekBarDate = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbarDate);
        mSeekBarTimeLength = (CrystalSeekbar) findViewById(R.id.rangeSeekbarTimeLength);
        mSeekBarTransparency = (CrystalSeekbar) findViewById(R.id.rangeSeekbarTransparency);
        tvMinTime = (TextView) findViewById(R.id.textViewMinTime);
        tvMaxTime = (TextView) findViewById(R.id.textViewMaxTime);
        tvMinDate = (TextView) findViewById(R.id.textViewMinDate);
        tvMaxDate = (TextView) findViewById(R.id.textViewMaxDate);
        tvMaxDates = (TextView) findViewById(R.id.textViewMaxDates);
        tvTransparency = (TextView) findViewById(R.id.textViewTransparency);

        mSeekBarTime.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                //TODO
            }
        });

        mSeekBarDate.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                //TODO
            }
        });

        mSeekBarTimeLength.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                //TODO
            }
        });

        mSeekBarTransparency.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                //TODO
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO
            }
        });

        mModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //TODO
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng norrkoping = new LatLng(58.5892513, 16.1832126);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(norrkoping, 15.0f));
    }
}
