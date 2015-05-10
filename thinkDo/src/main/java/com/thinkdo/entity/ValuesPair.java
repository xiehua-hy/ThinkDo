package com.thinkdo.entity;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by xh on 15/5/10.
 */
public class ValuesPair implements CopyProtocol<ValuesPair>, UnitConvertProtocol {
    private String min;
    private String mid;
    private String max;

    private String real;

    public ValuesPair() {
        init();
    }

    public ValuesPair(String min, String mid, String max) {
        this.min = min;
        this.mid = mid;
        this.max = max;
    }

    /**
     * @param fmin 上偏差
     * @param fmid 标准值
     * @param fmax 下偏差
     */
    public ValuesPair(float fmin, float fmid, float fmax) {
        String smid = format(fmid);
        if (isInitValue(smid)) {
            init();
            return;
        }
        mid = smid;
        if (isInitValue(format(fmin))) {
            min = mid;
            max = mid;
        } else {
            min = format(fmid - fmin);
            max = format(fmid + fmax);
        }
    }

    /**
     * 由总前束生成前束角
     */
    public ValuesPair(ValuesPair totalToe) {
        if (totalToe.getMid().equals(GloVariable.initValue)) {
            init();
        } else {
            min = format(Float.parseFloat(totalToe.getMin()) / 2);
            mid = format(Float.parseFloat(totalToe.getMid()) / 2);
            max = format(Float.parseFloat(totalToe.getMax()) / 2);
        }
    }

    private void init() {
        this.min = GloVariable.initValue;
        this.mid = GloVariable.initValue;
        this.max = GloVariable.initValue;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    @Override
    public ValuesPair copy() {
        return new ValuesPair(min, mid, max);
    }

    @Override
    public void unitConvert(UnitEnum unit) {
        min = unitConvert(min, unit);
        mid = unitConvert(mid, unit);
        max = unitConvert(max, unit);
    }

    public boolean isInitValue(String value) {
        return value.equals(GloVariable.initValue);
    }

    private String format(float value) {
        return new DecimalFormat("0.00").format(value);
    }

    /**
     * 百分度 转 其他单位
     *
     * @param angle 百分度的数值
     * @param unit  转换的目标单位
     * @return String
     */
    private String unitConvert(String angle, UnitEnum unit) {
        float val = Float.parseFloat(angle);

        switch (unit) {
            case degreeSecond:
                return degreeToMinu(val);
            case degree:
                return angle + "°";
            case mm:
                return String.format(Locale.getDefault(), "%.2f", toeDegreeToWidth(val, 500.26f)) + "m";
            case inch:
                return String.format(Locale.getDefault(), "%.2f", toeDegreeToWidth(val, 19.70f)) + "i";
            default:
                return angle + "°";
        }
    }

    /**
     * 度 转 长度
     *
     * @param angle          百分度的数值
     * @param fWheelDiameter 转换因子
     * @return mm or inch
     */
    private float toeDegreeToWidth(float angle, float fWheelDiameter) {
        return (float) (fWheelDiameter * Math.sin(degreeToRadian(angle)));
    }

    private float degreeToRadian(float angle) {
        return (float) ((Math.PI * angle) / 180);
    }

    /**
     * 度 转 度分
     *
     * @param angle 百分度的数值
     * @return 度分的数值
     */
    private String degreeToMinu(float angle) {
        StringBuilder str = new StringBuilder(10);
        if (angle < 0) str.append("-");

        angle = 60 * Math.abs(angle);
        int i = (int) (angle / 60);
        str.append(i + "°");
        i = Math.round(angle - 60 * i);
        if (i < 10) str.append("0");

        return str.append(i + "'").toString();
    }
}
