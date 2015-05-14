package com.thinkdo.entity;

import java.io.Serializable;

public class SpecialParams implements Serializable {
    private ReferData referData;

    private WeightParam weightParam;
    private HeightParam heightParam;
    private LevelParam levelParam;

    public ReferData getReferData() {
        return referData;
    }

    public void setReferData(ReferData referData) {
        this.referData = referData;
    }

    public WeightParam getWeightParam() {
        return weightParam;
    }

    public void setWeightParam(WeightParam weightParam) {
        this.weightParam = weightParam;
    }

    public HeightParam getHeightParam() {
        return heightParam;
    }

    public void setHeightParam(HeightParam heightParam) {
        this.heightParam = heightParam;
    }

    public LevelParam getLevelParam() {
        return levelParam;
    }

    public void setLevelParam(LevelParam levelParam) {
        this.levelParam = levelParam;
    }

}
