package com.a1techandroid.tourguide.Models;

public class HistotyModel {

    String type;
    String name;
    String status;
    String date;
    String email;

    public HistotyModel(){

    }

    public HistotyModel(String type, String name, String status, String date, String email) {
        this.type = type;
        this.name = name;
        this.status = status;
        this.date = date;
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
