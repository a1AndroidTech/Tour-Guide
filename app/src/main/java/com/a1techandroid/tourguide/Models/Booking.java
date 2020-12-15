package com.a1techandroid.tourguide.Models;

public class Booking {
    String type;
    String name;
    String location;
    String date;
    String status;


    public Booking(String type, String name, String location, String date, String status) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public Booking(){

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
