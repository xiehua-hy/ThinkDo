package com.thinkdo.entity;

import com.thinkdo.util.CommonUtil;

import java.io.Serializable;

/**
 * Created by xh on 15/5/10.
 */
public class ReferData implements Serializable, CopyProtocol<ReferData> {
    //状态标志位
    protected boolean save;
    protected boolean flush;

    protected boolean raiseStatus;

    //车辆信息
    protected String manId;

    protected String manInfo;
    protected String vehicleId;
    protected String realYear;
    protected String vehicleInfo;

    protected String startYear;
    protected String endYear;
    //参数
    protected ValuesPair frontTotalToe;

    protected ValuesPair leftFrontToe;
    protected ValuesPair rightFrontToe;
    protected ValuesPair leftFrontCamber;

    protected ValuesPair rightFrontCamber;
    protected ValuesPair leftCaster;

    protected ValuesPair rightCaster;
    protected ValuesPair leftKpi;

    protected ValuesPair rightKpi;
    protected ValuesPair rearTotalToe;

    protected ValuesPair leftRearToe;
    protected ValuesPair rightRearToe;
    protected ValuesPair leftRearCamber;

    protected ValuesPair rightRearCamber;
    //特殊参数
    protected String maxThrust;

    protected String wheelbase;

    protected String frontWheel;
    protected String rearWheel;
    protected String wheelbaseDiff; //轴距差


    protected String wheelDiff;    //轮距差
    //包容角
    protected String leftIncludeAngle;
    protected String rightIncludeAngle;

    //20度前张角
    protected String leftTurnAngle;
    protected String rightTurnAngle;

    //最大转向角
    protected String leftMaxTurnAngle;
    protected String rightMaxTurnAngle;


    public boolean isSaved() {
        return save;
    }

    public void setSaved(boolean save) {
        this.save = save;
    }

    public boolean isFlush() {
        return flush;
    }

    public void setFlush(boolean flush) {
        this.flush = flush;
    }

    public boolean isRaiseStatus() {
        return raiseStatus;
    }

    public void setRaiseStatus(boolean raiseStatus) {
        this.raiseStatus = raiseStatus;
    }

    public String getWheelbaseDiff() {
        return wheelbaseDiff;
    }

    public void setWheelbaseDiff(String wheelbaseDiff) {
        this.wheelbaseDiff = wheelbaseDiff;
    }

    public String getWheelDiff() {
        return wheelDiff;
    }

    public void setWheelDiff(String wheelDiff) {
        this.wheelDiff = wheelDiff;
    }

    public String getLeftIncludeAngle() {
        return leftIncludeAngle;
    }

    public void setLeftIncludeAngle(String leftIncludeAngle) {
        this.leftIncludeAngle = leftIncludeAngle;
    }

    public String getRightIncludeAngle() {
        return rightIncludeAngle;
    }

    public void setRightIncludeAngle(String rightIncludeAngle) {
        this.rightIncludeAngle = rightIncludeAngle;
    }

    public String getLeftTurnAngle() {
        return leftTurnAngle;
    }

    public void setLeftTurnAngle(String leftTurnAngle) {
        this.leftTurnAngle = leftTurnAngle;
    }

    public String getRightTurnAngle() {
        return rightTurnAngle;
    }

    public void setRightTurnAngle(String rightTurnAngle) {
        this.rightTurnAngle = rightTurnAngle;
    }

    public String getLeftMaxTurnAngle() {
        return leftMaxTurnAngle;
    }

    public void setLeftMaxTurnAngle(String leftMaxTurnAngle) {
        this.leftMaxTurnAngle = leftMaxTurnAngle;
    }

    public String getRightMaxTurnAngle() {
        return rightMaxTurnAngle;
    }

    public void setRightMaxTurnAngle(String rightMaxTurnAngle) {
        this.rightMaxTurnAngle = rightMaxTurnAngle;
    }

    public String getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(String wheelbase) {
        this.wheelbase = wheelbase;
    }

    @Override
    public ReferData copy() {
        ReferData copy = new ReferData();

        copy.setManId(manId);
        copy.setManInfo(manInfo);

        copy.setVehicleId(vehicleId);
        copy.setVehicleInfo(vehicleInfo);

        copy.setRealYear(realYear);
        copy.setStartYear(startYear);
        copy.setEndYear(endYear);

        copy.setMaxThrust(maxThrust);
        copy.setWheelbase(wheelbase);
        copy.setFrontWheel(frontWheel);
        copy.setRearWheel(rearWheel);


        copy.setFrontTotalToe(frontTotalToe.copy());
        copy.setLeftFrontToe(leftFrontToe.copy());
        copy.setRightFrontToe(rightFrontToe.copy());

        copy.setLeftFrontCamber(leftFrontCamber.copy());
        copy.setRightFrontCamber(rightFrontCamber.copy());

        copy.setLeftCaster(leftCaster.copy());
        copy.setRightCaster(rightCaster.copy());

        copy.setLeftKpi(leftKpi.copy());
        copy.setRightKpi(rightKpi.copy());

        copy.setRearTotalToe(rearTotalToe.copy());
        copy.setLeftRearToe(leftRearToe.copy());
        copy.setRightRearToe(rightRearToe.copy());

        copy.setLeftRearCamber(leftRearCamber.copy());
        copy.setRightRearCamber(rightRearCamber.copy());

        return copy;
    }

