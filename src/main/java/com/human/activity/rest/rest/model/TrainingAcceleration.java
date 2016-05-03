package com.human.activity.rest.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import javax.validation.constraints.NotNull;

@Table
public class TrainingAcceleration {

    @PrimaryKey
    @NotNull
    private UserActivityTimestamp userActivityTimestamp;

    @Column
    @NotNull
    private Double x;

    @Column
    @NotNull
    private Double y;

    @Column
    @NotNull
    private Double z;

    public TrainingAcceleration() {
        //Default constructor
    }

    public TrainingAcceleration (TrainingAccelerationModel model){
        this.userActivityTimestamp = new UserActivityTimestamp(model.getUserID(),model.getActivity(),model.getAcceleration().getTimestamp());
        this.x = model.getAcceleration().getX();
        this.y = model.getAcceleration().getY();
        this.z = model.getAcceleration().getZ();
    }

    public UserActivityTimestamp getUserActivityTimestamp() {
        return userActivityTimestamp;
    }

    public void setUserActivityTimestamp(UserActivityTimestamp userActivityTimestamp) {
        this.userActivityTimestamp = userActivityTimestamp;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }
}
