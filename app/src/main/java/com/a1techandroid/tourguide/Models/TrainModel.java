package com.a1techandroid.tourguide.Models;

public class TrainModel {

    String trainName;
    String endPoints;

    public TrainModel(String trainName, String endPoints) {
        this.trainName = trainName;
        this.endPoints = endPoints;
    }

    public TrainModel() {
    }


    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(String endPoints) {
        this.endPoints = endPoints;
    }
}
