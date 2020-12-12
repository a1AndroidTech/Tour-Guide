package com.a1techandroid.tourguide.Models;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class GiftModel {

    String arrival, destination, eco;
//    Drawable img;

    public GiftModel(){
        
    }

    public GiftModel(String arrival, String destination, String eco) {
        this.arrival = arrival;
        this.destination = destination;
        this.eco = eco;
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


}
