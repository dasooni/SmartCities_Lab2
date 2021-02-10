package com.example.smartcities.smartcitiesgps.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
//THIS CLASS IS TO INITIALIZE OUR ROOM DATABASE EXTENDING THE ROOM DATABASE CLASS

@Database(entities = {User.class}, version = 8)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
