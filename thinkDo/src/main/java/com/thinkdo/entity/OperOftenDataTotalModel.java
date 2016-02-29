package com.thinkdo.entity;

/**
 * Created by Administrator on 2015/5/21.
 */

import com.thinkdo.application.MainApplication;

/**
 * 自定义添加车型时 车辆信息的实体类
 * 对应 表OperOftenDataTotalModel
 * */
public class OperOftenDataTotalModel {
    private String manId;
    private String manInfo;

    private String vehicleId;
    private String vehicleInfo;

    private String startYear;
    private String endYear;

    private String OftenTotal6;

    private String date;
    private int language = 1;

    private String wheelType;
    private String diameterInch;
    private String diameterMM;

    private String pyIndex;

    private int refCount = 0;
    private String levelFlag;

    public String getManId() {
        return manId;
    }

    public void setManId(String manId) {
        this.manId = manId;
    }

    public String getManInfo() {
        if (manInfo == null) return MainApplication.emptyString;
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
        if (vehicleInfo == null) return MainApplication.emptyString;
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getStartYear() {
        if (startYear == null) return MainApplication.emptyString;
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        if (endYear == null) return MainApplication.emptyString;
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getOftenTotal6() {
        if (OftenTotal6 == null) return "0";
        return OftenTotal6;
    }

    public void setOftenTotal6(String oftenTotal6) {
        OftenTotal6 = oftenTotal6;
    }

    public String getDate() {
        if (date == null) return MainApplication.emptyString;
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getWheelType() {
        if (wheelType == null) return MainApplication.emptyString;
        return wheelType;
    }

    public void setWheelType(String wheelType) {
        this.wheelType = wheelType;
    }

    public String getDiameterInch() {
        if (diameterInch == null) return "0";
        return diameterInch;
    }

    public void setDiameterInch(String diameterInch) {
        this.diameterInch = diameterInch;
    }

    public String getDiameterMM() {
        if (diameterMM == null) return "0";
        return diameterMM;
    }

    public void setDiameterMM(String diameterMM) {
        this.diameterMM = diameterMM;
    }

    public String getPyIndex() {
        if (pyIndex == null) return MainApplication.emptyString;
        return pyIndex;
    }

    public void setPyIndex(String pyIndex) {
        this.pyIndex = pyIndex;
    }

    public int getRefCount() {
        return refCount;
    }

    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    public String getLevelFlag() {
        if (levelFlag == null) return "1";
        return levelFlag;
    }

    public void setLevelFlag(String levelFlag) {
        this.levelFlag = levelFlag;
    }
}
