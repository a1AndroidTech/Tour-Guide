package com.a1techandroid.tourguide.Models;

public class VisitPlaces {

    String detail;
    String imageUrl;


    public VisitPlaces(){

    }
    public VisitPlaces(String detail, String imageUrl) {
        this.detail = detail;
        this.imageUrl = imageUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
