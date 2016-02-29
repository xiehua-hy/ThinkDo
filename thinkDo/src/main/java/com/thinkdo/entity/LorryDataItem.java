package com.thinkdo.entity;


import android.graphics.Color;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/14.
 */
public class LorryDataItem {
    public static final String titleKey = "titleKey";
    public static final String backgroundColorKey = "backgroundColorKey";
    public static final String valuePairKey = "valuePairKey";

    protected boolean saved = false;

    //1
    protected ValuesPair firstTotalToe;
    protected ValuesPair firstLeftToe;
    protected ValuesPair firstRightToe;
    protected ValuesPair firstLeftCamber;
    protected ValuesPair firstRightCamber;

    protected ValuesPair firstLeftCaster;
    protected ValuesPair firstRightCaster;
    protected ValuesPair firstLeftKpi;
    protected ValuesPair firstRightKpi;

    //2
    protected ValuesPair secondTotalToe;
    protected ValuesPair secondLeftToe;
    protected ValuesPair secondRightToe;
    protected ValuesPair secondLeftCamber;
    protected ValuesPair secondRightCamber;

    protected ValuesPair secondLeftCaster;
    protected ValuesPair secondRightCaster;
    protected ValuesPair secondLeftKpi;
    protected ValuesPair secondRightKpi;

    //3
    protected ValuesPair thirdTotalToe;
    protected ValuesPair thirdLeftToe;
    protected ValuesPair thirdRightToe;
    protected ValuesPair thirdLeftCamber;
    protected ValuesPair thirdRightCamber;

    protected ValuesPair thirdLeftCaster;
    protected ValuesPair thirdRightCaster;
    protected ValuesPair thirdLeftKpi;
    protected ValuesPair thirdRightKpi;

    //4
    protected ValuesPair fourthTotalToe;
    protected ValuesPair fourthLeftToe;
    protected ValuesPair fourthRightToe;
    protected ValuesPair fourthLeftCamber;
    protected ValuesPair fourthRightCamber;

    protected ValuesPair fourthLeftCaster;
    protected ValuesPair fourthRightCaster;
    protected ValuesPair fourthLeftKpi;
    protected ValuesPair fourthRightKpi;

    //5
    protected ValuesPair fifthTotalToe;
    protected ValuesPair fifthLeftToe;
    protected ValuesPair fifthRightToe;
    protected ValuesPair fifthLeftCamber;
    protected ValuesPair fifthRightCamber;

    protected ValuesPair fifthLeftCaster;
    protected ValuesPair fifthRightCaster;
    protected ValuesPair fifthLeftKpi;
    protected ValuesPair fifthRightKpi;

    //6
    protected ValuesPair sixthTotalToe;
    protected ValuesPair sixthLeftToe;
    protected ValuesPair sixthRightToe;
    protected ValuesPair sixthLeftCamber;
    protected ValuesPair sixthRightCamber;

    protected ValuesPair sixthLeftCaster;
    protected ValuesPair sixthRightCaster;
    protected ValuesPair sixthLeftKpi;
    protected ValuesPair sixthRightKpi;


    //7
    protected ValuesPair seventhTotalToe;
    protected ValuesPair seventhLeftToe;
    protected ValuesPair seventhRightToe;
    protected ValuesPair seventhLeftCamber;
    protected ValuesPair seventhRightCamber;

    //8
    protected ValuesPair eighthTotalToe;
    protected ValuesPair eighthLeftToe;
    protected ValuesPair eighthRightToe;
    protected ValuesPair eighthLeftCamber;
    protected ValuesPair eighthRightCamber;

    //9
    protected ValuesPair ninthTotalToe;
    protected ValuesPair ninthLeftToe;
    protected ValuesPair ninthRightToe;
    protected ValuesPair ninthLeftCamber;
    protected ValuesPair ninthRightCamber;

    //10
    protected ValuesPair tenthTotalToe;
    protected ValuesPair tenthLeftToe;
    protected ValuesPair tenthRightToe;
    protected ValuesPair tenthLeftCamber;
    protected ValuesPair tenthRightCamber;

    //11
    protected ValuesPair eleventhTotalToe;
    protected ValuesPair eleventhLeftToe;
    protected ValuesPair eleventhRightToe;
    protected ValuesPair eleventhLeftCamber;
    protected ValuesPair eleventhRightCamber;

