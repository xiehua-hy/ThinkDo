package com.thinkdo.entity;

import android.graphics.Color;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Locale;

public class ValuesPair implements CopyProtocol<ValuesPair>, UnitConvertProtocol, Serializable {
    protected String min;
    protected String mid;
    protected String max;

    protected String preReal;
    protected String real;

    protected String explain;

    protected float percent;

    protected int color = Color.BLUE;

    public ValuesPair() {
        init();
    }

    public ValuesPair(String min, String max) {
        mid = originalAverage(min, max);
        if (GloVariable.initValue.equals(mid)) {
            init();
        } else {
            this.min = min;
            this.max = max;
        }

    }

    public ValuesPair(String min, String mid, String max) {
        this(min, mid, max, null);
    }

    public ValuesPair(String min, String mid, String max, String explain) {
        setMin(min);
        setMid(mid);
        setMax(max);
        setExplain(explain);
    }

    /**
     * 标准值为最大值与最小值的平均值
     *
     * @param min 最小值
     * @param max 最大值
     */
    public ValuesPair(float min, float max) {
        this(min, max, null);
    }

    /**
     * 标准值为最大值与最小值的平均值
     *
     * @param min     最小值
     * @param max     最大值
     * @param explain 说明
     */
    public ValuesPair(float min, float max, String explain) {
        setMid(format((min + max) / 2));
        setMin(format(min));
        setMax(format(max));

        setExplain(explain);
    }

    /**
     * @param min 下偏差
     * @param mid 标准值
     * @param max 上偏差
     */
    public ValuesPair(float min, float mid, float max) {
        this(min, mid, max, null);
    }

    /**
     * @param min     下偏差
     * @param mid     标准值
     * @param max     上偏差
     * @param explain 说明
     */
    public ValuesPair(float min, float mid, float max, String explain) {
        this(min, mid, max, false, explain);
    }

    /**
     * @param flag 当flag为true时, min,max表示最小值,最大值;
     *             否则min,max表示下偏差,上偏差
     */
    public ValuesPair(float min, float mid, float max, boolean flag) {
        this(min, mid, max, flag, null);
    }

    /**
     * @param flag 当flag为true时, fMin,fMax表示最小值,最大值;
     *             否则fMin,fMax表示下偏差,上偏差
     */
    public ValuesPair(float fMin, float fMid, float fMax, boolean flag, String explain) {
        String sMid = format(fMid);
        if (isInitValue(sMid)) {
            init();
            return;
        }

        this.mid = sMid;
        if (isInitValue(format(fMin))) {
            this.min = this.mid;
            this.max = this.mid;
            return;
        }

        if (flag) {
            this.min = format(fMin);
            this.max = format(fMax);
        } else {
            fMin = fMin >= 0 ? fMin : -fMin;
            this.min = format(fMid - fMin);
            this.max = format(fMid + fMax);
        }
        setExplain(explain);
    }

    public ValuesPair(String min, String max, UnitEnum unit) {
        if (GloVariable.initValue.equals(min) || GloVariable.initValue.equals(max)) {
            init();
        } else {
            this.min = reverseUnitConvert(min, unit);
            this.max = reverseUnitConvert(max, unit);
            this.mid = format((Float.parseFloat(min) + Float.parseFloat(max)) / 2, 2);
        }
    }

