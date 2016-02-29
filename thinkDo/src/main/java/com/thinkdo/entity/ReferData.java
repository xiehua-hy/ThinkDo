package com.thinkdo.entity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.thinkdo.activity.R;
import com.thinkdo.application.MainApplication;
import com.thinkdo.util.CommonUtil;
import com.thinkdo.util.XmlInit;

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

        ReferData copy = null;
        try {
            copy = this.getClass().newInstance();
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

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return copy;
    }


    //
    public void unitConvert() {
        if (frontTotalToe != null) {
            frontTotalToe.unitConvert(MainApplication.toeUnit);
            leftFrontToe.unitConvert(MainApplication.toeUnit);
            rightFrontToe.unitConvert(MainApplication.toeUnit);

            rearTotalToe.unitConvert(MainApplication.toeUnit);
            leftRearToe.unitConvert(MainApplication.toeUnit);
            rightRearToe.unitConvert(MainApplication.toeUnit);

            leftFrontCamber.unitConvert(MainApplication.unit);
            rightFrontCamber.unitConvert(MainApplication.unit);

            leftCaster.unitConvert(MainApplication.unit);
            rightCaster.unitConvert(MainApplication.unit);

            leftKpi.unitConvert(MainApplication.unit);
            rightKpi.unitConvert(MainApplication.unit);

            leftRearCamber.unitConvert(MainApplication.unit);
            rightRearCamber.unitConvert(MainApplication.unit);
        }

        if (maxThrust != null) maxThrust.unitConvert(MainApplication.unit);

        if (wheelbase != null) {
            wheelbase.addMMUnit();
            frontWheel.addMMUnit();
            rearWheel.addMMUnit();
        }

        if (wheelbaseDiff != null) wheelbaseDiff.addMMUnit();
        if (wheelDiff != null) wheelDiff.addMMUnit();

        if (leftIncludeAngle != null) {
            leftIncludeAngle.unitConvert(MainApplication.unit);
            rightIncludeAngle.unitConvert(MainApplication.unit);
        }

        if (leftTurnAngle != null) {
            leftTurnAngle.unitConvert(MainApplication.unit);
            rightTurnAngle.unitConvert(MainApplication.unit);
        }

        if (leftMaxTurnAngle != null) {
            leftMaxTurnAngle.unitConvert(MainApplication.unit);
            rightMaxTurnAngle.unitConvert(MainApplication.unit);
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
                int i = 18;
                int flag;
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
                    vehicleInfo = data[i++];
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

            if (data.length >= 18) {
                int i = 0;
                setRaiseStatus(getUpStatus(data[i++]));

                frontTotalToe.setReal(originalDeal(data[i++], 2));
                leftFrontToe.setReal(originalDeal(data[i++], 2));
                rightFrontToe.setReal(originalDeal(data[i++], 2));

                leftFrontCamber.setReal(originalDeal(data[i++], 2));
                rightFrontCamber.setReal(originalDeal(data[i++], 2));

                rearTotalToe.setReal(originalDeal(data[i++], 2));
                leftRearToe.setReal(originalDeal(data[i++], 2));
                rightRearToe.setReal(originalDeal(data[i++], 2));

                leftRearCamber.setReal(originalDeal(data[i++], 2));
                rightRearCamber.setReal(originalDeal(data[i++], 2));

                leftCaster.setReal(originalDeal(data[i++], 2));
                rightCaster.setReal(originalDeal(data[i++], 2));

                leftKpi.setReal(originalDeal(data[i++], 2));
                rightKpi.setReal(originalDeal(data[i++], 2));

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

                rearTotalToe.generatePercentAndColor(true);
                leftRearToe.generatePercentAndColor(true);
                rightRearToe.generatePercentAndColor(false);

                leftRearCamber.generatePercentAndColor(false);
                rightRearCamber.generatePercentAndColor(true);

                leftCaster.generatePercentAndColor(true);
                rightCaster.generatePercentAndColor(true);

                leftKpi.generatePercentAndColor(true);
                rightKpi.generatePercentAndColor(false);
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

            if (MainApplication.initValue.equals(data) || n == 2) return data;
            return CommonUtil.format(flo, n);
        } catch (NumberFormatException e) {
            return MainApplication.initValue;
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
            if (array[i] == null) array[i] = MainApplication.initValue;
            buff.append(String.format("%s|", array[i]));
        }
        return buff.toString();

    }

    public void initEmptyValue() {
        frontTotalToe = initEmptyValue(frontTotalToe);
        leftFrontToe = initEmptyValue(leftFrontToe);
        rightFrontToe = initEmptyValue(rightFrontToe);

        leftFrontCamber = initEmptyValue(leftFrontCamber);
        rightFrontCamber = initEmptyValue(rightFrontCamber);

        leftCaster = initEmptyValue(leftCaster);
        rightCaster = initEmptyValue(rightCaster);

        leftKpi = initEmptyValue(leftKpi);
        rightKpi = initEmptyValue(rightKpi);

        rearTotalToe = initEmptyValue(rearTotalToe);
        leftRearToe = initEmptyValue(leftRearToe);
        rightRearToe = initEmptyValue(rightRearToe);

        leftRearCamber = initEmptyValue(leftRearCamber);
        rightRearCamber = initEmptyValue(rightRearCamber);

        maxThrust = initEmptyValue(maxThrust);

        wheelbase = initEmptyValue(wheelbase);
        frontWheel = initEmptyValue(frontWheel);
        rearWheel = initEmptyValue(rearWheel);

        wheelbaseDiff = initEmptyValue(wheelbaseDiff);
        wheelDiff = initEmptyValue(wheelDiff);

        leftIncludeAngle = initEmptyValue(leftIncludeAngle);
        rightIncludeAngle = initEmptyValue(rightIncludeAngle);

        leftTurnAngle = initEmptyValue(leftTurnAngle);
        rightTurnAngle = initEmptyValue(rightTurnAngle);

        leftMaxTurnAngle = initEmptyValue(leftMaxTurnAngle);
        rightMaxTurnAngle = initEmptyValue(rightMaxTurnAngle);
    }

    private ValuesPair initEmptyValue(ValuesPair values) {
        if (values == null) {
            values = new ValuesPair();
        }

        values.initEmptyValue();
        return values;
    }

    public ValuesPair[] getValuesPairs() {
        ValuesPair[] pair = {
                frontTotalToe, leftFrontToe, rightFrontToe, leftFrontCamber, rightFrontCamber,
                leftCaster, rightCaster, leftKpi, leftKpi,
                rearTotalToe, leftRearToe, rightRearToe, leftRearCamber, rightRearCamber,
                maxThrust, wheelbaseDiff, wheelDiff, leftIncludeAngle, rightIncludeAngle,
                leftTurnAngle, rightTurnAngle, leftMaxTurnAngle, rightMaxTurnAngle
        };

        return pair;
    }

    public String getPrintData(CustomerModel custom, TestVehicleInfoModel info) {
        if (custom == null || info == null) return null;

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MainApplication.context);
        String initValue = "";

        String garageName = shared.getString(MainApplication.garageNameKey, initValue);
        String garageAddress = shared.getString(MainApplication.garageAddressKey, initValue);
        String garageTel = shared.getString(MainApplication.garageTelKey, initValue);
        String garagePostCode = shared.getString(MainApplication.garagePostCodeKey, initValue);
        String garageFax = shared.getString(MainApplication.garageFaxKey, initValue);

        String array[] = new String[175];
        array[0] = initValue;

        array[1] = XmlInit.getResourceNodeValue("String1600");
        array[2] = XmlInit.getResourceNodeValue("String5012");
        array[3] = XmlInit.getResourceNodeValue("String1612");
        array[4] = XmlInit.getResourceNodeValue("String1221");
        array[5] = XmlInit.getResourceNodeValue("String1222");
        array[6] = XmlInit.getResourceNodeValue("String1223");
        array[7] = XmlInit.getResourceNodeValue("String1613");

        array[8] = initValue;

        array[9] = custom.getName();
        array[10] = info.getPlateNum();
        array[11] = info.getManInfo();
        array[12] = info.getModel();
        array[13] = info.getStartYear();
        array[14] = info.getEndYear();
        array[15] = info.getCurUnit();

        array[16] = initValue;
        array[17] = XmlInit.getResourceNodeValue("String3001");
        array[18] = XmlInit.getResourceNodeValue("String3002");
        array[19] = initValue;
        array[20] = XmlInit.getResourceNodeValue("String3004");
        array[21] = initValue;

        array[22] = info.getTestDate();
        array[23] = info.getOperator();

        array[24] = initValue;
        array[25] = info.getMalfunction();
        array[26] = XmlInit.getResourceNodeValue("String3006");
        array[27] = info.getTestDate();
        array[28] = garageName;
        array[29] = garageTel;
        array[30] = XmlInit.getResourceNodeValue("String1601");

        array[31] = custom.getCompany();
        array[33] = custom.getAddress();

        array[32] = XmlInit.getResourceNodeValue("String1602");
        array[34] = garageAddress;
        array[35] = XmlInit.getResourceNodeValue("String1233");
        array[36] = info.getRemark();
        array[37] = initValue;
        array[38] = initValue;
        array[39] = initValue;
        array[40] = garageTel;

        array[41] = garageFax;
        array[42] = garagePostCode;

        array[43] = initValue;
        array[44] = initValue;
        array[45] = initValue;
        array[46] = initValue;
        array[47] = initValue;
        array[48] = initValue;
        array[49] = initValue;

        array[50] = MainApplication.context.getString(R.string.parameters);
        array[51] = MainApplication.context.getString(R.string.before);
        array[52] = MainApplication.context.getString(R.string.min);
        array[53] = MainApplication.context.getString(R.string.max);
        array[54] = MainApplication.context.getString(R.string.after);

        array[55] = MainApplication.context.getString(R.string.front_total_toe);
        array[60] = MainApplication.context.getString(R.string.left_front_toe);
        array[65] = MainApplication.context.getString(R.string.right_front_toe);
        array[70] = MainApplication.context.getString(R.string.left_front_camber);
        array[75] = MainApplication.context.getString(R.string.right_front_camber);
        array[80] = MainApplication.context.getString(R.string.left_caster);
        array[85] = MainApplication.context.getString(R.string.right_caster);
        array[90] = MainApplication.context.getString(R.string.left_kpi);
        array[95] = MainApplication.context.getString(R.string.right_kpi);
        array[100] = MainApplication.context.getString(R.string.rear_total_toe);
        array[105] = MainApplication.context.getString(R.string.left_rear_toe);
        array[110] = MainApplication.context.getString(R.string.right_rear_toe);
        array[115] = MainApplication.context.getString(R.string.left_rear_camber);
        array[120] = MainApplication.context.getString(R.string.right_rear_camber);
        array[125] = MainApplication.context.getString(R.string.thrust_angle);
        array[130] = MainApplication.context.getString(R.string.wheelbase_diff);
        array[135] = MainApplication.context.getString(R.string.wheel_diff);
        array[140] = MainApplication.context.getString(R.string.left_include_angle);
        array[145] = MainApplication.context.getString(R.string.right_include_angle);
        array[150] = MainApplication.context.getString(R.string.left_turn_angle);
        array[155] = MainApplication.context.getString(R.string.right_turn_angle);
        array[160] = MainApplication.context.getString(R.string.left_max_turn_angle);
        array[165] = MainApplication.context.getString(R.string.right_max_turn_angle);

        if (frontTotalToe != null) {
            array[56] = frontTotalToe.getPreReal();
            array[57] = frontTotalToe.getMin();
            array[58] = frontTotalToe.getMax();
            array[59] = frontTotalToe.getReal();

            array[61] = leftFrontToe.getPreReal();
            array[62] = leftFrontToe.getMin();
            array[63] = leftFrontToe.getMax();
            array[64] = leftFrontToe.getReal();

            array[66] = rightFrontToe.getPreReal();
            array[67] = rightFrontToe.getMin();
            array[68] = rightFrontToe.getMax();
            array[69] = rightFrontToe.getReal();

            array[71] = leftFrontCamber.getPreReal();
            array[72] = leftFrontCamber.getMin();
            array[73] = leftFrontCamber.getMax();
            array[74] = leftFrontCamber.getReal();

            array[76] = rightFrontCamber.getPreReal();
            array[77] = rightFrontCamber.getMin();
            array[78] = rightFrontCamber.getMax();
            array[79] = rightFrontCamber.getReal();

            array[81] = leftCaster.getPreReal();
            array[82] = leftCaster.getMin();
            array[83] = leftCaster.getMax();
            array[84] = leftCaster.getReal();

            array[86] = rightCaster.getPreReal();
            array[87] = rightCaster.getMin();
            array[88] = rightCaster.getMax();
            array[89] = rightCaster.getReal();

            array[91] = leftKpi.getPreReal();
            array[92] = leftKpi.getMin();
            array[93] = leftKpi.getMax();
            array[94] = leftKpi.getReal();

            array[96] = rightKpi.getPreReal();
            array[97] = rightKpi.getMin();
            array[98] = rightKpi.getMax();
            array[99] = rightKpi.getReal();

            array[101] = rearTotalToe.getPreReal();
            array[102] = rearTotalToe.getMin();
            array[103] = rearTotalToe.getMax();
            array[104] = rearTotalToe.getReal();

            array[106] = leftRearToe.getPreReal();
            array[107] = leftRearToe.getMin();
            array[108] = leftRearToe.getMax();
            array[109] = leftRearToe.getReal();

            array[111] = rightRearToe.getPreReal();
            array[112] = rightRearToe.getMin();
            array[113] = rightRearToe.getMax();
            array[114] = rightRearToe.getReal();

            array[116] = leftRearCamber.getPreReal();
            array[117] = leftRearCamber.getMin();
            array[118] = leftRearCamber.getMax();
            array[119] = leftRearCamber.getReal();

            array[121] = rightRearCamber.getPreReal();
            array[122] = rightRearCamber.getMin();
            array[123] = rightRearCamber.getMax();
            array[124] = rightRearCamber.getReal();

            array[126] = maxThrust.getPreReal();
            array[127] = maxThrust.getMin();
            array[128] = maxThrust.getMax();
            array[129] = maxThrust.getReal();

            array[131] = wheelbaseDiff.getPreReal();
            array[132] = wheelbaseDiff.getMin();
            array[133] = wheelbaseDiff.getMax();
            array[134] = wheelbaseDiff.getReal();

            array[136] = wheelDiff.getPreReal();
            array[137] = wheelDiff.getMin();
            array[138] = wheelDiff.getMax();
            array[139] = wheelDiff.getReal();
        }


        if (leftIncludeAngle != null) {
            array[141] = leftIncludeAngle.getPreReal();
            array[142] = leftIncludeAngle.getMin();
            array[143] = leftIncludeAngle.getMax();
            array[144] = leftIncludeAngle.getReal();
        }

        if (rightIncludeAngle != null) {
            array[146] = rightIncludeAngle.getPreReal();
            array[147] = rightIncludeAngle.getMin();
            array[148] = rightIncludeAngle.getMax();
            array[149] = rightIncludeAngle.getReal();

        }

        if (leftTurnAngle != null) {
            array[151] = leftTurnAngle.getPreReal();
            array[152] = leftTurnAngle.getMin();
            array[153] = leftTurnAngle.getMax();
            array[154] = leftTurnAngle.getReal();
        }

        if (rightTurnAngle != null) {
            array[156] = rightTurnAngle.getPreReal();
            array[157] = rightTurnAngle.getMin();
            array[158] = rightTurnAngle.getMax();
            array[159] = rightTurnAngle.getReal();
        }


        if (leftMaxTurnAngle != null) {
            array[161] = leftMaxTurnAngle.getPreReal();
            array[162] = leftMaxTurnAngle.getMin();
            array[163] = leftMaxTurnAngle.getMax();
            array[164] = leftMaxTurnAngle.getReal();
        }

        if (rightMaxTurnAngle != null) {
            array[166] = rightMaxTurnAngle.getPreReal();
            array[167] = rightMaxTurnAngle.getMin();
            array[168] = rightMaxTurnAngle.getMax();
            array[169] = rightMaxTurnAngle.getReal();
        }


        array[171] = MainApplication.unit.getValue();
        array[172] = "500.00";
        array[173] = info.getWheelBase();
        array[174] = info.getFrontTread();

        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) array[i] = "";
            buff.append(String.format("%s|", array[i]));
        }

        return buff.toString();

    }

}
