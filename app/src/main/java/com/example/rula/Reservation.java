package com.example.rula;

public class Reservation {

    private String name;
    private String email;
    private String phone;
    private String numOfPeople;

    public Reservation () {}

    public Reservation(String name, String email, String phone, String numOfPeople) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.numOfPeople = numOfPeople;
    }

    public String getName() { return this.name; }

    public String getEmail() { return this.email; }

    public String getPhone() { return this.phone; }

    public String getNumOfPeople() { return this.numOfPeople; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setNumOfPeople(String numOfPeople) { this.numOfPeople = numOfPeople; }

}