    /**
     * 将值由 其他单位 转成 度
     */
    public String reverseUnitConvert(String str, UnitEnum unit) {
        switch (unit) {
            case degree:
                return format(Float.parseFloat(str), 2);
            case degreeSecond:
                return format(minuToDegree(str), 2);
            case inch:
                return format(toeWidthToDegree(str, "500.26"), 2);
            case mm:
                return format(toeWidthToDegree(str, "19.70"), 2);
            default:
                return format(Float.parseFloat(str), 2);
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

    public ValuesPair generateTotalToe() {
        if (mid.equals(GloVariable.initValue)) {
            return new ValuesPair();
        } else {
            return new ValuesPair(format(Float.parseFloat(min) * 2),
                    format(Float.parseFloat(mid) * 2),
                    format(Float.parseFloat(max) * 2));
        }
    }

    protected void init() {
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

    public String getPreReal() {
        return preReal;
    }

    public void setPreReal(String preReal) {
        this.preReal = preReal;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        if (preReal == null) setPreReal(real);
        this.real = real;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public float getPercent() {
        return percent;
    }

    /**
     * @param ascent ascent true 则表示左边小，右边大; 否则反之
     */
    public void generatePercentAndcolor(boolean ascent) {
        if (mid == null || real == null) return;

        percent = calculatePercent(ascent);

        if(percent>1){
            color = Color.RED;
        }else if(percent>0){
            color = Color.GREEN;
        }else if(percent > -1){
            color = Color.RED;
        }else{
            color = Color.BLUE;
        }
    }

    protected float calculatePercent(boolean ascent) {
        if (GloVariable.initValue.equals(max)) return -1.0f;

        float ma = Float.parseFloat(max);
        float t = Float.parseFloat(real);
        float mi = Float.parseFloat(min);
        if (ascent) {
            if (t >= ma) return 1.21f;
            if (t <= mi) return -0.21f;
            if (max.equals(min)) return 0.5f;
            return (t - mi) / (ma - mi);
        } else {
            if (t >= ma) return -0.21f;
            if (t <= mi) return 1.21f;
            if (max.equals(min)) return 0.5f;
            return (ma - t) / (ma - mi);
        }
    }

    @Override
    public ValuesPair copy() {
        ValuesPair valuesPair = new ValuesPair(min, mid, max);
        valuesPair.setPreReal(preReal);
        valuesPair.setReal(real);
        valuesPair.setExplain(explain);
        return valuesPair;
    }

    /**
     * 将值由百分度转换成其他度量单位
     */
    @Override
    public void unitConvert(UnitEnum unit) {
        min = unitConvert(min, unit);
        mid = unitConvert(mid, unit);
        max = unitConvert(max, unit);

        preReal = unitConvert(preReal, unit);
        real = unitConvert(real, unit);
    }

    public boolean isInitValue(String value) {
        return value.equals(GloVariable.initValue);
    }

    /**
     * 将valuePair的值格式化
     * 如果值为初始值99.99,则此值不进行格式化操作
     *
     * @param num 值保留小数点的位数
     */
    public void format(int num) {
        if (mid != null && !GloVariable.initValue.equals(mid)) {
            min = format(Float.parseFloat(min), num);
            mid = format(Float.parseFloat(mid), num);
            max = format(Float.parseFloat(max), num);
        }
        if (real != null && !GloVariable.initValue.equals(real)) {
            real = format(Float.parseFloat(real), num);
            preReal = format(Float.parseFloat(preReal), num);
        }
    }

    protected String format(float value) {
        return new DecimalFormat("0.00").format(value);
    }

    protected String format(float f, int num) {
        StringBuilder style = new StringBuilder("0");

        if (num > 0)
            style.append(".");

        for (int i = num; i > 0; i--) {
            style.append("0");
        }
        return new DecimalFormat(style.toString()).format(f);
    }

    /**
     * 百分度 转 其他单位
     *
     * @param angle 百分度的数值
     * @param unit  转换的目标单位
     * @return String
     */
    protected String unitConvert(String angle, UnitEnum unit) {
        if (angle == null) return null;
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
    protected float toeDegreeToWidth(float angle, float fWheelDiameter) {
        return (float) (fWheelDiameter * Math.sin(degreeToRadian(angle)));
    }

    protected float degreeToRadian(float angle) {
        return (float) ((Math.PI * angle) / 180);
    }

    /**
     * 度 转 度分
     *
     * @param angle 百分度的数值
     * @return 度分的数值
     */
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

    /**
     * 度分 转 度 的单位转换
     *
     * @param str 单位的为度的数
     */
    protected float minuToDegree(String str) {
        boolean negative = false;
        float flo = Float.parseFloat(str);
        if (flo < 0) negative = true;
        flo = Math.abs(flo);

        int integer = (int) flo;
        float point = flo - integer;

        flo = 100 * point / 60f;
        integer += (int) flo;

        point = integer + flo - (int) flo;

        if (negative) point = -point;
        return point;
    }

    /**
     * 长度转换为 度 的单位转换
     *
     * @param str      要转换的数
     * @param diameter 轮胎的直径  默认值为500.26（mm） 或 19.70 inch
     */

    protected float toeWidthToDegree(String str, String diameter) {
        double flo = Math.asin(Float.parseFloat(str) / Float.parseFloat(diameter));
        flo = 180 * flo / Math.PI;
        return (float) flo;
    }

    /**
     * 如果 sMin、sMax当中有一个乱码，则需要最大值，最小值，标准值全置于初始值
     */
    public String originalAverage(String sMin, String sMax) {
        float min, max;
        try {
            min = Float.parseFloat(sMin);
            max = Float.parseFloat(sMax);
            return format((min + max) / 2, 2);
        } catch (NumberFormatException e) {
            return GloVariable.initValue;
        }
    }

}