    public void unitConvert() {
        frontTotalToe.unitConvert(GloVariable.toeUnit);
        leftFrontToe.unitConvert(GloVariable.toeUnit);
        rightFrontToe.unitConvert(GloVariable.toeUnit);

        rearTotalToe.unitConvert(GloVariable.toeUnit);
        leftRearToe.unitConvert(GloVariable.toeUnit);
        rightRearToe.unitConvert(GloVariable.toeUnit);

        leftFrontCamber.unitConvert(GloVariable.unit);
        rightFrontCamber.unitConvert(GloVariable.unit);

        leftCaster.unitConvert(GloVariable.unit);
        rightCaster.unitConvert(GloVariable.unit);

        leftKpi.unitConvert(GloVariable.unit);
        rightKpi.unitConvert(GloVariable.unit);

        leftRearCamber.unitConvert(GloVariable.unit);
        rightRearCamber.unitConvert(GloVariable.unit);
    }

    public ValuesPair getFrontTotalToe() {
        return frontTotalToe;
    }

    public void setFrontTotalToe(ValuesPair frontTotalToe) {
        this.frontTotalToe = frontTotalToe;
    }

    public ValuesPair getLeftFrontToe() {
        return leftFrontToe;
    }

    public void setLeftFrontToe(ValuesPair leftFrontToe) {
        this.leftFrontToe = leftFrontToe;
    }

    public ValuesPair getRightFrontToe() {
        return rightFrontToe;
    }

    public void setRightFrontToe(ValuesPair rightFrontToe) {
        this.rightFrontToe = rightFrontToe;
    }

    public ValuesPair getLeftFrontCamber() {
        return leftFrontCamber;
    }

    public void setLeftFrontCamber(ValuesPair leftFrontCamber) {
        this.leftFrontCamber = leftFrontCamber;
    }

    public ValuesPair getRightFrontCamber() {
        return rightFrontCamber;
    }

    public void setRightFrontCamber(ValuesPair rightFrontCamber) {
        this.rightFrontCamber = rightFrontCamber;
    }

    public ValuesPair getLeftCaster() {
        return leftCaster;
    }

    public void setLeftCaster(ValuesPair leftCaster) {
        this.leftCaster = leftCaster;
    }

    public ValuesPair getRightCaster() {
        return rightCaster;
    }

    public void setRightCaster(ValuesPair rightCaster) {
        this.rightCaster = rightCaster;
    }

    public ValuesPair getLeftKpi() {
        return leftKpi;
    }

    public void setLeftKpi(ValuesPair leftKpi) {
        this.leftKpi = leftKpi;
    }

    public ValuesPair getRightKpi() {
        return rightKpi;
    }

    public void setRightKpi(ValuesPair rightKpi) {
        this.rightKpi = rightKpi;
    }

    public ValuesPair getRearTotalToe() {
        return rearTotalToe;
    }

    public void setRearTotalToe(ValuesPair rearTotalToe) {
        this.rearTotalToe = rearTotalToe;
    }

    public ValuesPair getLeftRearToe() {
        return leftRearToe;
    }

    public void setLeftRearToe(ValuesPair leftRearToe) {
        this.leftRearToe = leftRearToe;
    }

    public ValuesPair getRightRearToe() {
        return rightRearToe;
    }

    public void setRightRearToe(ValuesPair rightRearToe) {
        this.rightRearToe = rightRearToe;
    }

    public ValuesPair getLeftRearCamber() {
        return leftRearCamber;
    }

    public void setLeftRearCamber(ValuesPair leftRearCamber) {
        this.leftRearCamber = leftRearCamber;
    }

    public ValuesPair getRightRearCamber() {
        return rightRearCamber;
    }

    public void setRightRearCamber(ValuesPair rightRearCamber) {
        this.rightRearCamber = rightRearCamber;
    }

    public String getManId() {
        return manId;
    }

    public void setManId(String manId) {
        this.manId = manId;
    }

