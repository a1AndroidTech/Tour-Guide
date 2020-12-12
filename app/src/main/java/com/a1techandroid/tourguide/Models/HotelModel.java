package com.a1techandroid.tourguide.Models;

public class HotelModel {
    String arrival, destination, eco;
    int rating;

    public HotelModel(){

    }

    public HotelModel(String arrival, String destination, String eco, int rating) {
        this.arrival = arrival;
        this.destination = destination;
        this.eco = eco;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
