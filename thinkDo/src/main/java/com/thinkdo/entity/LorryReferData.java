package com.thinkdo.entity;

import com.thinkdo.application.MainApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/7.
 */
public class LorryReferData extends ReferData {
    protected int referAxle = 0;

    //二轴
    protected ValuesPair secondTotalToe;
    protected ValuesPair secondLeftToe;
    protected ValuesPair secondRightToe;

    protected ValuesPair secondLeftCamber;
    protected ValuesPair secondRightCamber;

    protected ValuesPair secondLeftCaster;
    protected ValuesPair secondRightCaster;

    protected ValuesPair secondLeftKpi;
    protected ValuesPair secondRightKpi;

    //四轴
    protected ValuesPair fourthTotalToe;
    protected ValuesPair fourthLeftToe;
    protected ValuesPair fourthRightToe;

    protected ValuesPair fourthLeftCamber;
    protected ValuesPair fourthRightCamber;


    @Override
    public void init() {
        super.init();

        //secondAxle
        secondTotalToe = new ValuesPair();
        secondLeftToe = new ValuesPair();
        secondRightToe = new ValuesPair();

        secondLeftCamber = new ValuesPair();
        secondRightCamber = new ValuesPair();

        secondLeftCaster = new ValuesPair();
        secondRightCaster = new ValuesPair();

        secondLeftKpi = new ValuesPair();
        secondRightKpi = new ValuesPair();

        //fourthAxle
        fourthTotalToe = new ValuesPair();
        fourthLeftToe = new ValuesPair();
        fourthRightToe = new ValuesPair();

        fourthLeftCamber = new ValuesPair();
        fourthRightCamber = new ValuesPair();
    }

    public void initWithReferData(ReferData data) {
        if (data != null) {
            if (data.getVehicleInfo() != null) {
                vehicleInfo = data.getVehicleInfo();
            }

            if (data.getFrontTotalToe() != null) {
                leftFrontToe = data.getLeftFrontToe().copy();
                rightFrontToe = data.getRightFrontToe().copy();
                frontTotalToe = data.getFrontTotalToe().copy();

                secondLeftToe = data.getLeftFrontToe().copy();
                secondRightToe = data.getRightFrontToe().copy();
                secondTotalToe = data.getFrontTotalToe().copy();

                leftFrontCamber = data.getLeftFrontCamber().copy();
                rightFrontCamber = data.getRightFrontCamber().copy();

                secondLeftCamber = data.getLeftFrontCamber().copy();
                secondRightCamber = data.getRightFrontCamber().copy();

                leftCaster = data.getLeftCaster().copy();
                rightCaster = data.getRightCaster().copy();

                secondLeftCaster = data.getLeftCaster().copy();
                secondRightCaster = data.getRightCaster().copy();

                leftKpi = data.getLeftKpi().copy();
                rightKpi = data.getRightKpi().copy();

                secondLeftKpi = data.getLeftKpi();
                secondRightKpi = data.getRightKpi();

                leftRearToe = data.getLeftRearToe().copy();
                rightRearToe = data.getRightRearToe().copy();
                rearTotalToe = data.getRearTotalToe().copy();

                fourthLeftToe = data.getLeftRearToe().copy();
                fourthRightToe = data.getRightRearToe().copy();
                fourthTotalToe = data.getRearTotalToe().copy();

                leftRearCamber = data.getLeftRearCamber().copy();
                rightRearCamber = data.getRightRearCamber().copy();

                fourthLeftCamber = data.getLeftRearCamber().copy();
                fourthRightCamber = data.getRightRearCamber().copy();

                maxThrust = data.getMaxThrust().copy();
            }

            if (data.getWheelbase() != null) {
                wheelbase = data.getWheelbase().copy();
                frontWheel = data.getFrontWheel().copy();
                rearWheel = data.getRearWheel().copy();
            }

        }
    }

