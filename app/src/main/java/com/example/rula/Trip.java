package com.example.rula;

public class Trip {
    private String location;
    private String name;
    private Integer difficulty;
    private String date;
    private Integer maxPeople;
//test
    public Trip(){

    }

    public Trip(String name, String location, Integer difficulty, String date, Integer maxPeople){
        this.name = name;
        this.location = location;
        this.difficulty = difficulty;
        this.date = date;
        this.maxPeople = maxPeople;
    }

    public String getName(){
        return this.name;
    }

    public String getLocation(){
        return this.location;
    }

    public Integer getDifficulty(){
        return this.difficulty;
    }

    public String getDate(){
        return this.date;
    }

    public Integer getMaxPeople() { return this.maxPeople; }
}
