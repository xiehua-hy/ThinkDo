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

    UnitEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static UnitEnum getUnitFromValue(String value) {
        switch (value) {
            case "1":
                return degreeSecond;
            case "2":
                return degree;
            case "3":
                return mm;
            case "4":
                return inch;
        }
        return degreeSecond;
    }

    public static UnitEnum getUnitFromValue(int value) {
        switch (value) {
            case 1:
                return degreeSecond;
            case 2:
                return degree;
            case 3:
                return mm;
            case 4:
                return inch;
        }
        return degreeSecond;
    }
}
