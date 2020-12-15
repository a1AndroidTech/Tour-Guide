package com.a1techandroid.tourguide.Models;

public class PlaneModel {

    String arrival, destination, eco, gold, email;

    public PlaneModel(){

    }

    public PlaneModel(String arrival, String destination, String eco, String gold, String email1) {
        this.arrival = arrival;
        this.destination = destination;
        this.eco = eco;
        this.gold = gold;
        this.email = email1;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
