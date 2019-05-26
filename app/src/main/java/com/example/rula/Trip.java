package com.example.rula;


public class Trip {

    private String key;
    private String name;
    private Location location;
    private String difficulty;
    private String date;
    private String maxPeople;
    private String nBookings;
    private String available;


    public Trip(String key){
        this.key = key;
        this.name = "";
        this.difficulty = "";
        this.location = new Location();
        this.date = "";
        this.maxPeople = "0";
        this.nBookings = "0";
        this.available = "0";

    }

    public Trip(String key, String name, Location location, String difficulty, String date, String maxPeople, String nBookings){
        this.key = key;
        this.name = name;
        this.location = location;
        this.difficulty = difficulty;
        this.date = date;
        this.maxPeople = maxPeople;
        this.nBookings = nBookings;
        this.available = Long.toString(Long.parseLong(maxPeople)- Long.parseLong(nBookings));
    }

    public String getKey(){ return this.key; }
    public String getName(){
        return this.name;
    }
    public String getLatitude(){
        return this.location.getLatitude();
    }
    public String getLongitude(){
        return this.location.getLongitude();
    }
    public String getLocationTag() { return this.location.getTag(); }
    public String getDifficulty(){
        return this.difficulty;
    }
    public String getDate(){
        return this.date;
    }
    public String getAvailable() { return this.available; }
    public String getMaxPeople() { return this.maxPeople; }
    public String getNumberBookings(){ return this.nBookings; }



    public void setName(String name) { this.name = name; }
    public void setLocationTag(String tag){
        this.location.setTag(tag);
    }
    public void setLongitude(String longitude){this.location.setLatitude(longitude);}
    public void setLatitude(String latitude){this.location.setLatitude(latitude);}
    public void setDifficulty(String difficulty){ this.difficulty = difficulty; }
    public void setDate(String date){ this.date =  date;}
    public void setMaxPeople(String maxPeople){
        this.maxPeople = maxPeople;
        if(nBookings.equals("")) this.available = this.maxPeople;
        else this.available = Long.toString(Long.parseLong(maxPeople)- Long.parseLong(nBookings));

    }
    public void setNumberBookings(String nBookings){
        this.nBookings = nBookings;
        if(maxPeople.equals("")) this.available = "0";
        else this.available = Long.toString(Long.parseLong(maxPeople)- Long.parseLong(nBookings));
    }
}