    @Override
    public ReferData copy() {
        LorryReferData copy = (LorryReferData) super.copy();
        copy.setReferAxle(referAxle);

        if (frontTotalToe != null) {
            //second
            copy.setSecondTotalToe(secondTotalToe.copy());
            copy.setSecondLeftToe(secondLeftToe.copy());
            copy.setSecondRightToe(secondRightToe.copy());

            copy.setSecondLeftCamber(secondLeftCamber.copy());
            copy.setSecondRightCamber(secondRightCamber.copy());

            copy.setSecondLeftCaster(secondLeftCaster.copy());
            copy.setSecondRightCaster(secondRightCaster.copy());

            copy.setSecondLeftKpi(secondLeftKpi.copy());
            copy.setSecondRightKpi(secondRightKpi.copy());

            //fourth
            copy.setFourthTotalToe(fourthTotalToe.copy());
            copy.setFourthLeftToe(fourthLeftToe.copy());
            copy.setFourthRightToe(fourthRightToe.copy());

            copy.setFourthLeftCamber(fourthLeftCamber.copy());
            copy.setFourthRightCamber(fourthRightCamber.copy());

        }
        return copy;
    }

    @Override
    public void unitConvert() {
        super.unitConvert();
        if (frontTotalToe != null) {
            secondTotalToe.unitConvert(MainApplication.toeUnit);
            secondLeftToe.unitConvert(MainApplication.toeUnit);
            secondRightToe.unitConvert(MainApplication.toeUnit);

            secondLeftCamber.unitConvert(MainApplication.unit);
            secondRightCamber.unitConvert(MainApplication.unit);

            secondLeftCaster.unitConvert(MainApplication.unit);
            secondRightCaster.unitConvert(MainApplication.unit);

            secondLeftKpi.unitConvert(MainApplication.unit);
            secondRightKpi.unitConvert(MainApplication.unit);

            //fourth
            fourthTotalToe.unitConvert(MainApplication.toeUnit);
            fourthLeftToe.unitConvert(MainApplication.toeUnit);
            fourthRightToe.unitConvert(MainApplication.toeUnit);

            fourthLeftCamber.unitConvert(MainApplication.unit);
            fourthRightCamber.unitConvert(MainApplication.unit);
        }
    }

    public int getReferAxle() {
        return referAxle;
    }

