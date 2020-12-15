package com.a1techandroid.tourguide.Models;

import android.graphics.drawable.Drawable;

public class CityModel {

    String name, detail;
    Drawable img;


    public CityModel(String name, Drawable img, String detail) {
        this.name = name;
        this.img = img;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
