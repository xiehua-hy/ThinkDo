package com.thinkdo.entity;

import java.io.Serializable;

public class SpecialParams implements Serializable {

    private String weightId;       // ɳ������Id
    private String heightFlag;     // ��߱�־
    private String heightPicPath;  //���ͼƬ��·��
    private String levelFlag;      //����ˮƽ��־
    private String referDataId;    //��׼����ID

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
