package com.example.smartcities.smartcitiesgps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LocationDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Location.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LocationContract.Location.TABLE_NAME + " (" +
                    LocationContract.Location._ID + INT_TYPE + " PRIMARY KEY," +
                    LocationContract.Location.COLUMN_NAME_TIMESTAMP + INT_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_GROUP_ID + INT_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_LONGITUDE + DOUBLE_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_LATITUDE + DOUBLE_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_ACCURACY + DOUBLE_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_HEADING + DOUBLE_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_SPEED + DOUBLE_TYPE + COMMA_SEP +
                    LocationContract.Location.COLUMN_NAME_PHONE_ID + TEXT_TYPE + " )";

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
    public static final String[] projection = {
            LocationContract.Location._ID, //0
            LocationContract.Location.COLUMN_NAME_TIMESTAMP, //1
            LocationContract.Location.COLUMN_NAME_PHONE_ID, //2
            LocationContract.Location.COLUMN_NAME_GROUP_ID, //3
            LocationContract.Location.COLUMN_NAME_LONGITUDE, //4
            LocationContract.Location.COLUMN_NAME_LATITUDE, //5
            LocationContract.Location.COLUMN_NAME_ACCURACY, //6
            LocationContract.Location.COLUMN_NAME_HEADING, //7
            LocationContract.Location.COLUMN_NAME_SPEED, //8
            //Last column is date (example: 2016-11-25)
            "strftime('%Y-%m-%d', " + LocationContract.Location.COLUMN_NAME_TIMESTAMP + " / 1000, 'unixepoch')" //9
    };

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LocationContract.Location.TABLE_NAME;

    public LocationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public long insertData(String lat) {
        //get writeable database as we are inserting
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LocationContract.Location.COLUMN_NAME_LATITUDE, lat);

        long id = db.insert(LocationContract.Location.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    //READ INFORMATION FROM THE DATABASE
    public Cursor queryDatabase(String[] projection, String selection, String[] selectionArgs,
                                String sortOrder, String group) {
        //get readable database as we are not inserting anything
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(
                LocationContract.Location.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                group,                                     // Group by
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return c;
    }

    public List ListItems() {
        List itemIDs = new ArrayList<>();
        while(queryDatabase(projection,null,null,null,null).moveToNext()) {
            long itemID = queryDatabase(projection,null, null, null,null).getColumnIndexOrThrow(LocationContract.Location.COLUMN_NAME_LATITUDE);
            itemIDs.add(itemID);
        }

        return itemIDs;
    }
}