    public void setReferAxle(int referAxle) {
        this.referAxle = referAxle;
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

    public ValuesPair getSecondRightKpi() {
        return secondRightKpi;
    }

    public void setSecondRightKpi(ValuesPair secondRightKpi) {
        this.secondRightKpi = secondRightKpi;
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


    @Override
    public void addRealData(String test) {
        if (frontTotalToe == null) init();

        if (test.contains("&&")) {
            String[] array = test.split("&&");
            String[] data = array[1].split("\\|");


            //23+33
            if (data.length == 56) {
                int flag;
                int i = 33;
                try {
                    //保证vehicleId没有乱码
                    flag = Integer.parseInt(data[i]);
                } catch (NumberFormatException e) {
                    flag = 0;
                }

                if (flag != 0 && !String.valueOf(flag).equals(vehicleId)) {
                    setFlush(true);
                    vehicleId = data[i++];
                    manId = data[i++];
                    manInfo = data[i++];
                    i++;
                    vehicleInfo = data[i++]; //前面一位是vehicleId,已记录
                    startYear = data[i++];
                    endYear = data[i++];
                    realYear = data[i++];

                    leftFrontToe.setReferValue(data[i++], data[i++]);
                    rightFrontToe.setReferValue(leftFrontToe);
                    frontTotalToe.setReferValue(leftFrontToe.generateTotalToe());


                    leftFrontCamber.setReferValue(data[i++], data[i++]);
                    rightFrontCamber.setReferValue(leftFrontCamber);

                    leftCaster.setReferValue(data[i++], data[i++]);
                    rightCaster.setReferValue(leftCaster);

                    leftKpi.setReferValue(data[i++], data[i++]);
                    rightKpi.setReferValue(leftKpi);

                    leftRearToe.setReferValue(data[i++], data[i++]);
                    rightRearToe.setReferValue(leftRearToe);
                    rearTotalToe.setReferValue(leftRearToe.generateTotalToe());


                    leftRearCamber.setReferValue(data[i++], data[i++]);
                    rightRearCamber.setReferValue(leftRearCamber);

                    if (wheelbase == null) {
                        wheelbase = new ValuesPair(originalDeal(data[i++], 0));
                        frontWheel = new ValuesPair(originalDeal(data[i++], 0));
                        rearWheel = new ValuesPair(originalDeal(data[i++], 0));
                    } else {
                        wheelbase.setReferValue(originalDeal(data[i++], 0));
                        frontWheel.setReferValue(originalDeal(data[i++], 0));
                        rearWheel.setReferValue(originalDeal(data[i++], 0));
                    }
                }
            }


            //18+14+1
            if (data.length >= 33) {
                int i = 0;
                try {
                    referAxle = Integer.parseInt(data[i++]);
                } catch (NumberFormatException e) {
                    referAxle = 0;
                }
                setRaiseStatus(getUpStatus(data[i++]));

                frontTotalToe.setReal(originalDeal(data[i++], 2));
                leftFrontToe.setReal(originalDeal(data[i++], 2));
                rightFrontToe.setReal(originalDeal(data[i++], 2));

                leftFrontCamber.setReal(originalDeal(data[i++], 2));
                rightFrontCamber.setReal(originalDeal(data[i++], 2));

                secondTotalToe.setReal(originalDeal(data[i++], 2));
                secondLeftToe.setReal(originalDeal(data[i++], 2));
                secondRightToe.setReal(originalDeal(data[i++], 2));

                secondLeftCamber.setReal(originalDeal(data[i++], 2));
                secondRightCamber.setReal(originalDeal(data[i++], 2));

                //Rear
                rearTotalToe.setReal(originalDeal(data[i++], 2));
                leftRearToe.setReal(originalDeal(data[i++], 2));
                rightRearToe.setReal(originalDeal(data[i++], 2));

                leftRearCamber.setReal(originalDeal(data[i++], 2));
                rightRearCamber.setReal(originalDeal(data[i++], 2));

                fourthTotalToe.setReal(originalDeal(data[i++], 2));
                fourthLeftToe.setReal(originalDeal(data[i++], 2));
                fourthRightToe.setReal(originalDeal(data[i++], 2));

                fourthLeftCamber.setReal(originalDeal(data[i++], 2));
                fourthRightCamber.setReal(originalDeal(data[i++], 2));

                leftCaster.setReal(originalDeal(data[i++], 2));
                rightCaster.setReal(originalDeal(data[i++], 2));

                leftKpi.setReal(originalDeal(data[i++], 2));
                rightKpi.setReal(originalDeal(data[i++], 2));

                secondLeftCaster.setReal(originalDeal(data[i++], 2));
                secondRightCaster.setReal(originalDeal(data[i++], 2));

                secondLeftKpi.setReal(originalDeal(data[i++], 2));
                secondRightKpi.setReal(originalDeal(data[i++], 2));


                if (maxThrust != null) maxThrust.setReal(originalDeal(data[i++], 2));
                else {
                    maxThrust = new ValuesPair();
                    maxThrust.setReal(originalDeal(data[i++], 2));
                }

                if (wheelbaseDiff == null) {
                    wheelbaseDiff = new ValuesPair();
                    wheelDiff = new ValuesPair();

                    wheelbaseDiff.setReal(originalDeal(data[i++], 0));
                    wheelDiff.setReal((originalDeal(data[i++], 0)));
                } else {
                    wheelbaseDiff.setReal(originalDeal(data[i++], 0));
                    wheelDiff.setReal(originalDeal(data[i++], 0));
                }

                frontTotalToe.generatePercentAndColor(true);
                leftFrontToe.generatePercentAndColor(true);
                rightFrontToe.generatePercentAndColor(false);

                leftFrontCamber.generatePercentAndColor(false);
                rightFrontCamber.generatePercentAndColor(true);

                secondTotalToe.generatePercentAndColor(true);
                secondLeftToe.generatePercentAndColor(true);
                secondRightToe.generatePercentAndColor(false);

                secondLeftCamber.generatePercentAndColor(false);
                secondRightCamber.generatePercentAndColor(true);

                //Rear
                rearTotalToe.generatePercentAndColor(true);
                leftRearToe.generatePercentAndColor(true);
                rightRearToe.generatePercentAndColor(false);

                leftRearCamber.generatePercentAndColor(false);
                rightRearCamber.generatePercentAndColor(true);

                fourthTotalToe.generatePercentAndColor(true);
                fourthLeftToe.generatePercentAndColor(true);
                fourthRightToe.generatePercentAndColor(false);

                leftRearCamber.generatePercentAndColor(false);
                rightRearCamber.generatePercentAndColor(true);

                leftCaster.generatePercentAndColor(true);
                rightCaster.generatePercentAndColor(true);

                leftKpi.generatePercentAndColor(true);
                rightKpi.generatePercentAndColor(false);
            }
        }
    }


}