    public String getManInfo() {
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
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getRealYear() {
        return realYear;
    }

    public void setRealYear(String realYear) {
        this.realYear = realYear;
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

    public String getRearWheel() {
        return rearWheel;
    }

    public void setRearWheel(String rearWheel) {
        this.rearWheel = rearWheel;
    }

    public String getFrontWheel() {
        return frontWheel;
    }

    public void setFrontWheel(String frontWheel) {
        this.frontWheel = frontWheel;
    }

    public String getMaxThrust() {
        return maxThrust;
    }

    public void setMaxThrust(String maxThrust) {
        this.maxThrust = maxThrust;
    }

    public void combine(ReferData other) {
        if (other == null) return;

        if (other.getFrontTotalToe() != null) {
            this.frontTotalToe = other.getFrontTotalToe();
            this.leftFrontToe = other.getLeftFrontToe();
            this.rightFrontToe = other.getRightFrontToe();
        }

        if (other.getLeftFrontCamber() != null) {
            this.leftFrontCamber = other.getLeftFrontCamber();
            this.rightFrontCamber = other.getRightFrontCamber();
        }

        if (other.getRearTotalToe() != null) {
            this.rearTotalToe = other.getRearTotalToe();
            this.leftRearToe = other.getLeftRearToe();
            this.rightRearToe = other.getRightRearToe();
        }

        if (other.getLeftRearCamber() != null) {
            this.leftRearCamber = other.getLeftRearCamber();
            this.rightRearCamber = other.getRightRearCamber();
        }

        if (other.getLeftKpi() != null) {
            this.leftKpi = other.getLeftKpi();
            this.rightKpi = other.getRightKpi();
        }

        if (other.getRightCaster() != null) {
            this.leftCaster = other.getLeftCaster();
            this.rightCaster = other.getRightCaster();
        }
    }

    public void addRealData(String test) {
        if (test.contains("&&")) {
            String[] array = test.split("&&");
            String[] data = array[1].split("\\|");

            if (data.length >= 18) {
                setRaiseStatus(getUpStatus(array[0]));

                frontTotalToe.setReal(originalDeal(data[1], 2));
                leftFrontToe.setReal(originalDeal(data[2], 2));
                rightFrontToe.setReal(originalDeal(data[3], 2));


                leftFrontCamber.setReal(originalDeal(data[4], 2));
                rightFrontCamber.setReal(originalDeal(data[5], 2));

                rearTotalToe.setReal(originalDeal(data[6], 2));
                leftRearToe.setReal(originalDeal(data[7], 2));
                rightRearToe.setReal(originalDeal(data[8], 2));

                leftRearCamber.setReal(originalDeal(data[9], 2));
                rightRearCamber.setReal(originalDeal(data[10], 2));

                leftCaster.setReal(originalDeal(data[11], 2));
                rightCaster.setReal(originalDeal(data[12], 2));

                leftKpi.setReal(originalDeal(data[13], 2));
                rightKpi.setReal(originalDeal(data[14], 2));

                maxThrust = originalDeal(data[15], 2);

                wheelbaseDiff = originalDeal(data[16], 0);
                wheelDiff = originalDeal(data[17], 0);

                frontTotalToe.generatePercentAndcolor(true);
                leftFrontToe.generatePercentAndcolor(true);
                rightFrontToe.generatePercentAndcolor(false);

                leftFrontCamber.generatePercentAndcolor(true);
                rightFrontCamber.generatePercentAndcolor(false);

                rearTotalToe.generatePercentAndcolor(true);
                leftRearToe.generatePercentAndcolor(true);
                rightRearToe.generatePercentAndcolor(false);

                leftRearCamber.generatePercentAndcolor(true);
                rightRearCamber.generatePercentAndcolor(false);

                leftCaster.generatePercentAndcolor(true);
                rightCaster.generatePercentAndcolor(true);

                leftKpi.generatePercentAndcolor(true);
                rightKpi.generatePercentAndcolor(false);


                if (data.length == 41) {
                    int flag;
                    try {
                        //保证vehicleId没有乱码
                        flag = Integer.parseInt(data[18]);
                    } catch (NumberFormatException e) {
                        flag = 0;
                    }

                    if (flag == 0 || (vehicleId != null && vehicleId.equals(String.valueOf(flag))))
                        return;

                    vehicleId = data[18];
                    manId = data[19];
                    manInfo = data[20];
                    vehicleInfo = data[22];
                    startYear = data[23];
                    endYear = data[24];
                    realYear = data[25];

                    frontTotalToe = new ValuesPair(data[26], data[27]);
                    leftFrontToe = frontTotalToe.generateSingleToe();
                    rightFrontToe = leftFrontToe.copy();

                    leftFrontCamber = new ValuesPair(data[28], data[29]);
                    rightFrontCamber = leftFrontCamber.copy();

                    leftCaster = new ValuesPair(data[30], data[31]);
                    rightCaster = leftCaster.copy();

                    leftKpi = new ValuesPair(data[32], data[33]);
                    rightKpi = leftKpi.copy();

                    rearTotalToe = new ValuesPair(data[34], data[35]);
                    leftRearToe = rearTotalToe.generateSingleToe();
                    rightRearToe = leftRearToe.copy();

                    leftRearCamber = new ValuesPair(data[36], data[37]);
                    rightRearCamber = leftRearCamber.copy();

                    wheelbase = originalDeal(array[38], 0);
                    frontWheel = originalDeal(array[39], 0);
                    rearWheel = originalDeal(array[40], 0);

                }
            }
        }

    }

    /**
     * 数据处理
     *
     * @param str 要转换的数
     * @param n   小数点的位数
     */
    protected String originalDeal(String str, int n) {
        try {
            float flo = Float.parseFloat(str);
            String data = CommonUtil.format(flo, 2);

            if (GloVariable.initValue.equals(data) || n == 2) return data;
            return CommonUtil.format(flo, n);
        } catch (NumberFormatException e) {
            return GloVariable.initValue;
        }
    }

    protected boolean getUpStatus(String str) {
        return str.equals("1");
    }


}
