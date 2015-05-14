package com.thinkdo.entity;

import java.io.Serializable;

public class WeightParam implements Serializable {
    private String LeftFront;
    private String RightFront;
    private String RearSeat;
    private String LeftRear;
    private String RightRear;
    private String Trunk;

    public String getRearSeat() {
        return RearSeat;
    }

    public void setRearSeat(String rearSeat) {
        RearSeat = rearSeat;
    }

    public String getLeftRear() {
        return LeftRear;
    }

    public void setLeftRear(String leftRear) {
        LeftRear = leftRear;
    }

    public String getLeftFront() {
        return LeftFront;
    }

    public void setLeftFront(String leftFront) {
        LeftFront = leftFront;
    }

    public String getRightRear() {
        return RightRear;
    }

    public void setRightRear(String rightRear) {
        RightRear = rightRear;
    }

    public String getRightFront() {
        return RightFront;
    }

    public void setRightFront(String rightFront) {
        RightFront = rightFront;
    }

    public String getTrunk() {
        return Trunk;
    }

    public void setTrunk(String trunk) {
        Trunk = trunk;
    }


}
