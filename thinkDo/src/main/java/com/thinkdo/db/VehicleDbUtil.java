package com.thinkdo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.entity.ValuesPair;
import com.thinkdo.entity.WeightParam;
import com.thinkdo.util.CommonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleDbUtil {
    public SQLiteDatabase getWriteDb() {

        String path = GloVariable.context.getDatabasePath(GloVariable.carSqliteName).getPath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SQLiteDatabase getReadDb() {
        String path = GloVariable.context.getDatabasePath(GloVariable.carSqliteName).getPath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询汽车制造商名称
     *
     * @param manId 制造商ID
     */
    public String queryManufacturerInfo(int manId) {
        String info = null;
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;
        String sqlWhere = String.format("Manufact1 = %s", manId);
        Cursor cur = db.query("StandVehmanfacturers", null, sqlWhere, null, null, null, null);
        if (cur.moveToNext()) {
            info = cur.getString(cur.getColumnIndex("Manufact4"));
        }
        cur.close();
        db.close();
        return info;
    }

    /**
     * 当pinyin == null 查询某制造厂的所有车型 <br>
     * 当pinyin != null 根据拼音索引查询某一款车型
     *
     * @param manId  制造商ID
     * @param pinyin 拼音索引
     */
    public List<String> queryAllCar(String manId, String pinyin) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        List<String> data = new ArrayList<>();
        String sqlWhere;
        if (pinyin == null) {
            sqlWhere = String.format("Model4 = %s", manId);
        } else {
            String field = (CommonUtil.isChinese(pinyin)) ? "Model5" : "PyIndex";
            sqlWhere = String.format("Model4 = %s And %s like \'%%%s%%\'", manId, field, pinyin);
        }

        Cursor cur = db.query("StandTypelevel", null, sqlWhere, null, null, null, null);

        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex("Model1"));
            int startY = cur.getInt(cur.getColumnIndex("Model2"));
            int endY = cur.getInt(cur.getColumnIndex("Model3"));
            String type = cur.getString(cur.getColumnIndex("Model5"));

            for (int i = startY; i <= endY; i++) {
                String brand = type.split(CommonUtil.findSpecialChar(type))[0];
                data.add(String.format("%s;%s;%s;%s", brand, String.valueOf(i), type, id));
            }
        }
        cur.close();
        db.close();
        Collections.sort(data);
        return data;
    }

    public ReferData queryReferData(String vehicleId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        ReferData data = new ReferData();

        Cursor cur = db.query("StandTypelevel", new String[]{"Model2", "Model3", "Model5"}, "Model1=" + vehicleId, null, null, null, null);
        if (cur.moveToFirst()) {
            data.setStartYear(cur.getString(cur.getColumnIndex("Model2")));
            data.setEndYear(cur.getString(cur.getColumnIndex("Model3")));
            data.setVehicleInfo(cur.getString(cur.getColumnIndex("Model5")));
        }

        String sqlWhere = String.format("Specs1 = (select Model15 from StandTypelevel where Model1= %s )", vehicleId);

        cur = db.query("Standdeterment", null, sqlWhere, null, null, null, null);
        if (cur.moveToNext()) {

            float leftCaster = cur.getFloat(cur.getColumnIndex("Specs2"));
            float leftCasterMin = cur.getFloat(cur.getColumnIndex("Specs3"));
            float leftCasterMax = cur.getFloat(cur.getColumnIndex("Specs4"));

            float rightCaster = cur.getFloat(cur.getColumnIndex("Specs5"));
            float rightCasterMin = cur.getFloat(cur.getColumnIndex("Specs6"));
            float rightCasterMax = cur.getFloat(cur.getColumnIndex("Specs7"));

            float leftFrontCamber = cur.getFloat(cur.getColumnIndex("Specs9"));
            float leftFrontCamberMin = cur.getFloat(cur.getColumnIndex("Specs10"));
            float leftFrontCamberMax = cur.getFloat(cur.getColumnIndex("Specs11"));

            float rightFrontCamber = cur.getFloat(cur.getColumnIndex("Specs12"));
            float rightFrontCamberMin = cur.getFloat(cur.getColumnIndex("Specs13"));
            float rightFrontCamberMax = cur.getFloat(cur.getColumnIndex("Specs14"));

            float leftKPI = cur.getFloat(cur.getColumnIndex("Specs25"));
            float leftKPIMin = cur.getFloat(cur.getColumnIndex("Specs26"));
            float leftKPIMax = cur.getFloat(cur.getColumnIndex("Specs27"));

            float rightKPI = cur.getFloat(cur.getColumnIndex("Specs28"));
            float rightKPIMin = cur.getFloat(cur.getColumnIndex("Specs29"));
            float rightKPIMax = cur.getFloat(cur.getColumnIndex("Specs30"));

            float frontTotalToe = cur.getFloat(cur.getColumnIndex("Specs16"));
            float frontTotalToeMin = cur.getFloat(cur.getColumnIndex("Specs17"));
            float frontTotalToeMax = cur.getFloat(cur.getColumnIndex("Specs18"));

            float rightRearCamber = cur.getFloat(cur.getColumnIndex("Specs47"));
            float rightRearCamberMin = cur.getFloat(cur.getColumnIndex("Specs48"));
            float rightRearCamberMax = cur.getFloat(cur.getColumnIndex("Specs49"));

            float leftRearCamber = cur.getFloat(cur.getColumnIndex("Specs44"));
            float leftRearCamberMin = cur.getFloat(cur.getColumnIndex("Specs45"));
            float leftRearCamberMax = cur.getFloat(cur.getColumnIndex("Specs46"));

            float rearTotalToe = cur.getFloat(cur.getColumnIndex("Specs54"));
            float rearTotalToeMin = cur.getFloat(cur.getColumnIndex("Specs55"));
            float rearTotalToeMax = cur.getFloat(cur.getColumnIndex("Specs56"));

            float thrustAngleMax = cur.getFloat(cur.getColumnIndex("Specs57"));

            data.setFrontTotalToe(new ValuesPair(frontTotalToeMin, frontTotalToe, frontTotalToeMax));
            data.setLeftFrontToe(data.getFrontTotalToe().generateSingleToe());
            data.setRightFrontToe(data.getLeftFrontToe().copy());

            data.setLeftFrontCamber(new ValuesPair(leftFrontCamberMin, leftFrontCamber, leftFrontCamberMax));
            data.setRightFrontCamber(new ValuesPair(rightFrontCamberMin, rightFrontCamber, rightFrontCamberMax));

            data.setLeftCaster(new ValuesPair(leftCasterMin, leftCaster, leftCasterMax));
            data.setRightCaster(new ValuesPair(rightCasterMin, rightCaster, rightCasterMax));

            data.setLeftKpi(new ValuesPair(leftKPIMin, leftKPI, leftKPIMax));
            data.setRightKpi(new ValuesPair(rightKPIMin, rightKPI, rightKPIMax));

            data.setRearTotalToe(new ValuesPair(rearTotalToeMin, rearTotalToe, rearTotalToeMax));
            data.setLeftRearToe(data.getRearTotalToe().generateSingleToe());
            data.setRightRearToe(data.getLeftRearToe().copy());

            data.setLeftRearCamber(new ValuesPair(leftRearCamberMin, leftRearCamber, leftRearCamberMax));
            data.setRightRearCamber(new ValuesPair(rightRearCamberMin, rightRearCamber, rightRearCamberMax));

            data.setMaxThrust(CommonUtil.format(thrustAngleMax, 2));
        }

        sqlWhere = String.format("Dimension1 = (select Model21 from StandTypelevel where Model1= %s)", vehicleId);

        cur = db.query("StandDimensiontype", null, sqlWhere, null, null, null, null);
        if (cur.moveToFirst()) {
            float wheelbase = cur.getFloat(cur.getColumnIndex("Dimension20"));
            float frontWheel = cur.getFloat(cur.getColumnIndex("Dimension14"));
            float rearWheel = cur.getFloat(cur.getColumnIndex("Dimension17"));

            data.setWheelbase(dataInitHandle(wheelbase, 0));
            data.setFrontWheel(dataInitHandle(frontWheel, 0));
            data.setRearWheel(dataInitHandle(rearWheel, 0));
        }
        cur.close();
        db.close();
        return data;
    }

    public SpecialParams querySpecParam(String vehicleId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        Cursor cur = db.query("Standtypelevel", null, "Model1=" + vehicleId, null, null, null, null);
        SpecialParams data = new SpecialParams();
        if (cur.moveToFirst()) {
//            data.setVehicleId(vehicleId);
//            data.setWeightId(cur.getString(cur.getColumnIndex("Model19")));
//            data.setHeightFlag(cur.getString(cur.getColumnIndex("Model22")));
//            data.setHeightPicPath(cur.getString(cur.getColumnIndex("Model23")));
//            data.setLevelFlag(cur.getString(cur.getColumnIndex("Model26")));
//            data.setReferDataId(cur.getString(cur.getColumnIndex("Model15")));
//
//            data.setWeightData(queryWeight(data.getWeightId()));
//            if(!data.getHeightFlag().equals("0")){
//
//            }
//
//            if(!data.getLevelFlag().equals("0")){
//
//            }
        }

        cur.close();
        db.close();

        return data;
    }


    /**
     *  @return 当配重值不为0时 返回值; 否则返回null
     * */
    public WeightParam queryWeight(String loadId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        WeightParam data = null;
        String zero = "0";
        Cursor cur = db.query("StandProcessinfo", null, "Load1="+loadId, null, null, null, null);
        if (cur.moveToFirst()) {
            String load2 = cur.getString(cur.getColumnIndex("Load2"));
            String load3 = cur.getString(cur.getColumnIndex("Load3"));
            String load4 = cur.getString(cur.getColumnIndex("Load4"));
            String load5 = cur.getString(cur.getColumnIndex("Load5"));
            String load6 = cur.getString(cur.getColumnIndex("Load6"));
            String load7 = cur.getString(cur.getColumnIndex("Load7"));

            if (!load2.equals(zero) || !load3.equals(zero) || !load4.equals(zero) || !load5.equals(zero)
                    || !load6.equals(zero) || !load7.equals(zero)) {
                data = new WeightParam();

                data.setLeftFront(load2);
                data.setRightFront(load3);
                data.setRearSeat(load4);
                data.setLeftRear(load5);
                data.setRightRear(load6);
                data.setTrunk(load7);
            }
        }

        cur.close();
        db.close();
        return data;
    }


    public String dataInitHandle(float value, int num) {
        if (CommonUtil.format(value, 2).equals(GloVariable.initValue)) return GloVariable.initValue;

        return CommonUtil.format(value, num);
    }
}
