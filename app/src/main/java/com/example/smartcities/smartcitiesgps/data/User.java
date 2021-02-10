package com.example.smartcities.smartcitiesgps.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
//THIS CLASS CONTAINS COLUMNS & THEIR GETTERS AND SETTERS FOR THE DATABASE TABLE

@Entity
public class User {
    //Generating an id for the entry
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Creating each of the columns
    @ColumnInfo(name = "latitude")
    private double lat;

    @ColumnInfo(name = "longitude")
    private double lon;

    @ColumnInfo(name = "timestamp")
    private String ts;

    @ColumnInfo(name = "group_id")
    private String gid;

    @ColumnInfo(name = "accuracy")
    private double acc;

    @ColumnInfo(name = "heading")
    private double head;

    @ColumnInfo(name = "speed")
    private double speed;

    @ColumnInfo(name = "phone_id")
    private String phone_id;

    //Creating getters and setters for each column value
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public double getAcc() {
        return acc;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public double getHead() {
        return head;
    }

    public void setHead(double head) {
        this.head = head;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getPhone_id() { return phone_id; }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }
}
