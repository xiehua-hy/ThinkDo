package com.thinkdo.entity;

/**
 * Created by xh on 15/5/10.
 */
public enum UnitEnum {
    degreeSecond("0"), //度分
    degree("1"),       //百分度
    mm("2"),           //毫米
    inch("3");         //英寸

    private String value;

    private UnitEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static UnitEnum getUnitFromValue(String value) {
        switch (value) {
            case "0":
                return degreeSecond;
            case "1":
                return degree;
            case "2":
                return mm;
            case "3":
                return inch;
        }
        return degreeSecond;
    }
}
