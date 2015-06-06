package com.thinkdo.entity;

/**
 * Created by Administrator on 2015/6/2.
 */
public class FormMetaModel implements Comparable<FormMetaModel>{
    private String customerId;
    private String testNo;
    private String name;
    private String vehicle;
    private String plateNo;
    private String date;
    private String tester;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle() {
        return vehicle;
    }
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getPlateNo() {
        return plateNo;
    }
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTester() {
        return tester;
    }
    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTestNo() {
        return testNo;
    }

    public void setTestNo(String testNo) {
        this.testNo = testNo;
    }

    @Override
    public int compareTo(FormMetaModel another) {
        return another.getDate().compareTo(date);
    }

}
