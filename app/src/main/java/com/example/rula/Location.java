package com.example.rula;

public class Location {
    private String latitude;
    private String longitude;
    private String tag;

    public Location(String latitude, String longitude, String tag){
        this.latitude = latitude;
        this.longitude = longitude;
        this.tag = tag;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public String getLongitude(){
        return this.longitude;
    }

    public String getTag(){
        return this.tag;
    }
}
