package com.thinkdo.entity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by xh on 15/5/10.
 */
public class ReferData implements Serializable, CopyProtocol<ReferData> {
    //车辆信息
    private String manId;
    private String manInfo;
    private String vehicleId;
    private String realYear;

    private String vehicleInfo;
    private String startYear;
    private String endYear;

    //参数
    private ValuesPair frontTotalToe;
    private ValuesPair leftFrontToe;
    private ValuesPair rightFrontToe;

    private ValuesPair leftFrontCamber;
    private ValuesPair rightFrontCamber;

    private ValuesPair leftCaster;
    private ValuesPair rightCaster;

    private ValuesPair leftKpi;
    private ValuesPair rightKpi;

    private ValuesPair rearTotalToe;
    private ValuesPair leftRearToe;
    private ValuesPair rightRearToe;

    private ValuesPair leftRearCamber;
    private ValuesPair rightRearCamber;

    //特殊参数
    private String maxThrust;

    private String wheelbase;
    private String frontWheel;
    private String rearWheel;

    public String getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(String wheelbase) {
        this.wheelbase = wheelbase;
    }

    @Override
    public ReferData copy() {
        ReferData copy = new ReferData();

        copy.setManId(manId);
        copy.setManInfo(manInfo);

        copy.setVehicleId(vehicleId);
        copy.setVehicleInfo(vehicleInfo);

        copy.setRealYear(realYear);
        copy.setStartYear(startYear);
        copy.setEndYear(endYear);

        copy.setMaxThrust(maxThrust);
        copy.setWheelbase(wheelbase);
        copy.setFrontWheel(frontWheel);
        copy.setRearWheel(rearWheel);


        copy.setFrontTotalToe(frontTotalToe.copy());
        copy.setLeftFrontToe(leftFrontToe.copy());
        copy.setRightFrontToe(rightFrontToe.copy());

        copy.setLeftFrontCamber(leftFrontCamber.copy());
        copy.setRightFrontCamber(rightFrontCamber.copy());

        copy.setLeftCaster(leftCaster.copy());
        copy.setRightCaster(rightCaster.copy());

        copy.setLeftKpi(leftKpi.copy());
        copy.setRightKpi(rightKpi.copy());

        copy.setRearTotalToe(rearTotalToe.copy());
        copy.setLeftRearToe(leftRearToe.copy());
        copy.setRightRearToe(rightRearToe.copy());

        copy.setLeftRearCamber(leftRearCamber.copy());
        copy.setRightRearCamber(rightRearCamber.copy());

        return copy;
    }

    public void unitConvert() {
        frontTotalToe.unitConvert(GloVariable.toeUnit);
        leftFrontToe.unitConvert(GloVariable.toeUnit);
        rightFrontToe.unitConvert(GloVariable.toeUnit);

        rearTotalToe.unitConvert(GloVariable.toeUnit);
        leftRearToe.unitConvert(GloVariable.toeUnit);
        rightRearToe.unitConvert(GloVariable.toeUnit);

        leftFrontCamber.unitConvert(GloVariable.unit);
        rightFrontCamber.unitConvert(GloVariable.unit);

        leftCaster.unitConvert(GloVariable.unit);
        rightCaster.unitConvert(GloVariable.unit);

        leftKpi.unitConvert(GloVariable.unit);
        rightKpi.unitConvert(GloVariable.unit);

        leftRearCamber.unitConvert(GloVariable.unit);
        rightRearCamber.unitConvert(GloVariable.unit);
    }

    public ValuesPair getFrontTotalToe() {
        return frontTotalToe;
    }

    public void setFrontTotalToe(ValuesPair frontTotalToe) {
        this.frontTotalToe = frontTotalToe;
    }

    public ValuesPair getLeftFrontToe() {
        return leftFrontToe;
    }

    public void setLeftFrontToe(ValuesPair leftFrontToe) {
        this.leftFrontToe = leftFrontToe;
    }

    public ValuesPair getRightFrontToe() {
        return rightFrontToe;
    }

    public void setRightFrontToe(ValuesPair rightFrontToe) {
        this.rightFrontToe = rightFrontToe;
    }

    public ValuesPair getLeftFrontCamber() {
        return leftFrontCamber;
    }

