package com.example.smartcities.smartcitiesgps;

import android.provider.BaseColumns;

public final class LocationContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private LocationContract() {}

    /* Inner class that defines the table contents */
    //Do not change anything here
    public static class Location implements BaseColumns {
        public static final String TABLE_NAME = "location";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_PHONE_ID = "phone_id";
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_ACCURACY = "accuracy";
        public static final String COLUMN_NAME_HEADING = "heading";
        public static final String COLUMN_NAME_SPEED = "speed";
    }
}
