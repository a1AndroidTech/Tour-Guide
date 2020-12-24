package com.a1techandroid.tourguide.Models;

public class BookingHotel {
    String email;
    String type;
    String name;
    String location;
    String date;
    String status;
    String userName;
    String userPhone;
    String numOFRooms;
    String familyType;

    public BookingHotel(){

    }


    public BookingHotel(String email, String type, String name, String location, String date, String status, String userName, String userPhone, String numOFRooms, String familyType) {
        this.email = email;
        this.type = type;
        this.name = name;
        this.location = location;
        this.date = date;
        this.status = status;
        this.userName = userName;
        this.userPhone = userPhone;
        this.numOFRooms = numOFRooms;
        this.familyType = familyType;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getNumOFRooms() {
        return numOFRooms;
    }

    public void setNumOFRooms(String numOFRooms) {
        this.numOFRooms = numOFRooms;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
