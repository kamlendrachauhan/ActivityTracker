package com.human.activity.rest.model;


import javax.validation.constraints.NotNull;

public class TrainingAccelerationModel {

    @NotNull
    private String userID;

    @NotNull
    private String activity;

    @NotNull
    private AccelerationModel acceleration;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public AccelerationModel getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(AccelerationModel acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public String toString() {
        return "TrainingAccelerationModel{" +
                "userID='" + userID + '\'' +
                ", activity='" + activity + '\'' +
                ", acceleration=" + acceleration +
                '}';
    }
}