    //12
    protected ValuesPair twelfthTotalToe;
    protected ValuesPair twelfthLeftToe;
    protected ValuesPair twelfthRightToe;
    protected ValuesPair twelfthLeftCamber;
    protected ValuesPair twelfthRightCamber;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }


    public void setTotalToe(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstTotalToe(valuesPair);
                break;
            case 1:
                setSecondTotalToe(valuesPair);
                break;
            case 2:
                setThirdTotalToe(valuesPair);
                break;
            case 3:
                setFourthTotalToe(valuesPair);
                break;
            case 4:
                setFifthTotalToe(valuesPair);
                break;
            case 5:
                setSixthTotalToe(valuesPair);
                break;
            case 6:
                setSeventhTotalToe(valuesPair);
                break;
            case 7:
                setEighthTotalToe(valuesPair);
                break;
            case 8:
                setNinthTotalToe(valuesPair);
                break;
            case 9:
                setTenthTotalToe(valuesPair);
                break;
            case 10:
                setEleventhTotalToe(valuesPair);
                break;
            case 11:
                setTwelfthTotalToe(valuesPair);
                break;
        }
    }

    public void setLeftToe(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstLeftToe(valuesPair);
                break;
            case 1:
                setSecondLeftToe(valuesPair);
                break;
            case 2:
                setThirdLeftToe(valuesPair);
                break;
            case 3:
                setFourthLeftToe(valuesPair);
                break;
            case 4:
                setFifthLeftToe(valuesPair);
                break;
            case 5:
                setSixthLeftToe(valuesPair);
                break;
            case 6:
                setSeventhLeftToe(valuesPair);
                break;
            case 7:
                setEighthLeftToe(valuesPair);
                break;
            case 8:
                setNinthLeftToe(valuesPair);
                break;
            case 9:
                setTenthLeftToe(valuesPair);
                break;
            case 10:
                setEleventhLeftToe(valuesPair);
                break;
            case 11:
                setTwelfthLeftToe(valuesPair);
                break;
        }
    }

    public void setRightToe(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstRightToe(valuesPair);
                break;
            case 1:
                setSecondRightToe(valuesPair);
                break;
            case 2:
                setThirdRightToe(valuesPair);
                break;
            case 3:
                setFourthRightToe(valuesPair);
                break;
            case 4:
                setFifthRightToe(valuesPair);
                break;
            case 5:
                setSixthRightToe(valuesPair);
                break;
            case 6:
                setSeventhRightToe(valuesPair);
                break;
            case 7:
                setEighthRightToe(valuesPair);
                break;
            case 8:
                setNinthRightToe(valuesPair);
                break;
            case 9:
                setTenthRightToe(valuesPair);
                break;
            case 10:
                setEleventhRightToe(valuesPair);
                break;
            case 11:
                setTwelfthRightToe(valuesPair);
                break;
        }
    }

    public void setLeftCamber(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstLeftCamber(valuesPair);
                break;
            case 1:
                setSecondLeftCamber(valuesPair);
                break;
            case 2:
                setThirdLeftCamber(valuesPair);
                break;
            case 3:
                setFourthLeftCamber(valuesPair);
                break;
            case 4:
                setFifthLeftCamber(valuesPair);
                break;
            case 5:
                setSixthLeftCamber(valuesPair);
                break;
            case 6:
                setSeventhLeftCamber(valuesPair);
                break;
            case 7:
                setEighthLeftCamber(valuesPair);
                break;
            case 8:
                setNinthLeftCamber(valuesPair);
                break;
            case 9:
                setTenthLeftCamber(valuesPair);
                break;
            case 10:
                setEleventhLeftCamber(valuesPair);
                break;
            case 11:
                setTwelfthLeftCamber(valuesPair);
                break;
        }
    }

    public void setRightCamber(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstRightCamber(valuesPair);
                break;
            case 1:
                setSecondRightCamber(valuesPair);
                break;
            case 2:
                setThirdRightCamber(valuesPair);
                break;
            case 3:
                setFourthRightCamber(valuesPair);
                break;
            case 4:
                setFifthRightCamber(valuesPair);
                break;
            case 5:
                setSixthRightCamber(valuesPair);
                break;
            case 6:
                setSeventhRightCamber(valuesPair);
                break;
            case 7:
                setEighthRightCamber(valuesPair);
                break;
            case 8:
                setNinthRightCamber(valuesPair);
                break;
            case 9:
                setTenthRightCamber(valuesPair);
                break;
            case 10:
                setEleventhRightCamber(valuesPair);
                break;
            case 11:
                setTwelfthRightCamber(valuesPair);
                break;
        }
    }

    public void setLeftCaster(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstLeftCaster(valuesPair);
                break;
            case 1:
                setSecondLeftCaster(valuesPair);
                break;
            case 2:
                setThirdLeftCaster(valuesPair);
                break;
            case 3:
                setFourthLeftCaster(valuesPair);
                break;
            case 4:
                setFifthLeftCaster(valuesPair);
                break;
            case 5:
                setSixthLeftCaster(valuesPair);
                break;
        }
    }

    public void setRightCaster(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstRightCaster(valuesPair);
                break;
            case 1:
                setSecondRightCaster(valuesPair);
                break;
            case 2:
                setThirdRightCaster(valuesPair);
                break;
            case 3:
                setFourthRightCaster(valuesPair);
                break;
            case 4:
                setFifthRightCaster(valuesPair);
                break;
            case 5:
                setSixthRightCaster(valuesPair);
                break;
        }
    }

    public void setLeftKpi(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstLeftKpi(valuesPair);
                break;
            case 1:
                setSecondLeftKpi(valuesPair);
                break;
            case 2:
                setThirdLeftKpi(valuesPair);
                break;
            case 3:
                setFourthLeftKpi(valuesPair);
                break;
            case 4:
                setFifthLeftKpi(valuesPair);
                break;
            case 5:
                setSixthLeftKpi(valuesPair);
                break;
        }
    }

    public void setRightKpi(int index, ValuesPair valuesPair) {
        switch (index) {
            case 0:
                setFirstRightKpi(valuesPair);
                break;
            case 1:
                setSecondRightKpi(valuesPair);
                break;
            case 2:
                setThirdRightKpi(valuesPair);
                break;
            case 3:
                setFourthRightKpi(valuesPair);
                break;
            case 4:
                setFifthRightKpi(valuesPair);
                break;
            case 5:
                setSixthRightKpi(valuesPair);
                break;

        }
    }


    public ValuesPair getFirstTotalToe() {
        return firstTotalToe;
    }

    public void setFirstTotalToe(ValuesPair firstTotalToe) {
        this.firstTotalToe = firstTotalToe;
    }

    public ValuesPair getFirstLeftToe() {
        return firstLeftToe;
    }

    public void setFirstLeftToe(ValuesPair firstLeftToe) {
        this.firstLeftToe = firstLeftToe;
    }

    public ValuesPair getFirstRightToe() {
        return firstRightToe;
    }

    public void setFirstRightToe(ValuesPair firstRightToe) {
        this.firstRightToe = firstRightToe;
    }

    public ValuesPair getFirstLeftCamber() {
        return firstLeftCamber;
    }

    public void setFirstLeftCamber(ValuesPair firstLeftCamber) {
        this.firstLeftCamber = firstLeftCamber;
    }

    public ValuesPair getFirstRightCamber() {
        return firstRightCamber;
    }

    public void setFirstRightCamber(ValuesPair firstRightCamber) {
        this.firstRightCamber = firstRightCamber;
    }

    public ValuesPair getFirstLeftCaster() {
        return firstLeftCaster;
    }

    public void setFirstLeftCaster(ValuesPair firstLeftCaster) {
        this.firstLeftCaster = firstLeftCaster;
    }

    public ValuesPair getFirstRightCaster() {
        return firstRightCaster;
    }

    public void setFirstRightCaster(ValuesPair firstRightCaster) {
        this.firstRightCaster = firstRightCaster;
    }

    public ValuesPair getFirstLeftKpi() {
        return firstLeftKpi;
    }

    public void setFirstLeftKpi(ValuesPair firstLeftKpi) {
        this.firstLeftKpi = firstLeftKpi;
    }

    public ValuesPair getFirstRightKpi() {
        return firstRightKpi;
    }

    public void setFirstRightKpi(ValuesPair firstRightKpi) {
        this.firstRightKpi = firstRightKpi;
    }

    public ValuesPair getSecondTotalToe() {
        return secondTotalToe;
    }

    public void setSecondTotalToe(ValuesPair secondTotalToe) {
        this.secondTotalToe = secondTotalToe;
    }

    public ValuesPair getSecondLeftToe() {
        return secondLeftToe;
    }

    public void setSecondLeftToe(ValuesPair secondLeftToe) {
        this.secondLeftToe = secondLeftToe;
    }

    public ValuesPair getSecondRightToe() {
        return secondRightToe;
    }

    public void setSecondRightToe(ValuesPair secondRightToe) {
        this.secondRightToe = secondRightToe;
    }

    public ValuesPair getSecondLeftCamber() {
        return secondLeftCamber;
    }

    public void setSecondLeftCamber(ValuesPair secondLeftCamber) {
        this.secondLeftCamber = secondLeftCamber;
    }

    public ValuesPair getSecondRightCamber() {
        return secondRightCamber;
    }

    public void setSecondRightCamber(ValuesPair secondRightCamber) {
        this.secondRightCamber = secondRightCamber;
    }

    public ValuesPair getSecondLeftCaster() {
        return secondLeftCaster;
    }

    public void setSecondLeftCaster(ValuesPair secondLeftCaster) {
        this.secondLeftCaster = secondLeftCaster;
    }

    public ValuesPair getSecondRightCaster() {
        return secondRightCaster;
    }

    public void setSecondRightCaster(ValuesPair secondRightCaster) {
        this.secondRightCaster = secondRightCaster;
    }

    public ValuesPair getSecondLeftKpi() {
        return secondLeftKpi;
    }

    public void setSecondLeftKpi(ValuesPair secondLeftKpi) {
        this.secondLeftKpi = secondLeftKpi;
    }

    public ValuesPair getSecondRightKpi() {
        return secondRightKpi;
    }

    public void setSecondRightKpi(ValuesPair secondRightKpi) {
        this.secondRightKpi = secondRightKpi;
    }

    public ValuesPair getThirdTotalToe() {
        return thirdTotalToe;
    }

    public void setThirdTotalToe(ValuesPair thirdTotalToe) {
        this.thirdTotalToe = thirdTotalToe;
    }

    public ValuesPair getThirdLeftToe() {
        return thirdLeftToe;
    }

    public void setThirdLeftToe(ValuesPair thirdLeftToe) {
        this.thirdLeftToe = thirdLeftToe;
    }

    public ValuesPair getThirdRightToe() {
        return thirdRightToe;
    }

    public void setThirdRightToe(ValuesPair thirdRightToe) {
        this.thirdRightToe = thirdRightToe;
    }

    public ValuesPair getThirdLeftCamber() {
        return thirdLeftCamber;
    }

    public void setThirdLeftCamber(ValuesPair thirdLeftCamber) {
        this.thirdLeftCamber = thirdLeftCamber;
    }

    public ValuesPair getThirdRightCamber() {
        return thirdRightCamber;
    }

    public void setThirdRightCamber(ValuesPair thirdRightCamber) {
        this.thirdRightCamber = thirdRightCamber;
    }

    public ValuesPair getThirdLeftCaster() {
        return thirdLeftCaster;
    }

    public void setThirdLeftCaster(ValuesPair thirdLeftCaster) {
        this.thirdLeftCaster = thirdLeftCaster;
    }

    public ValuesPair getThirdRightCaster() {
        return thirdRightCaster;
    }

    public void setThirdRightCaster(ValuesPair thirdRightCaster) {
        this.thirdRightCaster = thirdRightCaster;
    }

    public ValuesPair getThirdLeftKpi() {
        return thirdLeftKpi;
    }

    public void setThirdLeftKpi(ValuesPair thirdLeftKpi) {
        this.thirdLeftKpi = thirdLeftKpi;
    }

    public ValuesPair getThirdRightKpi() {
        return thirdRightKpi;
    }

    public void setThirdRightKpi(ValuesPair thirdRightKpi) {
        this.thirdRightKpi = thirdRightKpi;
    }

    public ValuesPair getFourthTotalToe() {
        return fourthTotalToe;
    }

    public void setFourthTotalToe(ValuesPair fourthTotalToe) {
        this.fourthTotalToe = fourthTotalToe;
    }

    public ValuesPair getFourthLeftToe() {
        return fourthLeftToe;
    }

    public void setFourthLeftToe(ValuesPair fourthLeftToe) {
        this.fourthLeftToe = fourthLeftToe;
    }

    public ValuesPair getFourthRightToe() {
        return fourthRightToe;
    }

    public void setFourthRightToe(ValuesPair fourthRightToe) {
        this.fourthRightToe = fourthRightToe;
    }

    public ValuesPair getFourthLeftCamber() {
        return fourthLeftCamber;
    }

    public void setFourthLeftCamber(ValuesPair fourthLeftCamber) {
        this.fourthLeftCamber = fourthLeftCamber;
    }

    public ValuesPair getFourthRightCamber() {
        return fourthRightCamber;
    }

    public void setFourthRightCamber(ValuesPair fourthRightCamber) {
        this.fourthRightCamber = fourthRightCamber;
    }

    public ValuesPair getFourthLeftCaster() {
        return fourthLeftCaster;
    }

    public void setFourthLeftCaster(ValuesPair fourthLeftCaster) {
        this.fourthLeftCaster = fourthLeftCaster;
    }

    public ValuesPair getFourthRightCaster() {
        return fourthRightCaster;
    }

    public void setFourthRightCaster(ValuesPair fourthRightCaster) {
        this.fourthRightCaster = fourthRightCaster;
    }

    public ValuesPair getFourthLeftKpi() {
        return fourthLeftKpi;
    }

    public void setFourthLeftKpi(ValuesPair fourthLeftKpi) {
        this.fourthLeftKpi = fourthLeftKpi;
    }

    public ValuesPair getFourthRightKpi() {
        return fourthRightKpi;
    }

    public void setFourthRightKpi(ValuesPair fourthRightKpi) {
        this.fourthRightKpi = fourthRightKpi;
    }

    public ValuesPair getFifthTotalToe() {
        return fifthTotalToe;
    }

    public void setFifthTotalToe(ValuesPair fifthTotalToe) {
        this.fifthTotalToe = fifthTotalToe;
    }

    public ValuesPair getFifthLeftToe() {
        return fifthLeftToe;
    }

    public void setFifthLeftToe(ValuesPair fifthLeftToe) {
        this.fifthLeftToe = fifthLeftToe;
    }

    public ValuesPair getFifthRightToe() {
        return fifthRightToe;
    }

    public void setFifthRightToe(ValuesPair fifthRightToe) {
        this.fifthRightToe = fifthRightToe;
    }

    public ValuesPair getFifthLeftCamber() {
        return fifthLeftCamber;
    }

    public void setFifthLeftCamber(ValuesPair fifthLeftCamber) {
        this.fifthLeftCamber = fifthLeftCamber;
    }

    public ValuesPair getFifthRightCamber() {
        return fifthRightCamber;
    }

    public void setFifthRightCamber(ValuesPair fifthRightCamber) {
        this.fifthRightCamber = fifthRightCamber;
    }

    public ValuesPair getFifthLeftCaster() {
        return fifthLeftCaster;
    }

    public void setFifthLeftCaster(ValuesPair fifthLeftCaster) {
        this.fifthLeftCaster = fifthLeftCaster;
    }

    public ValuesPair getFifthRightCaster() {
        return fifthRightCaster;
    }

    public void setFifthRightCaster(ValuesPair fifthRightCaster) {
        this.fifthRightCaster = fifthRightCaster;
    }

    public ValuesPair getFifthLeftKpi() {
        return fifthLeftKpi;
    }

    public void setFifthLeftKpi(ValuesPair fifthLeftKpi) {
        this.fifthLeftKpi = fifthLeftKpi;
    }

    public ValuesPair getFifthRightKpi() {
        return fifthRightKpi;
    }

    public void setFifthRightKpi(ValuesPair fifthRightKpi) {
        this.fifthRightKpi = fifthRightKpi;
    }

    public ValuesPair getSixthTotalToe() {
        return sixthTotalToe;
    }

    public void setSixthTotalToe(ValuesPair sixthTotalToe) {
        this.sixthTotalToe = sixthTotalToe;
    }

    public ValuesPair getSixthLeftToe() {
        return sixthLeftToe;
    }

    public void setSixthLeftToe(ValuesPair sixthLeftToe) {
        this.sixthLeftToe = sixthLeftToe;
    }

    public ValuesPair getSixthRightToe() {
        return sixthRightToe;
    }

    public void setSixthRightToe(ValuesPair sixthRightToe) {
        this.sixthRightToe = sixthRightToe;
    }

    public ValuesPair getSixthLeftCamber() {
        return sixthLeftCamber;
    }

    public void setSixthLeftCamber(ValuesPair sixthLeftCamber) {
        this.sixthLeftCamber = sixthLeftCamber;
    }

    public ValuesPair getSixthRightCamber() {
        return sixthRightCamber;
    }

    public void setSixthRightCamber(ValuesPair sixthRightCamber) {
        this.sixthRightCamber = sixthRightCamber;
    }

    public ValuesPair getSixthLeftCaster() {
        return sixthLeftCaster;
    }

    public void setSixthLeftCaster(ValuesPair sixthLeftCaster) {
        this.sixthLeftCaster = sixthLeftCaster;
    }

    public ValuesPair getSixthRightCaster() {
        return sixthRightCaster;
    }

    public void setSixthRightCaster(ValuesPair sixthRightCaster) {
        this.sixthRightCaster = sixthRightCaster;
    }

    public ValuesPair getSixthLeftKpi() {
        return sixthLeftKpi;
    }

    public void setSixthLeftKpi(ValuesPair sixthLeftKpi) {
        this.sixthLeftKpi = sixthLeftKpi;
    }

    public ValuesPair getSixthRightKpi() {
        return sixthRightKpi;
    }

    public void setSixthRightKpi(ValuesPair sixthRightKpi) {
        this.sixthRightKpi = sixthRightKpi;
    }

    public ValuesPair getSeventhTotalToe() {
        return seventhTotalToe;
    }

    public void setSeventhTotalToe(ValuesPair seventhTotalToe) {
        this.seventhTotalToe = seventhTotalToe;
    }

    public ValuesPair getSeventhLeftToe() {
        return seventhLeftToe;
    }

    public void setSeventhLeftToe(ValuesPair seventhLeftToe) {
        this.seventhLeftToe = seventhLeftToe;
    }

    public ValuesPair getSeventhRightToe() {
        return seventhRightToe;
    }

    public void setSeventhRightToe(ValuesPair seventhRightToe) {
        this.seventhRightToe = seventhRightToe;
    }

    public ValuesPair getSeventhLeftCamber() {
        return seventhLeftCamber;
    }

    public void setSeventhLeftCamber(ValuesPair seventhLeftCamber) {
        this.seventhLeftCamber = seventhLeftCamber;
    }

    public ValuesPair getSeventhRightCamber() {
        return seventhRightCamber;
    }

    public void setSeventhRightCamber(ValuesPair seventhRightCamber) {
        this.seventhRightCamber = seventhRightCamber;
    }

    public ValuesPair getEighthTotalToe() {
        return eighthTotalToe;
    }

    public void setEighthTotalToe(ValuesPair eighthTotalToe) {
        this.eighthTotalToe = eighthTotalToe;
    }

    public ValuesPair getEighthLeftToe() {
        return eighthLeftToe;
    }

    public void setEighthLeftToe(ValuesPair eighthLeftToe) {
        this.eighthLeftToe = eighthLeftToe;
    }

    public ValuesPair getEighthRightToe() {
        return eighthRightToe;
    }

    public void setEighthRightToe(ValuesPair eighthRightToe) {
        this.eighthRightToe = eighthRightToe;
    }

    public ValuesPair getEighthLeftCamber() {
        return eighthLeftCamber;
    }

    public void setEighthLeftCamber(ValuesPair eighthLeftCamber) {
        this.eighthLeftCamber = eighthLeftCamber;
    }

    public ValuesPair getEighthRightCamber() {
        return eighthRightCamber;
    }

    public void setEighthRightCamber(ValuesPair eighthRightCamber) {
        this.eighthRightCamber = eighthRightCamber;
    }

    public ValuesPair getNinthTotalToe() {
        return ninthTotalToe;
    }

    public void setNinthTotalToe(ValuesPair ninthTotalToe) {
        this.ninthTotalToe = ninthTotalToe;
    }

    public ValuesPair getNinthLeftToe() {
        return ninthLeftToe;
    }

    public void setNinthLeftToe(ValuesPair ninthLeftToe) {
        this.ninthLeftToe = ninthLeftToe;
    }

    public ValuesPair getNinthRightToe() {
        return ninthRightToe;
    }

    public void setNinthRightToe(ValuesPair ninthRightToe) {
        this.ninthRightToe = ninthRightToe;
    }

    public ValuesPair getNinthLeftCamber() {
        return ninthLeftCamber;
    }

    public void setNinthLeftCamber(ValuesPair ninthLeftCamber) {
        this.ninthLeftCamber = ninthLeftCamber;
    }

    public ValuesPair getNinthRightCamber() {
        return ninthRightCamber;
    }

    public void setNinthRightCamber(ValuesPair ninthRightCamber) {
        this.ninthRightCamber = ninthRightCamber;
    }

    public ValuesPair getTenthTotalToe() {
        return tenthTotalToe;
    }

    public void setTenthTotalToe(ValuesPair tenthTotalToe) {
        this.tenthTotalToe = tenthTotalToe;
    }

    public ValuesPair getTenthLeftToe() {
        return tenthLeftToe;
    }

    public void setTenthLeftToe(ValuesPair tenthLeftToe) {
        this.tenthLeftToe = tenthLeftToe;
    }

    public ValuesPair getTenthRightToe() {
        return tenthRightToe;
    }

    public void setTenthRightToe(ValuesPair tenthRightToe) {
        this.tenthRightToe = tenthRightToe;
    }

    public ValuesPair getTenthLeftCamber() {
        return tenthLeftCamber;
    }

    public void setTenthLeftCamber(ValuesPair tenthLeftCamber) {
        this.tenthLeftCamber = tenthLeftCamber;
    }

    public ValuesPair getTenthRightCamber() {
        return tenthRightCamber;
    }

    public void setTenthRightCamber(ValuesPair tenthRightCamber) {
        this.tenthRightCamber = tenthRightCamber;
    }

    public ValuesPair getEleventhTotalToe() {
        return eleventhTotalToe;
    }

    public void setEleventhTotalToe(ValuesPair eleventhTotalToe) {
        this.eleventhTotalToe = eleventhTotalToe;
    }

    public ValuesPair getEleventhLeftToe() {
        return eleventhLeftToe;
    }

    public void setEleventhLeftToe(ValuesPair eleventhLeftToe) {
        this.eleventhLeftToe = eleventhLeftToe;
    }

    public ValuesPair getEleventhRightToe() {
        return eleventhRightToe;
    }

    public void setEleventhRightToe(ValuesPair eleventhRightToe) {
        this.eleventhRightToe = eleventhRightToe;
    }

    public ValuesPair getEleventhLeftCamber() {
        return eleventhLeftCamber;
    }

    public void setEleventhLeftCamber(ValuesPair eleventhLeftCamber) {
        this.eleventhLeftCamber = eleventhLeftCamber;
    }

    public ValuesPair getEleventhRightCamber() {
        return eleventhRightCamber;
    }

    public void setEleventhRightCamber(ValuesPair eleventhRightCamber) {
        this.eleventhRightCamber = eleventhRightCamber;
    }

    public ValuesPair getTwelfthTotalToe() {
        return twelfthTotalToe;
    }

    public void setTwelfthTotalToe(ValuesPair twelfthTotalToe) {
        this.twelfthTotalToe = twelfthTotalToe;
    }

    public ValuesPair getTwelfthLeftToe() {
        return twelfthLeftToe;
    }

    public void setTwelfthLeftToe(ValuesPair twelfthLeftToe) {
        this.twelfthLeftToe = twelfthLeftToe;
    }

    public ValuesPair getTwelfthRightToe() {
        return twelfthRightToe;
    }

    public void setTwelfthRightToe(ValuesPair twelfthRightToe) {
        this.twelfthRightToe = twelfthRightToe;
    }

    public ValuesPair getTwelfthLeftCamber() {
        return twelfthLeftCamber;
    }

    public void setTwelfthLeftCamber(ValuesPair twelfthLeftCamber) {
        this.twelfthLeftCamber = twelfthLeftCamber;
    }

    public ValuesPair getTwelfthRightCamber() {
        return twelfthRightCamber;
    }

    public void setTwelfthRightCamber(ValuesPair twelfthRightCamber) {
        this.twelfthRightCamber = twelfthRightCamber;
    }


    private int[] titleIDs = {
            //1
            R.string.first_total_toe, R.string.first_left_toe, R.string.first_right_toe,
            R.string.first_left_camber, R.string.first_right_camber,
            R.string.first_left_caster, R.string.first_right_caster,
            R.string.first_left_kpi, R.string.first_right_kpi,
            //2
            R.string.second_total_toe, R.string.second_left_toe, R.string.second_right_toe,
            R.string.second_left_camber, R.string.second_right_camber,
            R.string.second_left_caster, R.string.second_right_caster,
            R.string.second_left_kpi, R.string.second_right_kpi,
            //3
            R.string.third_total_toe, R.string.third_left_toe, R.string.third_right_toe,
            R.string.third_left_camber, R.string.third_right_camber,
            R.string.third_left_caster, R.string.third_right_caster,
            R.string.third_left_kpi, R.string.third_right_kpi,
            //4
            R.string.fourth_total_toe, R.string.fourth_left_toe, R.string.fourth_right_toe,
            R.string.fourth_left_camber, R.string.fourth_right_camber,
            R.string.fourth_left_caster, R.string.fourth_right_caster,
            R.string.fourth_left_kpi, R.string.fourth_right_kpi,
            //5
            R.string.fifth_total_toe, R.string.fifth_left_toe, R.string.fifth_right_toe,
            R.string.fifth_left_camber, R.string.fifth_right_camber,
            R.string.fifth_left_caster, R.string.fifth_right_caster,
            R.string.fifth_left_kpi, R.string.fifth_right_kpi,
            //6
            R.string.sixth_total_toe, R.string.sixth_left_toe, R.string.sixth_right_toe,
            R.string.sixth_left_camber, R.string.sixth_right_camber,
            R.string.sixth_left_caster, R.string.sixth_right_caster,
            R.string.sixth_left_kpi, R.string.sixth_right_kpi,
            //7
            R.string.seventh_total_toe, R.string.seventh_left_toe, R.string.seventh_right_toe,
            R.string.seventh_left_camber, R.string.seventh_right_camber,
            //8
            R.string.eighth_total_toe, R.string.eighth_left_toe, R.string.eighth_right_toe,
            R.string.eighth_left_camber, R.string.eighth_right_camber,
            //9
            R.string.ninth_total_toe, R.string.ninth_left_toe, R.string.ninth_right_toe,
            R.string.ninth_left_camber, R.string.ninth_right_camber,
            //10
            R.string.tenth_total_toe, R.string.tenth_left_toe, R.string.tenth_right_toe,
            R.string.tenth_left_camber, R.string.tenth_right_camber,
            //11
            R.string.eleventh_total_toe, R.string.eleventh_left_toe, R.string.eleventh_right_toe,
            R.string.eleventh_left_camber, R.string.eleventh_right_camber,
            //12
            R.string.twelfth_total_toe, R.string.twelfth_left_toe, R.string.twelfth_right_toe,
            R.string.twelfth_left_camber, R.string.twelfth_right_camber,
    };

    public LorryDataItem copy() {
        LorryDataItem copy = new LorryDataItem();

        copy.setSaved(saved);
        //1
        copy.setFirstTotalToe(copyItself(firstTotalToe));
        copy.setFirstLeftToe(copyItself(firstLeftToe));
        copy.setFirstRightToe(copyItself(firstRightToe));
        copy.setFirstLeftCamber(copyItself(firstLeftCamber));
        copy.setFirstRightCamber(copyItself(firstRightCamber));
        copy.setFirstLeftCaster(copyItself(firstLeftCaster));
        copy.setFirstRightCaster(copyItself(firstRightCaster));
        copy.setFirstLeftKpi(copyItself(firstLeftKpi));
        copy.setFirstRightKpi(copyItself(firstRightKpi));
        //2
        copy.setSecondTotalToe(copyItself(secondTotalToe));
        copy.setSecondLeftToe(copyItself(secondLeftToe));
        copy.setSecondRightToe(copyItself(secondRightToe));
        copy.setSecondLeftCamber(copyItself(secondLeftCamber));
        copy.setSecondRightCamber(copyItself(secondRightCamber));
        copy.setSecondLeftCaster(copyItself(secondLeftCaster));
        copy.setSecondRightCaster(copyItself(secondRightCaster));
        copy.setSecondLeftKpi(copyItself(secondLeftKpi));
        copy.setSecondRightKpi(copyItself(secondRightKpi));
        //3
        copy.setThirdTotalToe(copyItself(thirdTotalToe));
        copy.setThirdLeftToe(copyItself(thirdLeftToe));
        copy.setThirdRightToe(copyItself(thirdRightToe));
        copy.setThirdLeftCamber(copyItself(thirdLeftCamber));
        copy.setThirdRightCamber(copyItself(thirdRightCamber));
        copy.setThirdLeftCaster(copyItself(thirdLeftCaster));
        copy.setThirdRightCaster(copyItself(thirdRightCaster));
        copy.setThirdLeftKpi(copyItself(thirdLeftKpi));
        copy.setThirdRightKpi(copyItself(thirdRightKpi));
        //4
        copy.setFourthTotalToe(copyItself(fourthTotalToe));
        copy.setFourthLeftToe(copyItself(fourthLeftToe));
        copy.setFourthRightToe(copyItself(fourthRightToe));
        copy.setFourthLeftCamber(copyItself(fourthLeftCamber));
        copy.setFourthRightCamber(copyItself(fourthRightCamber));
        copy.setFourthLeftCaster(copyItself(fourthLeftCaster));
        copy.setFourthRightCaster(copyItself(fourthRightCaster));
        copy.setFourthLeftKpi(copyItself(fourthLeftKpi));
        copy.setFourthRightKpi(copyItself(fourthRightKpi));
        //5
        copy.setFifthTotalToe(copyItself(fifthTotalToe));
        copy.setFifthLeftToe(copyItself(fifthLeftToe));
        copy.setFifthRightToe(copyItself(fifthRightToe));
        copy.setFifthLeftCamber(copyItself(fifthLeftCamber));
        copy.setFifthRightCamber(copyItself(fifthRightCamber));
        copy.setFifthLeftCaster(copyItself(fifthLeftCaster));
        copy.setFifthRightCaster(copyItself(fifthRightCaster));
        copy.setFifthLeftKpi(copyItself(fifthLeftKpi));
        copy.setFifthRightKpi(copyItself(fifthRightKpi));
        //6
        copy.setSixthTotalToe(copyItself(sixthTotalToe));
        copy.setSixthLeftToe(copyItself(sixthLeftToe));
        copy.setSixthRightToe(copyItself(sixthRightToe));
        copy.setSixthLeftCamber(copyItself(sixthLeftCamber));
        copy.setSixthRightCamber(copyItself(sixthRightCamber));
        copy.setSixthLeftCaster(copyItself(sixthLeftCaster));
        copy.setSixthRightCaster(copyItself(sixthRightCaster));
        copy.setSixthLeftKpi(copyItself(sixthLeftKpi));
        copy.setSixthRightKpi(copyItself(sixthRightKpi));
        //7
        copy.setSeventhTotalToe(copyItself(seventhTotalToe));
        copy.setSeventhLeftToe(copyItself(seventhLeftToe));
        copy.setSeventhRightToe(copyItself(seventhRightToe));
        copy.setSeventhLeftCamber(copyItself(seventhLeftCamber));
        copy.setSeventhRightCamber(copyItself(seventhRightCamber));
        //8
        copy.setEighthTotalToe(copyItself(eighthTotalToe));
        copy.setEighthLeftToe(copyItself(eighthLeftToe));
        copy.setEighthRightToe(copyItself(eighthRightToe));
        copy.setEighthLeftCamber(copyItself(eighthLeftCamber));
        copy.setEighthRightCamber(copyItself(eighthRightCamber));
        //9
        copy.setNinthTotalToe(copyItself(ninthTotalToe));
        copy.setNinthLeftToe(copyItself(ninthLeftToe));
        copy.setNinthRightToe(copyItself(ninthRightToe));
        copy.setNinthLeftCamber(copyItself(ninthLeftCamber));
        copy.setNinthRightCamber(copyItself(ninthRightCamber));
        //10
        copy.setTenthTotalToe(copyItself(tenthTotalToe));
        copy.setTenthLeftToe(copyItself(tenthLeftToe));
        copy.setTenthRightToe(copyItself(tenthRightToe));
        copy.setTenthLeftCamber(copyItself(tenthLeftCamber));
        copy.setTenthRightCamber(copyItself(tenthRightCamber));
        //11
        copy.setEleventhTotalToe(copyItself(eleventhTotalToe));
        copy.setEleventhLeftToe(copyItself(eleventhLeftToe));
        copy.setEleventhRightToe(copyItself(eleventhRightToe));
        copy.setEleventhLeftCamber(copyItself(eleventhLeftCamber));
        copy.setEleventhRightCamber(copyItself(eleventhRightCamber));
        //12
        copy.setTwelfthTotalToe(copyItself(twelfthTotalToe));
        copy.setTwelfthLeftToe(copyItself(twelfthLeftToe));
        copy.setTwelfthRightToe(copyItself(twelfthRightToe));
        copy.setTwelfthLeftCamber(copyItself(twelfthLeftCamber));
        copy.setTwelfthRightCamber(copyItself(twelfthRightCamber));

        return copy;
    }

    private ValuesPair copyItself(ValuesPair pair) {
        if (pair != null) {
            return pair.copy();
        }
        return null;
    }

    public void unitConvert() {
        //1
        unitConvert(firstTotalToe, MainApplication.toeUnit);
        unitConvert(firstLeftToe, MainApplication.toeUnit);
        unitConvert(firstRightToe, MainApplication.toeUnit);
        unitConvert(firstLeftCamber, MainApplication.unit);
        unitConvert(firstRightCamber, MainApplication.unit);
        unitConvert(firstLeftCaster, MainApplication.unit);
        unitConvert(firstRightCaster, MainApplication.unit);
        unitConvert(firstLeftKpi, MainApplication.unit);
        unitConvert(firstRightKpi, MainApplication.unit);
        //2
        unitConvert(secondTotalToe, MainApplication.toeUnit);
        unitConvert(secondLeftToe, MainApplication.toeUnit);
        unitConvert(secondRightToe, MainApplication.toeUnit);
        unitConvert(secondLeftCamber, MainApplication.unit);
        unitConvert(secondRightCamber, MainApplication.unit);
        unitConvert(secondLeftCaster, MainApplication.unit);
        unitConvert(secondRightCaster, MainApplication.unit);
        unitConvert(secondLeftKpi, MainApplication.unit);
        unitConvert(secondRightKpi, MainApplication.unit);
        //3
        unitConvert(thirdTotalToe, MainApplication.toeUnit);
        unitConvert(thirdLeftToe, MainApplication.toeUnit);
        unitConvert(thirdRightToe, MainApplication.toeUnit);
        unitConvert(thirdLeftCamber, MainApplication.unit);
        unitConvert(thirdRightCamber, MainApplication.unit);
        unitConvert(thirdLeftCaster, MainApplication.unit);
        unitConvert(thirdRightCaster, MainApplication.unit);
        unitConvert(thirdLeftKpi, MainApplication.unit);
        unitConvert(thirdRightKpi, MainApplication.unit);
        //4
        unitConvert(fourthTotalToe, MainApplication.toeUnit);
        unitConvert(fourthLeftToe, MainApplication.toeUnit);
        unitConvert(fourthRightToe, MainApplication.toeUnit);
        unitConvert(fourthLeftCamber, MainApplication.unit);
        unitConvert(fourthRightCamber, MainApplication.unit);
        unitConvert(fourthLeftCaster, MainApplication.unit);
        unitConvert(fourthRightCaster, MainApplication.unit);
        unitConvert(fourthLeftKpi, MainApplication.unit);
        unitConvert(fourthRightKpi, MainApplication.unit);
        //5
        unitConvert(fifthTotalToe, MainApplication.toeUnit);
        unitConvert(fifthLeftToe, MainApplication.toeUnit);
        unitConvert(fifthRightToe, MainApplication.toeUnit);
        unitConvert(fifthLeftCamber, MainApplication.unit);
        unitConvert(fifthRightCamber, MainApplication.unit);
        unitConvert(fifthLeftCaster, MainApplication.unit);
        unitConvert(fifthRightCaster, MainApplication.unit);
        unitConvert(fifthLeftKpi, MainApplication.unit);
        unitConvert(fifthRightKpi, MainApplication.unit);
        //6
        unitConvert(sixthTotalToe, MainApplication.toeUnit);
        unitConvert(sixthLeftToe, MainApplication.toeUnit);
        unitConvert(sixthRightToe, MainApplication.toeUnit);
        unitConvert(sixthLeftCamber, MainApplication.unit);
        unitConvert(sixthRightCamber, MainApplication.unit);
        unitConvert(sixthLeftCaster, MainApplication.unit);
        unitConvert(sixthRightCaster, MainApplication.unit);
        unitConvert(sixthLeftKpi, MainApplication.unit);
        unitConvert(sixthRightKpi, MainApplication.unit);
        //7
        unitConvert(seventhTotalToe, MainApplication.toeUnit);
        unitConvert(seventhLeftToe, MainApplication.toeUnit);
        unitConvert(seventhRightToe, MainApplication.toeUnit);
        unitConvert(seventhLeftCamber, MainApplication.unit);
        unitConvert(seventhRightCamber, MainApplication.unit);
        //8
        unitConvert(eighthTotalToe, MainApplication.toeUnit);
        unitConvert(eighthLeftToe, MainApplication.toeUnit);
        unitConvert(eighthRightToe, MainApplication.toeUnit);
        unitConvert(eighthLeftCamber, MainApplication.unit);
        unitConvert(eighthRightCamber, MainApplication.unit);
        //9
        unitConvert(ninthTotalToe, MainApplication.toeUnit);
        unitConvert(ninthLeftToe, MainApplication.toeUnit);
        unitConvert(ninthRightToe, MainApplication.toeUnit);
        unitConvert(ninthLeftCamber, MainApplication.unit);
        unitConvert(ninthRightCamber, MainApplication.unit);
        //10
        unitConvert(tenthTotalToe, MainApplication.toeUnit);
        unitConvert(tenthLeftToe, MainApplication.toeUnit);
        unitConvert(tenthRightToe, MainApplication.toeUnit);
        unitConvert(tenthLeftCamber, MainApplication.unit);
        unitConvert(tenthRightCamber, MainApplication.unit);
        //11
        unitConvert(eleventhTotalToe, MainApplication.toeUnit);
        unitConvert(eleventhLeftToe, MainApplication.toeUnit);
        unitConvert(eleventhRightToe, MainApplication.toeUnit);
        unitConvert(eleventhLeftCamber, MainApplication.unit);
        unitConvert(eleventhRightCamber, MainApplication.unit);
        //12
        unitConvert(twelfthTotalToe, MainApplication.toeUnit);
        unitConvert(twelfthLeftToe, MainApplication.toeUnit);
        unitConvert(twelfthRightToe, MainApplication.toeUnit);
        unitConvert(twelfthLeftCamber, MainApplication.unit);
        unitConvert(twelfthRightCamber, MainApplication.unit);
    }

    public ValuesPair unitConvert(ValuesPair pair, UnitEnum unit) {
        if (pair != null) {
            pair.unitConvert(unit);
            return pair;
        }
        return null;
    }

    public List<Map> generatePrintDataMap() {
        List<Map> list = new ArrayList<>();
        int i = 0;
        //1
        list.add(generateMap(i++, firstTotalToe, true));
        list.add(generateMap(i++, firstLeftToe, true));
        list.add(generateMap(i++, firstRightToe, true));
        list.add(generateMap(i++, firstLeftCamber));
        list.add(generateMap(i++, firstRightCamber));
        list.add(generateMap(i++, firstLeftCaster, true));
        list.add(generateMap(i++, firstRightCaster, true));
        list.add(generateMap(i++, firstLeftKpi));
        list.add(generateMap(i++, firstRightKpi));
        //2
        list.add(generateMap(i++, secondTotalToe, true));
        list.add(generateMap(i++, secondLeftToe, true));
        list.add(generateMap(i++, secondRightToe, true));
        list.add(generateMap(i++, secondLeftCamber));
        list.add(generateMap(i++, secondRightCamber));
        list.add(generateMap(i++, secondLeftCaster, true));
        list.add(generateMap(i++, secondRightCaster, true));
        list.add(generateMap(i++, secondLeftKpi));
        list.add(generateMap(i++, secondRightKpi));
        //3
        list.add(generateMap(i++, thirdTotalToe, true));
        list.add(generateMap(i++, thirdLeftToe, true));
        list.add(generateMap(i++, thirdRightToe, true));
        list.add(generateMap(i++, thirdLeftCamber));
        list.add(generateMap(i++, thirdRightCamber));
        list.add(generateMap(i++, thirdLeftCaster, true));
        list.add(generateMap(i++, thirdRightCaster, true));
        list.add(generateMap(i++, thirdLeftKpi));
        list.add(generateMap(i++, thirdRightKpi));
        //4
        list.add(generateMap(i++, fourthTotalToe, true));
        list.add(generateMap(i++, fourthLeftToe, true));
        list.add(generateMap(i++, fourthRightToe, true));
        list.add(generateMap(i++, fourthLeftCamber));
        list.add(generateMap(i++, fourthRightCamber));
        list.add(generateMap(i++, fourthLeftCaster, true));
        list.add(generateMap(i++, fourthRightCaster, true));
        list.add(generateMap(i++, fourthLeftKpi));
        list.add(generateMap(i++, fourthRightKpi));
        //5
        list.add(generateMap(i++, fifthTotalToe, true));
        list.add(generateMap(i++, fifthLeftToe, true));
        list.add(generateMap(i++, fifthRightToe, true));
        list.add(generateMap(i++, fifthLeftCamber));
        list.add(generateMap(i++, fifthRightCamber));
        list.add(generateMap(i++, fifthLeftCaster, true));
        list.add(generateMap(i++, fifthRightCaster, true));
        list.add(generateMap(i++, fifthLeftKpi));
        list.add(generateMap(i++, fifthRightKpi));
        //6
        list.add(generateMap(i++, sixthTotalToe, true));
        list.add(generateMap(i++, sixthLeftToe, true));
        list.add(generateMap(i++, sixthRightToe, true));
        list.add(generateMap(i++, sixthLeftCamber));
        list.add(generateMap(i++, sixthRightCamber));
        list.add(generateMap(i++, sixthLeftCaster, true));
        list.add(generateMap(i++, sixthRightCaster, true));
        list.add(generateMap(i++, sixthLeftKpi));
        list.add(generateMap(i++, sixthRightKpi));
        //7
        list.add(generateMap(i++, seventhTotalToe, true));
        list.add(generateMap(i++, seventhLeftToe, true));
        list.add(generateMap(i++, seventhRightToe, true));
        list.add(generateMap(i++, seventhLeftCamber));
        list.add(generateMap(i++, seventhRightCamber));
        //8
        list.add(generateMap(i++, eighthTotalToe, true));
        list.add(generateMap(i++, eighthLeftToe, true));
        list.add(generateMap(i++, eighthRightToe, true));
        list.add(generateMap(i++, eighthLeftCamber));
        list.add(generateMap(i++, eighthRightCamber));
        //9
        list.add(generateMap(i++, ninthTotalToe, true));
        list.add(generateMap(i++, ninthLeftToe, true));
        list.add(generateMap(i++, ninthRightToe, true));
        list.add(generateMap(i++, ninthLeftCamber));
        list.add(generateMap(i++, ninthRightCamber));
        //10
        list.add(generateMap(i++, tenthTotalToe, true));
        list.add(generateMap(i++, tenthLeftToe, true));
        list.add(generateMap(i++, tenthRightToe, true));
        list.add(generateMap(i++, tenthLeftCamber));
        list.add(generateMap(i++, tenthRightCamber));
        //11
        list.add(generateMap(i++, eleventhTotalToe, true));
        list.add(generateMap(i++, eleventhLeftToe, true));
        list.add(generateMap(i++, eleventhRightToe, true));
        list.add(generateMap(i++, eleventhLeftCamber));
        list.add(generateMap(i++, eleventhRightCamber));
        //12
        list.add(generateMap(i++, twelfthTotalToe, true));
        list.add(generateMap(i++, twelfthLeftToe, true));
        list.add(generateMap(i++, twelfthRightToe, true));
        list.add(generateMap(i++, twelfthLeftCamber));
        list.add(generateMap(i, twelfthRightCamber));

        return list;
    }

    private Map generateMap(int index, ValuesPair valuesPair) {
        return this.generateMap(index, valuesPair, false);
    }

    private Map generateMap(int index, ValuesPair valuesPair, boolean isHolo) {
        int color = isHolo ? MainApplication.context.getResources().getColor(R.color.bar_holo) : Color.WHITE;

        Map<String, Object> map = new HashMap<>();
        map.put(titleKey, MainApplication.context.getString(titleIDs[index]));
        map.put(valuePairKey, valuesPair);
        map.put(backgroundColorKey, color);
        return map;
    }

    public void initEmptyValue() {
        firstTotalToe = initEmptyValue(firstTotalToe);
        firstLeftToe = initEmptyValue(firstLeftToe);
        firstRightToe = initEmptyValue(firstRightToe);
        firstLeftCamber = initEmptyValue(firstLeftCamber);
        firstRightCamber = initEmptyValue(firstRightCamber);
        firstLeftCaster = initEmptyValue(firstLeftCaster);
        firstRightCaster = initEmptyValue(firstRightCaster);
        firstLeftKpi = initEmptyValue(firstLeftKpi);
        firstRightKpi = initEmptyValue(firstRightKpi);

        secondTotalToe = initEmptyValue(secondTotalToe);
        secondLeftToe = initEmptyValue(secondLeftToe);
        secondRightToe = initEmptyValue(secondRightToe);
        secondLeftCamber = initEmptyValue(secondLeftCamber);
        secondRightCamber = initEmptyValue(secondRightCamber);
        secondLeftCaster = initEmptyValue(secondLeftCaster);
        secondRightCaster = initEmptyValue(secondRightCaster);
        secondLeftKpi = initEmptyValue(secondLeftKpi);
        secondRightKpi = initEmptyValue(secondRightKpi);

        thirdTotalToe = initEmptyValue(thirdTotalToe);
        thirdLeftToe = initEmptyValue(thirdLeftToe);
        thirdRightToe = initEmptyValue(thirdRightToe);
        thirdLeftCamber = initEmptyValue(thirdLeftCamber);
        thirdRightCamber = initEmptyValue(thirdRightCamber);
        thirdLeftCaster = initEmptyValue(thirdLeftCaster);
        thirdRightCaster = initEmptyValue(thirdRightCaster);
        thirdLeftKpi = initEmptyValue(thirdLeftKpi);
        thirdRightKpi = initEmptyValue(thirdRightKpi);

        fourthTotalToe = initEmptyValue(fourthTotalToe);
        fourthLeftToe = initEmptyValue(fourthLeftToe);
        fourthRightToe = initEmptyValue(fourthRightToe);
        fourthLeftCamber = initEmptyValue(fourthLeftCamber);
        fourthRightCamber = initEmptyValue(fourthRightCamber);
        fourthLeftCaster = initEmptyValue(fourthLeftCaster);
        fourthRightCaster = initEmptyValue(fourthRightCaster);
        fourthLeftKpi = initEmptyValue(fourthLeftKpi);
        fourthRightKpi = initEmptyValue(fourthRightKpi);

        fifthTotalToe = initEmptyValue(fifthTotalToe);
        fifthLeftToe = initEmptyValue(fifthLeftToe);
        fifthRightToe = initEmptyValue(fifthRightToe);
        fifthLeftCamber = initEmptyValue(fifthLeftCamber);
        fifthRightCamber = initEmptyValue(fifthRightCamber);
        fifthLeftCaster = initEmptyValue(fifthLeftCaster);
        fifthRightCaster = initEmptyValue(fifthRightCaster);
        fifthLeftKpi = initEmptyValue(fifthLeftKpi);
        fifthRightKpi = initEmptyValue(fifthRightKpi);

        sixthTotalToe = initEmptyValue(sixthTotalToe);
        sixthLeftToe = initEmptyValue(sixthLeftToe);
        sixthRightToe = initEmptyValue(sixthRightToe);
        sixthLeftCamber = initEmptyValue(sixthLeftCamber);
        sixthRightCamber = initEmptyValue(sixthRightCamber);
        sixthLeftCaster = initEmptyValue(sixthLeftCaster);
        sixthRightCaster = initEmptyValue(sixthRightCaster);
        sixthLeftKpi = initEmptyValue(sixthLeftKpi);
        sixthRightKpi = initEmptyValue(sixthRightKpi);

        seventhTotalToe = initEmptyValue(seventhTotalToe);
        seventhLeftToe = initEmptyValue(seventhLeftToe);
        seventhRightToe = initEmptyValue(seventhRightToe);
        seventhLeftCamber = initEmptyValue(seventhLeftCamber);
        seventhRightCamber = initEmptyValue(seventhRightCamber);

        eighthTotalToe = initEmptyValue(eighthTotalToe);
        eighthLeftToe = initEmptyValue(eighthLeftToe);
        eighthRightToe = initEmptyValue(eighthRightToe);
        eighthLeftCamber = initEmptyValue(eighthLeftCamber);
        eighthRightCamber = initEmptyValue(eighthRightCamber);

        ninthTotalToe = initEmptyValue(ninthTotalToe);
        ninthLeftToe = initEmptyValue(ninthLeftToe);
        ninthRightToe = initEmptyValue(ninthRightToe);
        ninthLeftCamber = initEmptyValue(ninthLeftCamber);
        ninthRightCamber = initEmptyValue(ninthRightCamber);

        tenthTotalToe = initEmptyValue(tenthTotalToe);
        tenthLeftToe = initEmptyValue(tenthLeftToe);
        tenthRightToe = initEmptyValue(tenthRightToe);
        tenthLeftCamber = initEmptyValue(tenthLeftCamber);
        tenthRightCamber = initEmptyValue(tenthRightCamber);

        eleventhTotalToe = initEmptyValue(eleventhTotalToe);
        eleventhLeftToe = initEmptyValue(eleventhLeftToe);
        eleventhRightToe = initEmptyValue(eleventhRightToe);
        eleventhLeftCamber = initEmptyValue(eleventhLeftCamber);
        eleventhRightCamber = initEmptyValue(eleventhRightCamber);

        twelfthTotalToe = initEmptyValue(twelfthTotalToe);
        twelfthLeftToe = initEmptyValue(twelfthLeftToe);
        twelfthRightToe = initEmptyValue(twelfthRightToe);
        twelfthLeftCamber = initEmptyValue(twelfthLeftCamber);
        twelfthRightCamber = initEmptyValue(twelfthRightCamber);
    }

    private ValuesPair initEmptyValue(ValuesPair values) {
        if (values == null) {
            values = new ValuesPair();
        }

        values.initEmptyValue();
        return values;
    }

    public String getPrintData(CustomerModel custom, TestVehicleInfoModel info) {


        return null;
    }


    public ValuesPair[] getValuesPairs() {
        ValuesPair[] pairs = {
                firstTotalToe, firstLeftToe, firstRightToe, firstLeftCamber, firstRightCamber,
                firstLeftCaster, firstRightCaster, firstLeftKpi, firstRightKpi,

                secondTotalToe, secondLeftToe, secondRightToe, secondLeftCamber, secondRightCamber,
                secondLeftCaster, secondRightCaster, secondLeftKpi, secondRightKpi,

                thirdTotalToe, thirdLeftToe, thirdRightToe, thirdLeftCamber, thirdRightCamber,
                thirdLeftCaster, thirdRightCaster, thirdLeftKpi, thirdRightKpi,

                fourthTotalToe, fourthLeftToe, fourthRightToe, fourthLeftCamber, fourthRightCamber,
                fourthLeftCaster, fourthRightCaster, fourthLeftKpi, fourthRightKpi,

                fifthTotalToe, fifthLeftToe, fifthRightToe, fifthLeftCamber, fifthRightCamber,
                fifthLeftCaster, fifthRightCaster, fifthLeftKpi, fifthRightKpi,

                sixthTotalToe, sixthLeftToe, sixthRightToe, sixthLeftCamber, sixthRightCamber,
                sixthLeftCaster, sixthRightCaster, sixthLeftKpi, sixthRightKpi,

                seventhTotalToe, seventhLeftToe, seventhRightToe, seventhLeftCamber, seventhRightCamber,

                eighthTotalToe, eighthLeftToe, eighthRightToe, eighthLeftCamber, eighthRightCamber,

                ninthTotalToe, ninthLeftToe, ninthRightToe, ninthLeftCamber, ninthRightCamber,

                tenthTotalToe, tenthLeftToe, tenthRightToe, tenthLeftCamber, tenthRightCamber,

                eleventhTotalToe, eleventhLeftToe, eleventhRightToe, eleventhLeftCamber, eleventhRightCamber,

                twelfthTotalToe, twelfthLeftToe, twelfthRightToe, twelfthLeftCamber, twelfthRightCamber,
        };
        return pairs;
    }
}
