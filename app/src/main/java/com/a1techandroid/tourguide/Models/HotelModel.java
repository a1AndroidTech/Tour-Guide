package com.a1techandroid.tourguide.Models;

public class HotelModel {
    String arrival, destination, eco, email;
    int rating;


    public HotelModel(){

    }

    public HotelModel(String email, String arrival, String destination, String eco, int rating) {
        this.arrival = arrival;
        this.destination = destination;
        this.eco = eco;
        this.rating = rating;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
