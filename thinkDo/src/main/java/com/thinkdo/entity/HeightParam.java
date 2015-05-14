package com.thinkdo.entity;

public class HeightParam {
    private String vehicleId;

    private String heightFlag;     //测高标志
    private String heightPicPath;  //测高图片路径

    private ValuesPair frontHeight;
    private ValuesPair RearHeight;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getHeightFlag() {
        return heightFlag;
    }

    public void setHeightFlag(String heightFlag) {
        this.heightFlag = heightFlag;
    }

    public String getHeightPicPath() {
        return heightPicPath;
    }

    public void setHeightPicPath(String heightPicPath) {
        this.heightPicPath = heightPicPath;
    }

    public ValuesPair getFrontHeight() {
        return frontHeight;
    }

    public void setFrontHeight(ValuesPair frontHeight) {
        this.frontHeight = frontHeight;
    }

    public ValuesPair getRearHeight() {
        return RearHeight;
    }

    public void setRearHeight(ValuesPair rearHeight) {
        RearHeight = rearHeight;
    }
}
