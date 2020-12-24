package com.a1techandroid.tourguide.Models;

public class HistotyModel {

    String type;
    String name;
    String status;
    String date;
    String email;
    String userName;
    String userEmail;

    public HistotyModel(String type, String name, String status, String date, String email, String userName, String userEmail) {
        this.type = type;
        this.name = name;
        this.status = status;
        this.date = date;
        this.email = email;
        this.userName = userName;
        this.userEmail = userEmail;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
