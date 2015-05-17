package com.thinkdo.entity;

import java.io.Serializable;

/**
 * Created by xh on 15/5/14.
 */
public class LevelParam implements Serializable {
    private String vehicleId;

    private ValuesPair frontLevel;
    private ValuesPair rearLevel;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public ValuesPair getFrontLevel() {
        return frontLevel;
    }

    public void setFrontLevel(ValuesPair frontLevel) {
        this.frontLevel = frontLevel;
    }

    public ValuesPair getRearLevel() {
        return rearLevel;
    }

    public void setRearLevel(ValuesPair rearLevel) {
        this.rearLevel = rearLevel;
    }
}
