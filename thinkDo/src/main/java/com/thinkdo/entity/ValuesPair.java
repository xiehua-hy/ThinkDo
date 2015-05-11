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
        this(fmin, fmid, fmax, false);
    }

    /**
     * @param flag 当flag为true时, fmin,fmax表示最小值,最大值;
     *             否则fmin,fmax表示下偏差,上偏差
     */
    public ValuesPair(float fmin, float fmid, float fmax, boolean flag) {
        String smid = format(fmid);
        if (isInitValue(smid)) {
            init();
            return;
        }

        mid = smid;
        if (isInitValue(format(fmin))) {
            min = mid;
            max = mid;
            return;
        }

        if (flag) {
            min = format(fmin);
            max = format(fmax);
        } else {
            min = format(fmid - fmin);
            max = format(fmid + fmax);
        }

    }

    /**
     * 把自己当成总前束 产生一个 单前束
     */
    public ValuesPair generateSingleToe() {
        if (mid.equals(GloVariable.initValue)) {
            return new ValuesPair();
        } else {
            return new ValuesPair(format(Float.parseFloat(min) / 2),
                    format(Float.parseFloat(mid) / 2),
                    format(Float.parseFloat(max) / 2));
        }
    }

    /**
     * 与 valuesPair 合并
     * 如果valuesPair的值不为初始值的话,则将值替换成valuesPair的值;
     */
    public void combine(ValuesPair valuesPair) {
        if (valuesPair.getMid().equals(GloVariable.initValue)) return;

        min = valuesPair.getMin();
        mid = valuesPair.getMid();
        max = valuesPair.getMax();
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
        if (angle.equals(GloVariable.initValue)) return angle;
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
