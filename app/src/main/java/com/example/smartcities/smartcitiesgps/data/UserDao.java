package com.example.smartcities.smartcitiesgps.data;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
//THIS CLASS CONTAINS THE DAO THAT DEFINES QUERY AND CONVENIENCE METHODS FOR THE ROOM DATABASE

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert()
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);


}
