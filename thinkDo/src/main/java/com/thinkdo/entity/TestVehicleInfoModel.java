package com.thinkdo.entity;

import com.thinkdo.application.MainApplication;

/**
 * Created by Administrator on 2015/5/29.
 */
public class TestVehicleInfoModel {

    protected String customerId;
    protected String plateNum;
    protected String testSerial;
    protected String testDate;
    protected String operator;
    protected String testTimeLong;
    protected String malfunction;
    protected String manInfo;
    protected String model;
    protected String startYear;
    protected String endYear;
    protected String curUnit;
    protected String curToeUnit;

    protected String tireDiameter;
    protected String wheelBase = MainApplication.initValue;
    protected String frontTread = MainApplication.initValue;
    protected String RearTread = MainApplication.initValue;
    protected String remark;

    protected boolean cb1 = false;
    protected boolean cb2 = false;
    protected boolean cb3 = false;
    protected boolean cb4 = false;
    protected boolean cb5 = false;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getTestSerial() {
        return testSerial;
    }

    public void setTestSerial(String testSerial) {
        this.testSerial = testSerial;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTestTimeLong() {
        return testTimeLong;
    }

    public void setTestTimeLong(String testTimeLong) {
        this.testTimeLong = testTimeLong;
    }

    public String getMalfunction() {
        return malfunction;
    }

    public void setMalfunction(String malfunction) {
        this.malfunction = malfunction;
    }

    public String getManInfo() {
        return manInfo;
    }

    public void setManInfo(String manInfo) {
        this.manInfo = manInfo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getCurUnit() {
        return curUnit;
    }

    public void setCurUnit(String curUnit) {
        this.curUnit = curUnit;
    }

    public String getCurToeUnit() {
        return curToeUnit;
    }

    public void setCurToeUnit(String curToeUnit) {
        this.curToeUnit = curToeUnit;
    }

    public String getTireDiameter() {
        return tireDiameter;
    }

    public void setTireDiameter(String tireDiameter) {
        this.tireDiameter = tireDiameter;
    }

    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    public String getFrontTread() {
        return frontTread;
    }

    public void setFrontTread(String frontTread) {
        this.frontTread = frontTread;
    }

    public String getRearTread() {
        return RearTread;
    }

    public void setRearTread(String rearTread) {
        RearTread = rearTread;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isCb1() {
        return cb1;
    }

    public void setCb1(boolean cb1) {
        this.cb1 = cb1;
    }

    public boolean isCb2() {
        return cb2;
    }

    public void setCb2(boolean cb2) {
        this.cb2 = cb2;
    }

    public boolean isCb3() {
        return cb3;
    }

    public void setCb3(boolean cb3) {
        this.cb3 = cb3;
    }

    public boolean isCb4() {
        return cb4;
    }

    public void setCb4(boolean cb4) {
        this.cb4 = cb4;
    }

    public boolean isCb5() {
        return cb5;
    }

    public void setCb5(boolean cb5) {
        this.cb5 = cb5;
    }

    public void initEmptyValue() {
        if (customerId == null) customerId = "";
        if (plateNum == null) plateNum = "";
        if (testSerial == null) testSerial = "";
        if (testDate == null) testDate = "";
        if (operator == null) operator = "";
        if (testTimeLong == null) testTimeLong = "";
        if (malfunction == null) malfunction = "";
        if (manInfo == null) manInfo = "";
        if (model == null) model = "";
        if (startYear == null) startYear = "";
        if (endYear == null) endYear = "";
        if (curUnit == null) curUnit = "";
        if (curToeUnit == null) curToeUnit = "";
        if (tireDiameter == null) tireDiameter = "";
        if (remark == null) remark = "";
    }

}
