package com.thinkdo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.HeightParam;
import com.thinkdo.entity.LevelParam;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.SpecialParams;
import com.thinkdo.entity.ValuesPair;
import com.thinkdo.entity.WeightParam;
import com.thinkdo.util.CommonUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (endY == 0) endY = startY;
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
            String weightId = cur.getString(cur.getColumnIndex("Model19"));
            String heightFlag = cur.getString(cur.getColumnIndex("Model22"));
            String heightPicPath = cur.getString(cur.getColumnIndex("Model23"));

            String levelFlag = cur.getString(cur.getColumnIndex("Model26"));

            WeightParam weightParam = queryWeight(weightId);

            if (weightParam != null) {
                data.setWeightParam(weightParam);
            }

            if (!heightFlag.equals("0")) {
                HeightParam heightParam = queryHeightParam(vehicleId, heightFlag);
                if (heightParam != null) {
                    heightParam.setVehicleId(vehicleId);
                    heightParam.setHeightFlag(heightFlag);

                    //获取图片路径
                    String where = String.format("Manufact1 = (select Model4 from StandTypelevel where Model1 = %s)", vehicleId);
                    cur = db.query("StandVehmanfacturers", new String[]{"Manufact4"}, where, null, null, null, null);
                    if (cur.moveToFirst()) {
                        String manInfo = cur.getString(cur.getColumnIndex("Manufact4"));
                        heightParam.setHeightPicPath(getPicPath(manInfo, heightPicPath));
                    }

                    data.setHeightParam(heightParam);
                }
            }

            if (!levelFlag.equals("0")) {
                LevelParam levelParam = queryLevelParam(vehicleId);

                if (levelParam != null) {
                    levelParam.setVehicleId(vehicleId);
                    data.setLevelParam(levelParam);
                }
            }
        }
        cur.close();
        db.close();
        return data;
    }


    /**
     * @return 当配重值不为0时 返回值; 否则返回null
     */
    public WeightParam queryWeight(String loadId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        WeightParam data = null;
        String zero = "0";
        Cursor cur = db.query("StandProcessinfo", null, "Load1=" + loadId, null, null, null, null);
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

    public HeightParam queryHeightParam(String vehicleId, String flag) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        HeightParam data = new HeightParam();
        if (flag.equals("1")) {
            Cursor cur = db.query("Standdeterment", null, "Specs1=" + vehicleId, null, null, null, null);
            if (cur.moveToNext()) {

                float frontMid = cur.getFloat(cur.getColumnIndex("Specs70"));
                float frontMax = cur.getFloat(cur.getColumnIndex("Specs71"));
                float frontMin = cur.getFloat(cur.getColumnIndex("Specs72"));

                float rearMid = cur.getFloat(cur.getColumnIndex("Specs73"));
                float rearMax = cur.getFloat(cur.getColumnIndex("Specs74"));
                float rearMin = cur.getFloat(cur.getColumnIndex("Specs75"));

                String frontString = cur.getString(cur.getColumnIndex("Specs86"));
                String rearString = cur.getString(cur.getColumnIndex("Specs87"));

                data.setFrontHeight(new ValuesPair(frontMin, frontMid, frontMax, frontString));
                data.setRearHeight(new ValuesPair(rearMin, rearMid, rearMax, rearString));

                data.getFrontHeight().format(1);
                data.getRearHeight().format(1);

            }
            cur.close();
        } else {
            Float frontHeightMin = null, rearHeightMin = null;

            String sqlWhere = String.format("ModelId =%s And abs(FrontHeighMin-99.98999)>0.001", vehicleId);
            Cursor cur = db.query("RideHeightData", new String[]{"min(FrontHeighMin) FrontHeighMin"}, sqlWhere, null, null, null, null);

            if (cur.moveToNext()) {
                frontHeightMin = cur.getFloat(cur.getColumnIndex("FrontHeighMin"));
            }

            sqlWhere = String.format("ModelId = %s And abs(FrontHeighMax-99.98999)>0.001", vehicleId);
            cur = db.query("RideHeightData", new String[]{"max(FrontHeighMax) FrontHeighMax", "FrontHeighString"}, sqlWhere, null, null, null, null);

            if (cur.moveToNext() && frontHeightMin != null) {
                float frontHeightMax = cur.getFloat(cur.getColumnIndex("FrontHeighMax"));
                String frontHeightString = cur.getString(cur.getColumnIndex("FrontHeighString"));

                //取前值
                if (frontHeightMax != 0 || frontHeightMin != 0) {
                    data.setFrontHeight(new ValuesPair(frontHeightMin, frontHeightMax, frontHeightString));
                    data.getFrontHeight().format(1);
                }
            }

            sqlWhere = String.format("ModelId = %s And abs(RearHeighMin-99.98999)>0.001", vehicleId);
            cur = db.query("RideHeightData", new String[]{"min(RearHeighMin) RearHeighMin"}, sqlWhere, null, null, null, null);

            if (cur.moveToNext()) {
                rearHeightMin = cur.getFloat(cur.getColumnIndex("RearHeighMin"));
            }

            sqlWhere = String.format("ModelId = %s And abs(RearHeighMax-99.98999)>0.001", vehicleId);
            cur = db.query("RideHeightData", new String[]{"MAX(RearHeighMax) RearHeighMax", "RearHeighString"}, sqlWhere, null, null, null, null);
            if (cur.moveToNext() && rearHeightMin != null) {
                float rearHeightMax = cur.getFloat(cur.getColumnIndex("RearHeighMax"));
                String rearHeightString = cur.getString(cur.getColumnIndex("RearHeighString"));

                //取后值
                if (rearHeightMin != 0 || rearHeightMax != 0) {
                    data.setRearHeight(new ValuesPair(rearHeightMin, rearHeightMax, rearHeightString));
                    data.getRearHeight().format(1);
                }
            }
            cur.close();
        }
        db.close();
        return data;
    }

    public LevelParam queryLevelParam(String vehicleId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        Cursor cur = db.query("SPEClevelstandard", null, "ModelId=" + vehicleId, null, null, null, null);
        LevelParam data = new LevelParam();
        if (cur.moveToNext()) {
            float frontMid = cur.getFloat(cur.getColumnIndex("LevelFrontSpec"));
            float frontMin = cur.getFloat(cur.getColumnIndex("LevelFrontMinToSpec"));
            float frontMax = cur.getFloat(cur.getColumnIndex("LevelFrontMaxToSpec"));

            float rearMid = cur.getFloat(cur.getColumnIndex("LevelRearSpec"));
            float rearMin = cur.getFloat(cur.getColumnIndex("LevelRearMinToSpec"));
            float rearMax = cur.getFloat(cur.getColumnIndex("LevelRearMaxToSpec"));

            data.setFrontLevel(new ValuesPair(-frontMin, frontMid, frontMax));
            data.setRearLevel(new ValuesPair(-rearMin, rearMid, rearMax));

            data.getFrontLevel().format(1);
            data.getRearLevel().format(1);
        }

        cur.close();
        db.close();
        return data;
    }


    public ReferData queryLevelData(String vehicleId, String frontLevel, String rearLevel) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        ReferData data = new ReferData();

        //LevelRear全为99.99
        String where = String.format("ModelId = %s "
                + "And LevelFrontMin <= %s "
                + "And LevelFrontMax > %s", vehicleId, frontLevel, frontLevel);
        Cursor cur = db.query("SPECCamberFront", null, where, null, null, null, null);
        if (cur.moveToNext()) {
            float frontCamber = cur.getFloat(cur.getColumnIndex("CamberFrontSpec"));
            float frontCamberMin = cur.getFloat(cur.getColumnIndex("CamberFrontMinToSpec"));
            float frontCamberMax = cur.getFloat(cur.getColumnIndex("CamberFrontMaxToSpec"));

            if (isValid(frontCamber)) {
                data.setLeftFrontCamber(new ValuesPair(-frontCamberMin, frontCamber, frontCamberMax));
                data.setRightFrontCamber(data.getLeftFrontCamber().copy());
            }

        }

        //LevelFront全为99.99
        where = String.format("ModelId = %s " +
                "And LevelRearMin<= %s " +
                "And LevelRearMax > %s", vehicleId, rearLevel, rearLevel);
        cur = db.query("SPECCamberRear", null, where, null, null, null, null);
        if (cur.moveToNext()) {
            float rearCamber = cur.getFloat(cur.getColumnIndex("CamberRearSpec"));
            float rearCamberMin = cur.getFloat(cur.getColumnIndex("CamberRearMinToSpec"));
            float rearCamberMax = cur.getFloat(cur.getColumnIndex("CamberRearMaxToSpec"));

            if (isValid(rearCamber)) {
                data.setLeftRearCamber(new ValuesPair(-rearCamberMin, rearCamber, rearCamberMax));
                data.setRightRearCamber(data.getLeftRearCamber().copy());
            }
        }


        where = String.format("ModelId = %s "
                + "And LevelFrontMin <= %s "
                + "And LevelFrontMax > %s "
                + "And LevelRearMin <= %s "
                + "And LevelRearMax > %s", vehicleId, frontLevel, frontLevel, rearLevel, rearLevel);
        cur = db.query("SPECcaster", null, where, null, null, null, null);
        if (cur.moveToNext()) {
            float caster = cur.getFloat(cur.getColumnIndex("CasterSpec"));
            float casterMin = cur.getFloat(cur.getColumnIndex("CasterMinToSpec"));
            float casterMax = cur.getFloat(cur.getColumnIndex("CasterMaxToSpec"));

            if (isValid(caster)) {
                data.setLeftCaster(new ValuesPair(-casterMin, caster, casterMax));
                data.setRightCaster(data.getLeftCaster().copy());
            }
        }

        where = String.format("ModelId = %s "
                + "And LevelFrontMin <= %s "
                + "And LevelFrontMax > %s", vehicleId, frontLevel, frontLevel);
        cur = db.query("SPECToeInFront", null, where, null, null, null, null);
        if (cur.moveToNext()) {
            float frontToe = cur.getFloat(cur.getColumnIndex("ToeInFrontSpec"));
            float frontToeMin = cur.getFloat(cur.getColumnIndex("ToeInFrontMinToSpec"));
            float frontToeMax = cur.getFloat(cur.getColumnIndex("ToeInFrontMaxToSpec"));

            if (isValid(frontToe)) {
                data.setLeftFrontToe(new ValuesPair(-frontToeMin, frontToe, frontToeMax));
                data.setRightFrontToe(data.getLeftFrontToe().copy());
                data.setFrontTotalToe(data.getLeftFrontToe().generateTotalToe());
            }
        }

        where = String.format("ModelId = %s "
                + "And LevelRearMin <= %s "
                + "And LevelRearMax > %s", vehicleId, rearLevel, rearLevel);
        cur = db.query("SPECToeInRear", null, where, null, null, null, null);
        if (cur.moveToNext()) {
            float rearTotalToe = cur.getFloat(cur.getColumnIndex("ToeInRearSpec"));
            float rearTotalToeMin = cur.getFloat(cur.getColumnIndex("ToeInRearMinToSpec"));
            float rearTotalToeMax = cur.getFloat(cur.getColumnIndex("ToeInRearMaxToSpec"));

            if (isValid(rearTotalToe)) {
                data.setLeftRearToe(new ValuesPair(-rearTotalToeMin, rearTotalToe, rearTotalToeMax));
                data.setRightRearToe(data.getLeftRearToe().copy());
                data.setRearTotalToe(data.getLeftRearToe().generateTotalToe());
            }
        }

        cur.close();
        db.close();
        return data;
    }

    public ReferData queryHeightData(String vehicleId, String leftFront, String rightFront, String leftRear, String rightRear, String flag) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;
        Cursor cur = null;
        ReferData data = new ReferData();
        switch (Integer.parseInt(flag)) {
            case 2:
                String where = String.format("ModelId=%s And FrontHeighMin<=%s And FrontHeighMax>%s", vehicleId, leftFront, leftFront);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, true);

                where = String.format("ModelId=%s And FrontHeighMin<=%s And FrontHeighMax>%s", vehicleId, rightFront, rightFront);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, false);
                break;
            case 3:
                where = String.format("ModelId=%s And RearHeighMin<=%s And RearHeighMax>%s", vehicleId, leftRear, leftRear);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, true);

                where = String.format("ModelId=%s And RearHeighMin<=%s And RearHeighMax>%s", vehicleId, rightRear, rightRear);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, false);
                break;
            case 4:
                where = String.format("ModelId=%s And FrontHeighMin<=%s And FrontHeighMax>%s", vehicleId, leftFront, leftFront);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, true);

                where = String.format("ModelId=%s And FrontHeighMin<=%s And FrontHeighMax>%s", vehicleId, rightFront, rightFront);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, false);

                where = String.format("ModelId=%s And RearHeighMin<=%s And RearHeighMax>%s", vehicleId, leftRear, leftRear);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, true);

                where = String.format("ModelId=%s And RearHeighMin<=%s And RearHeighMax>%s", vehicleId, rightRear, rightRear);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, false);
                break;
            case 5:
                where = String.format("ModelId=%s And FrontHeighMin<=%s And FrontHeighMax>%s And RearHeighMin<=%s And RearHeighMax>%s",
                        vehicleId, leftFront, leftFront, leftRear, leftRear);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, true);

                where = String.format("ModelId=%s And FrontHeighMin<=%s And FrontHeighMax>%s And RearHeighMin<=%s And RearHeighMax>%s",
                        vehicleId, rightFront, rightFront, rightRear, rightRear);
                cur = db.query("RideHeightData", null, where, null, null, null, null);
                getSpecsAngles(data, cur, false);
                break;
        }
        if (cur != null) cur.close();
        db.close();
        return data;
    }

    private void getSpecsAngles(ReferData data, Cursor cursor, boolean isLeft) {
        if (cursor != null && cursor.moveToNext()) {
            boolean frontToeFlag = false;
            boolean rearToeFlag = false;

            float frontTotalToe = cursor.getFloat(cursor.getColumnIndex("FrontTotalToe"));
            float frontTotalToeMin = cursor.getFloat(cursor.getColumnIndex("FrontTotalToeMin"));
            float frontTotalToeMax = cursor.getFloat(cursor.getColumnIndex("FrontTotalToeMax"));

            float frontCamber = cursor.getFloat(cursor.getColumnIndex("FrontCamber"));
            float frontCamberMin = cursor.getFloat(cursor.getColumnIndex("FrontCamberMin"));
            float frontCamberMax = cursor.getFloat(cursor.getColumnIndex("FrontCamberMax"));

            float kpi = cursor.getFloat(cursor.getColumnIndex("KPI"));
            float kpiMin = cursor.getFloat(cursor.getColumnIndex("KPIMin"));
            float kpiMax = cursor.getFloat(cursor.getColumnIndex("KPIMax"));

            float rearTotalToe = cursor.getFloat(cursor.getColumnIndex("RearTotalToe"));
            float rearTotalToeMin = cursor.getFloat(cursor.getColumnIndex("RearTotalToeMIn"));
            float rearTotalToeMax = cursor.getFloat(cursor.getColumnIndex("RearTotalToeMax"));

            float rearCamber = cursor.getFloat(cursor.getColumnIndex("RearCamber"));
            float rearCamberMin = cursor.getFloat(cursor.getColumnIndex("RearCamberMin"));
            float rearCamberMax = cursor.getFloat(cursor.getColumnIndex("RearCamberMax"));

            float caster = cursor.getFloat(cursor.getColumnIndex("Caster"));
            float casterMin = cursor.getFloat(cursor.getColumnIndex("CasterMin"));
            float casterMax = cursor.getFloat(cursor.getColumnIndex("CasterMax"));

            if (isLeft) {
                if (isValid(frontTotalToe))
                    data.setLeftFrontToe(new ValuesPair(frontTotalToeMin, frontTotalToe, frontTotalToeMax));
                if (isValid(frontCamber))
                    data.setLeftFrontCamber(new ValuesPair(frontCamberMin, frontCamber, frontCamberMax));
                if (isValid(kpi))
                    data.setLeftKpi(new ValuesPair(kpiMin, kpi, kpiMax));
                if (isValid(rearTotalToe))
                    data.setLeftRearToe(new ValuesPair(rearTotalToeMin, rearTotalToe, rearTotalToeMax));
                if (isValid(rearCamber))
                    data.setLeftRearCamber(new ValuesPair(rearCamberMin, rearCamber, rearCamberMax));
                if (isValid(caster))
                    data.setLeftCaster(new ValuesPair(casterMin, caster, casterMax));
            } else {
                if (isValid(frontTotalToe))
                    frontToeFlag = true;
                if (isValid(frontCamber))
                    data.setRightFrontCamber(new ValuesPair(frontCamberMin, frontCamber, frontCamberMax));
                if (isValid(kpi))
                    data.setRightKpi(new ValuesPair(kpiMin, kpi, kpiMax));
                if (isValid(rearTotalToe))
                    rearToeFlag = true;
                if (isValid(rearCamber))
                    data.setRightRearCamber(new ValuesPair(rearCamberMin, rearCamber, rearCamberMax));
                if (isValid(caster))
                    data.setRightCaster(new ValuesPair(casterMin, caster, casterMax));
            }

            if (frontToeFlag) {
                float min = (Float.parseFloat(data.getLeftFrontToe().getMin()) + frontTotalToe - frontTotalToeMin) / 2;
                float mid = (Float.parseFloat(data.getLeftFrontToe().getMid()) + frontTotalToe) / 2;
                float max = (Float.parseFloat(data.getLeftFrontToe().getMax()) + frontTotalToe + frontTotalToeMax) / 2;

                data.setFrontTotalToe(new ValuesPair(CommonUtil.format(min, 2), CommonUtil.format(mid, 2), CommonUtil.format(max, 2)));
                data.setLeftFrontToe(data.getFrontTotalToe().generateSingleToe());
                data.setRightFrontToe(data.getLeftFrontToe().copy());
            }

            if (rearToeFlag) {
                float min = (Float.parseFloat(data.getLeftRearToe().getMin()) + rearTotalToe - rearTotalToeMin) / 2;
                float mid = (Float.parseFloat(data.getLeftRearToe().getMid()) + rearTotalToe) / 2;
                float max = (Float.parseFloat(data.getLeftRearToe().getMax()) + rearTotalToe + rearTotalToeMax) / 2;

                data.setRearTotalToe(new ValuesPair(CommonUtil.format(min, 2), CommonUtil.format(mid, 2), CommonUtil.format(max, 2)));
                data.setLeftRearToe(data.getRearTotalToe().generateSingleToe());
                data.setRightRearToe(data.getLeftRearToe().copy());
            }
        }
    }

    public List getManufactures(String pyIndex) {
        List<Map<String, String>> data = new ArrayList<>();
        if (pyIndex == null || pyIndex.equals(""))
            return data;

        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        String field = CommonUtil.isChinese(pyIndex) ? "Model5" : "PyIndex";
        String where = String.format("S2.%s like '%%%s%%' ", field, pyIndex);
        Cursor cur = db.query("StandVehmanfacturers S1 INNER JOIN StandTypelevel S2 ON S1.Manufact1=S2.Model4", new String[]{
                "S1.Manufact1", "S1.Manufact2", "S2.Model5"}, where, null, null, null, null);
        while (cur.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put("name", cur.getString(cur.getColumnIndex("Manufact2")));
            map.put("id", cur.getString(cur.getColumnIndex("Manufact1")));
            map.put("vehicle", cur.getString(cur.getColumnIndex("Model5")));
            map.put("dbIndex", "0");
            data.add(map);
        }
        cur.close();
        db.close();
        return data;
    }

    private boolean isValid(float value) {
        return !GloVariable.initValue.equals(new DecimalFormat("0.00").format(value));
    }

    private String getPicPath(String manInfo, String path) {

        int index = manInfo.indexOf("(");
        if (index >= 0) {
            manInfo = manInfo.subSequence(0, index).toString();
        }

        return String.format("RideHeight/%s %s.PNG", manInfo.toUpperCase(), path);
    }


    public String dataInitHandle(float value, int num) {
        if (CommonUtil.format(value, 2).equals(GloVariable.initValue)) return GloVariable.initValue;

        return CommonUtil.format(value, num);
    }
}
