package com.example.rula;


public class Trip {
    private String latitude;
    private String longitude;
    private String name;
    private String difficulty;
    private String date;
    private String maxPeople;

    public Trip(){

    }

    public Trip(String name, String latitude, String longitude, String difficulty, String date, String maxPeople){
        this.name = name;
        this.latitude = latitude;
        this.difficulty = difficulty;
        this.date = date;
        this.maxPeople = maxPeople;
        this.longitude = longitude;
    }

    public String getName(){
        return this.name;
    }

    public String getLatitude(){
        return this.latitude;
    }


    public String getLongitude(){
        return this.longitude;
    }


    public String getDifficulty(){
        return this.difficulty;
    }

    public String getDate(){
        return this.date;
    }

    public String getMaxPeople() { return this.maxPeople; }
}
