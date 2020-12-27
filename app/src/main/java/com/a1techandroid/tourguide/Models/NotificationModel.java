package com.a1techandroid.tourguide.Models;

public class NotificationModel {
    String userId;
    String status;
    String type;
    String email;

    public NotificationModel(String userId, String status, String type, String email) {
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.email = email;
    }

    public NotificationModel(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
