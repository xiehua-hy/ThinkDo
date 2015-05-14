package com.thinkdo.entity;

import java.io.Serializable;

public class SpecialParams implements Serializable {

    private String weightId;       // 沙袋油箱Id
    private String heightFlag;     // 测高标志
    private String heightPicPath;  //测高图片的路径
    private String levelFlag;      //奔驰水平标志
    private String referDataId;    //标准数据ID

    public String getWeightId() {
        return weightId;
    }

    public void setWeightId(String weightId) {
        this.weightId = weightId;
    }

    public String getReferDataId() {
        return referDataId;
    }

    public void setReferDataId(String referDataId) {
        this.referDataId = referDataId;
    }

    public String getLevelFlag() {
        return levelFlag;
    }

    public void setLevelFlag(String levelFlag) {
        this.levelFlag = levelFlag;
    }

    public String getHeightPicPath() {
        return heightPicPath;
    }

    public void setHeightPicPath(String heightPicPath) {
        this.heightPicPath = heightPicPath;
    }

    public String getHeightFlag() {
        return heightFlag;
    }

    public void setHeightFlag(String heightFlag) {
        this.heightFlag = heightFlag;
    }

}
