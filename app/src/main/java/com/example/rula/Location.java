package com.example.rula;

public class Location {
    private String latitude;
    private String longitude;
    private String tag;

    public Location(){
        this.latitude = "";
        this.longitude = "";
        this.tag = "";
    }
    public Location(String latitude, String longitude, String tag){
        this.latitude = latitude;
        this.longitude = longitude;
        this.tag = tag;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public void setLatitude(String latitude){this.latitude = latitude; }

    public String getLongitude(){
        return this.longitude;
    }

    public void setLongitude(String longitude){ this.longitude = longitude; }

    public String getTag(){
        return this.tag;
    }

    public void setTag(String tag){ this.tag = tag; }
}
