package com.thinkdo.entity;

import com.thinkdo.util.CommonUtil;

import java.io.Serializable;

/**
 * Created by xh on 15/5/10.
 */
public class ReferData implements Serializable, CopyProtocol<ReferData> {


    //状态标志位
    protected boolean saved = false;
    protected boolean flush = false;

    protected boolean raiseStatus;
    //车辆信息
    protected String manId;
    protected String manInfo;

    protected String vehicleId;
    protected String vehicleInfo;

    protected String realYear;
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
    protected ValuesPair maxThrust;

    protected ValuesPair wheelbase;
    protected ValuesPair frontWheel;
    protected ValuesPair rearWheel;

    protected ValuesPair wheelbaseDiff; //轴距差
    protected ValuesPair wheelDiff;    //轮距差
    //包容角
    protected ValuesPair leftIncludeAngle;
    protected ValuesPair rightIncludeAngle;

    //20度前张角
    protected ValuesPair leftTurnAngle;
    protected ValuesPair rightTurnAngle;

    //最大转向角
    protected ValuesPair leftMaxTurnAngle;
    protected ValuesPair rightMaxTurnAngle;

    public void init() {
        frontTotalToe = new ValuesPair();
        leftFrontToe = new ValuesPair();
        rightFrontToe = new ValuesPair();

        leftFrontCamber = new ValuesPair();
        rightFrontCamber = new ValuesPair();

        leftCaster = new ValuesPair();
        rightCaster = new ValuesPair();

        leftKpi = new ValuesPair();
        rightKpi = new ValuesPair();

        rearTotalToe = new ValuesPair();
        leftRearToe = new ValuesPair();
        rightRearToe = new ValuesPair();

        leftRearCamber = new ValuesPair();
        rightRearCamber = new ValuesPair();

        maxThrust = new ValuesPair();
        wheelbaseDiff = new ValuesPair();
        wheelDiff = new ValuesPair();

    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean save) {
        this.saved = save;
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


    @Override
    public ReferData copy() {
        ReferData copy = new ReferData();
        copy.setSaved(saved);
        copy.setFlush(flush);
        copy.setRaiseStatus(raiseStatus);

        copy.setManId(manId);
        copy.setManInfo(manInfo);

        copy.setVehicleId(vehicleId);
        copy.setVehicleInfo(vehicleInfo);

        copy.setRealYear(realYear);
        copy.setStartYear(startYear);
        copy.setEndYear(endYear);

        if (frontTotalToe != null) {

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
        }

        if (wheelbase != null) {
            copy.setWheelbase(wheelbase.copy());
            copy.setFrontWheel(frontWheel.copy());
            copy.setRearWheel(rearWheel.copy());
        }

        if (maxThrust != null) copy.setMaxThrust(maxThrust.copy());

        if (wheelbaseDiff != null) copy.setWheelbaseDiff(wheelbaseDiff.copy());
        if (wheelDiff != null) copy.setWheelDiff(wheelDiff.copy());

        if (leftIncludeAngle != null) {
            copy.setLeftIncludeAngle(leftIncludeAngle.copy());
            copy.setRightIncludeAngle(rightIncludeAngle.copy());
        }

        if (leftTurnAngle != null) {
            copy.setLeftTurnAngle(leftTurnAngle.copy());
            copy.setRightTurnAngle(rightTurnAngle.copy());
        }

        if (leftMaxTurnAngle != null) {
            copy.setLeftMaxTurnAngle(leftMaxTurnAngle.copy());
            copy.setRightMaxTurnAngle(rightMaxTurnAngle.copy());
        }
        return copy;
    }


    //
    public void unitConvert() {
        if (frontTotalToe != null) {
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

        if (maxThrust != null) maxThrust.unitConvert(GloVariable.unit);

        if (wheelbase != null) {
            wheelbase.addMMUnit();
            frontWheel.addMMUnit();
            rearWheel.addMMUnit();
        }

        if (wheelbaseDiff != null) wheelbaseDiff.addMMUnit();
        if (wheelDiff != null) wheelDiff.addMMUnit();

        if (leftIncludeAngle != null) {
            leftIncludeAngle.unitConvert(GloVariable.unit);
            rightIncludeAngle.unitConvert(GloVariable.unit);
        }

        if (leftTurnAngle != null) {
            leftTurnAngle.unitConvert(GloVariable.unit);
            rightTurnAngle.unitConvert(GloVariable.unit);
        }

        if (leftMaxTurnAngle != null) {
            leftMaxTurnAngle.unitConvert(GloVariable.unit);
            rightMaxTurnAngle.unitConvert(GloVariable.unit);
        }

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

    public ValuesPair getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(ValuesPair wheelbase) {
        this.wheelbase = wheelbase;
    }

    public ValuesPair getMaxThrust() {
        return maxThrust;
    }

    public void setMaxThrust(ValuesPair maxThrust) {
        this.maxThrust = maxThrust;
    }

    public ValuesPair getFrontWheel() {
        return frontWheel;
    }

    public void setFrontWheel(ValuesPair frontWheel) {
        this.frontWheel = frontWheel;
    }

    public ValuesPair getRightMaxTurnAngle() {
        return rightMaxTurnAngle;
    }

    public void setRightMaxTurnAngle(ValuesPair rightMaxTurnAngle) {
        this.rightMaxTurnAngle = rightMaxTurnAngle;
    }

    public ValuesPair getRearWheel() {
        return rearWheel;
    }

    public void setRearWheel(ValuesPair rearWheel) {
        this.rearWheel = rearWheel;
    }

    public ValuesPair getWheelbaseDiff() {
        return wheelbaseDiff;
    }

    public void setWheelbaseDiff(ValuesPair wheelbaseDiff) {
        this.wheelbaseDiff = wheelbaseDiff;
    }

    public ValuesPair getWheelDiff() {
        return wheelDiff;
    }

    public void setWheelDiff(ValuesPair wheelDiff) {
        this.wheelDiff = wheelDiff;
    }

    public ValuesPair getLeftIncludeAngle() {
        return leftIncludeAngle;
    }

    public void setLeftIncludeAngle(ValuesPair leftIncludeAngle) {
        this.leftIncludeAngle = leftIncludeAngle;
    }

    public ValuesPair getRightIncludeAngle() {
        return rightIncludeAngle;
    }

    public void setRightIncludeAngle(ValuesPair rightIncludeAngle) {
        this.rightIncludeAngle = rightIncludeAngle;
    }

    public ValuesPair getLeftTurnAngle() {
        return leftTurnAngle;
    }

    public void setLeftTurnAngle(ValuesPair leftTurnAngle) {
        this.leftTurnAngle = leftTurnAngle;
    }

    public ValuesPair getRightTurnAngle() {
        return rightTurnAngle;
    }

    public void setRightTurnAngle(ValuesPair rightTurnAngle) {
        this.rightTurnAngle = rightTurnAngle;
    }

    public ValuesPair getLeftMaxTurnAngle() {
        return leftMaxTurnAngle;
    }

    public void setLeftMaxTurnAngle(ValuesPair leftMaxTurnAngle) {
        this.leftMaxTurnAngle = leftMaxTurnAngle;
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
        if (frontTotalToe == null) {
            init();
        }

        if (test.contains("&&")) {
            String[] array = test.split("&&");
            String[] data = array[1].split("\\|");

            if (data.length == 41) {
                int flag;
                try {
                    //保证vehicleId没有乱码
                    flag = Integer.parseInt(data[18]);
                } catch (NumberFormatException e) {
                    flag = 0;
                }


                if (flag != 0 && (vehicleId != null && !vehicleId.equals(String.valueOf(flag)))) {

                    setFlush(true);
                    vehicleId = data[18];
                    manId = data[19];
                    manInfo = data[20];
                    vehicleInfo = data[22];
                    startYear = data[23];
                    endYear = data[24];
                    realYear = data[25];

                    frontTotalToe.setReferValue(data[26], data[27]);
                    leftFrontToe.setReferValue(frontTotalToe.generateSingleToe());
                    rightFrontToe.setReferValue(leftFrontToe);

                    leftFrontCamber.setReferValue(data[28], data[29]);
                    rightFrontCamber.setReferValue(leftFrontCamber);

                    leftCaster.setReferValue(data[30], data[31]);
                    rightCaster.setReferValue(leftCaster);

                    leftKpi.setReferValue(data[32], data[33]);
                    rightKpi.setReferValue(leftKpi);

                    rearTotalToe.setReferValue(data[34], data[35]);
                    leftRearToe.setReferValue(rearTotalToe.generateSingleToe());
                    rightRearToe.setReferValue(leftRearToe);

                    leftRearCamber.setReferValue(data[36], data[37]);
                    rightRearCamber.setReferValue(leftRearCamber);

                    if (wheelbase == null) {
                        wheelbase = new ValuesPair(originalDeal(data[38], 0));
                        frontWheel = new ValuesPair(originalDeal(data[39], 0));
                        rearWheel = new ValuesPair(originalDeal(data[40], 0));
                    } else {
                        wheelbase.setReferValue(originalDeal(data[38], 0));
                        frontWheel.setReferValue(originalDeal(data[39], 0));
                        rearWheel.setReferValue(originalDeal(data[40], 0));
                    }
                }

            }

            if (data.length >= 18) {
                setRaiseStatus(getUpStatus(data[0]));

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

                maxThrust.setReal(originalDeal(data[15], 2));

                if (wheelbaseDiff == null) {
                    wheelbaseDiff = new ValuesPair(originalDeal(data[16], 0));
                    wheelDiff = new ValuesPair((originalDeal(data[17], 0)));
                } else {
                    wheelbaseDiff.setReal(originalDeal(data[16], 0));
                    wheelDiff.setReal(originalDeal(data[17], 0));
                }

                frontTotalToe.generatePercentAndcolor(true);
                leftFrontToe.generatePercentAndcolor(true);
                rightFrontToe.generatePercentAndcolor(false);

                leftFrontCamber.generatePercentAndcolor(false);
                rightFrontCamber.generatePercentAndcolor(true);

                rearTotalToe.generatePercentAndcolor(true);
                leftRearToe.generatePercentAndcolor(true);
                rightRearToe.generatePercentAndcolor(false);

                leftRearCamber.generatePercentAndcolor(false);
                rightRearCamber.generatePercentAndcolor(true);

                leftCaster.generatePercentAndcolor(true);
                rightCaster.generatePercentAndcolor(true);

                leftKpi.generatePercentAndcolor(true);
                rightKpi.generatePercentAndcolor(false);
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

    public String getSynchData() {
        StringBuilder buff = new StringBuilder();
        String[] array = new String[23];

        array[0] = vehicleId;

        array[1] = manId;
        array[2] = manInfo;

        array[3] = vehicleId;
        array[4] = vehicleInfo;

        array[5] = startYear;
        array[6] = endYear;
        array[7] = realYear;

        if (leftFrontToe != null) {
            array[8] = leftFrontToe.getMin();
            array[9] = leftFrontToe.getMax();

            array[10] = leftFrontCamber.getMin();
            array[11] = leftFrontCamber.getMax();

            array[12] = leftCaster.getMin();
            array[13] = leftCaster.getMax();

            array[14] = leftKpi.getMin();
            array[15] = leftKpi.getMax();

            array[16] = leftRearToe.getMin();
            array[17] = leftRearToe.getMax();

            array[18] = leftRearCamber.getMin();
            array[19] = leftRearCamber.getMax();
        }

        if (wheelbase != null) {
            array[20] = wheelbase.getMid();
            array[21] = frontWheel.getMid();
            array[22] = rearWheel.getMid();
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) array[i] = GloVariable.initValue;
            buff.append(String.format("%s|", array[i]));
        }
        return buff.toString();

    }


}
