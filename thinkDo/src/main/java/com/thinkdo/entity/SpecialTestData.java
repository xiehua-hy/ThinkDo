package com.thinkdo.entity;


import com.thinkdo.application.MainApplication;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/7/1.
 */
public class SpecialTestData {
    private String wheelbase_diff;
    private String rearWheel;
    private String frontWheel;
    private String axleOffset;
    private String leftWheelbase;
    private String wheel_diff;
    private String rightWheelbase;

    public void addRealData(String test) {
        if (test == null) return;
        if (test.contains("&&")) {
            String[] array = test.split("&&");
            String[] data = array[1].split("\\|");

            if (data.length == 7) {
                for (int i = 0; i < data.length; i++) {
                    try {
                        String style;
                        if (i == 3) style = "0.00";
                        else style = "0";

                        data[i] = new DecimalFormat(style).format(Float.parseFloat(data[i]));
                    } catch (NumberFormatException e) {
                        data[i] = MainApplication.initValue;
                    }
                }

                setWheel_diff(data[0]);          //轮距差

                setRearWheel(data[1]);    //后轮距
                setFrontWheel(data[2]);          //前轮距
                setAxleOffset(data[3]);     //轴偏位
                setLeftWheelbase(data[4]);//左轴距

                setWheelbase_diff(data[5]);       //轴距差
                setRightWheelbase(data[6]);//右轴距
            }
        }
    }

    public String getLeftWheelbase() {
        return leftWheelbase;
    }

    public void setLeftWheelbase(String leftWheelbase) {
        this.leftWheelbase = leftWheelbase;
    }

    public String getWheelbase_diff() {
        return wheelbase_diff;
    }

    public void setWheelbase_diff(String wheelbase_diff) {
        this.wheelbase_diff = wheelbase_diff;
    }

    public String getRearWheel() {
        return rearWheel;
    }

    public void setRearWheel(String rearWheel) {
        this.rearWheel = rearWheel;
    }

    public String getAxleOffset() {
        return axleOffset;
    }

    public void setAxleOffset(String axleOffset) {
        this.axleOffset = axleOffset;
    }

    public String getRightWheelbase() {
        return rightWheelbase;
    }

    public void setRightWheelbase(String rightWheelbase) {
        this.rightWheelbase = rightWheelbase;
    }

    public String getWheel_diff() {
        return wheel_diff;
    }

    public void setWheel_diff(String wheel_diff) {
        this.wheel_diff = wheel_diff;
    }

    public String getFrontWheel() {
        return frontWheel;
    }


    public void setFrontWheel(String frontWheel) {
        this.frontWheel = frontWheel;
    }

    public void unitConvert() {
        axleOffset = unitConvert(axleOffset, MainApplication.unit);
    }

    public String unitConvert(String angle, UnitEnum unit) {
        if (angle == null || unit == null) return null;
        if (angle.equals(MainApplication.initValue)) return angle;
        float val;
        try {
            val = Float.parseFloat(angle);
        } catch (NumberFormatException e) {
            return null;
        }

        switch (unit) {
            case degreeSecond:
                return degreeToMinu(val);
            default:
                return angle + "°";
        }
    }

    protected String degreeToMinu(float angle) {
        StringBuilder str = new StringBuilder(10);
        if (angle < 0) str.append("-");

        angle = 60 * Math.abs(angle);
        int i = (int) (angle / 60);
        str.append(String.format("%d°", i));
        i = Math.round(angle - 60 * i);
        if (i < 10) str.append("0");
        return str.append(String.format("%d'", i)).toString();
    }
}