    public void setLeftFrontCamber(ValuesPair leftFrontCamber) {
        this.leftFrontCamber = leftFrontCamber;
    }

    public ValuesPair getRightFrontCamber() {
        return rightFrontCamber;
    }

    public void setRightFrontCamber(ValuesPair rightFrontCamber) {
        this.rightFrontCamber = rightFrontCamber;
    }

    public ValuesPair getLeftCaster() {
        return leftCaster;
    }

    public void setLeftCaster(ValuesPair leftCaster) {
        this.leftCaster = leftCaster;
    }

    public ValuesPair getRightCaster() {
        return rightCaster;
    }

    public void setRightCaster(ValuesPair rightCaster) {
        this.rightCaster = rightCaster;
    }

    public ValuesPair getLeftKpi() {
        return leftKpi;
    }

    public void setLeftKpi(ValuesPair leftKpi) {
        this.leftKpi = leftKpi;
    }

    public ValuesPair getRightKpi() {
        return rightKpi;
    }

    public void setRightKpi(ValuesPair rightKpi) {
        this.rightKpi = rightKpi;
    }

    public ValuesPair getRearTotalToe() {
        return rearTotalToe;
    }

    public void setRearTotalToe(ValuesPair rearTotalToe) {
        this.rearTotalToe = rearTotalToe;
    }

    public ValuesPair getLeftRearToe() {
        return leftRearToe;
    }

    public void setLeftRearToe(ValuesPair leftRearToe) {
        this.leftRearToe = leftRearToe;
    }

    public ValuesPair getRightRearToe() {
        return rightRearToe;
    }

    public void setRightRearToe(ValuesPair rightRearToe) {
        this.rightRearToe = rightRearToe;
    }

    public ValuesPair getLeftRearCamber() {
        return leftRearCamber;
    }

    public void setLeftRearCamber(ValuesPair leftRearCamber) {
        this.leftRearCamber = leftRearCamber;
    }

    public ValuesPair getRightRearCamber() {
        return rightRearCamber;
    }

    public void setRightRearCamber(ValuesPair rightRearCamber) {
        this.rightRearCamber = rightRearCamber;
    }

    public String getManId() {
        return manId;
    }

    public void setManId(String manId) {
        this.manId = manId;
    }

    public String getManInfo() {
        return manInfo;
    }

    public void setManInfo(String manInfo) {
        this.manInfo = manInfo;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getRealYear() {
        return realYear;
    }

    public void setRealYear(String realYear) {
        this.realYear = realYear;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getRearWheel() {
        return rearWheel;
    }

    public void setRearWheel(String rearWheel) {
        this.rearWheel = rearWheel;
    }

    public String getFrontWheel() {
        return frontWheel;
    }

    public void setFrontWheel(String frontWheel) {
        this.frontWheel = frontWheel;
    }

    public String getMaxThrust() {
        return maxThrust;
    }

    public void setMaxThrust(String maxThrust) {
        this.maxThrust = maxThrust;
    }

    public void combine(ReferData other) {
        if (other == null) return;

        if (other.getFrontTotalToe() != null) {
            this.frontTotalToe = other.getFrontTotalToe();
            this.leftFrontToe = other.getLeftFrontToe();
            this.rightFrontToe = other.getRightFrontToe();
        }

        if (other.getLeftFrontCamber() != null) {
            this.leftFrontCamber = other.getLeftFrontCamber();
            this.rightFrontCamber = other.getRightFrontCamber();
        }

        if (other.getRearTotalToe() != null) {
            this.rearTotalToe = other.getRearTotalToe();
            this.leftRearToe = other.getLeftRearToe();
            this.rightRearToe = other.getRightRearToe();
        }

        if (other.getLeftRearCamber() != null) {
            this.leftRearCamber = other.getLeftRearCamber();
            this.rightRearCamber = other.getRightRearCamber();
        }

        if (other.getLeftKpi() != null) {
            this.leftKpi = other.getLeftKpi();
            this.rightKpi = other.getRightKpi();
        }

        if (other.getRightCaster() != null) {
            this.leftCaster = other.getLeftCaster();
            this.rightCaster = other.getRightCaster();
        }
    }

}
