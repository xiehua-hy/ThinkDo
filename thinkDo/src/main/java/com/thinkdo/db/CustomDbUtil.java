package com.thinkdo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.thinkdo.application.MainApplication;
import com.thinkdo.entity.CustomerModel;
import com.thinkdo.entity.FormMetaModel;
import com.thinkdo.entity.LorryDataItem;
import com.thinkdo.entity.LorryReferData;
import com.thinkdo.entity.OperOftenDataTotalModel;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.TestVehicleInfoModel;
import com.thinkdo.entity.ValuesPair;
import com.thinkdo.fragment.PickCusCarFragment;
import com.thinkdo.util.CommonUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDbUtil {

    public SQLiteDatabase getWriteDb() {
        String path = MainApplication.context.getDatabasePath(MainApplication.customSqliteName).getPath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SQLiteDatabase getReadDb() {
        String path = MainApplication.context.getDatabasePath(MainApplication.customSqliteName).getPath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
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

        String sqlWhere;
        if (pinyin == null) {
            sqlWhere = String.format("OftenTotal1=%s", manId);
        } else {
            String field = CommonUtil.isChinese(pinyin) ? "OftenTotal3" : "Pyindex";
            sqlWhere = String.format("OftenTotal1=%s and %s like '%%%s%%'", manId, field, pinyin);
        }
        Cursor cur = db.query("OperOftenDataTotal", null, sqlWhere, null, null, null, null);

        List<String> data = new ArrayList<>();
        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex("OftenTotal23"));
            int startY = cur.getInt(cur.getColumnIndex("OftenTotal4"));
            int endY = cur.getInt(cur.getColumnIndex("OftenTotal5"));
            String type = cur.getString(cur.getColumnIndex("OftenTotal3"));

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

    public List<Map<String, String>> queryAllManufacturer() {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        List<Map<String, String>> data = new ArrayList<>();
        //each row is unique
        Cursor cur = db.query(true, "OperOftenDataTotal", new String[]{"OftenTotal1", "OftenTotal2"}, null, null, null, null, null, null);

        while (cur.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put(PickCusCarFragment.colID, cur.getString(cur.getColumnIndex("OftenTotal1")));
            map.put(PickCusCarFragment.colInfo, cur.getString(cur.getColumnIndex("OftenTotal2")));
            data.add(map);
        }

        cur.close();
        db.close();
        return data;
    }

    public List<Map<String, String>> getManufactures(String pyIndex) {
        List<Map<String, String>> data = new ArrayList<>();
        if (pyIndex == null || pyIndex.equals("")) return data;

        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        String field = CommonUtil.isChinese(pyIndex) ? "OftenTotal3" : "Pyindex";
        String where = String.format("%s like \'%%%s%%\'", field, pyIndex);

        Cursor cur = db.query("OperOftenDataTotal", new String[]{"OftenTotal1", "OftenTotal2", "OftenTotal3"}, where, null, null, null, null);
        if (cur.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put("name", cur.getString(cur.getColumnIndex("OftenTotal2")));
            map.put("id", cur.getString(cur.getColumnIndex("OftenTotal1")));
            map.put("vehicle", cur.getString(cur.getColumnIndex("OftenTotal3")));
            map.put("dbIndex", "1");
            data.add(map);
        }
        cur.close();
        db.close();

        return data;
    }

    public ReferData queryReferData(String vehicleId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        ReferData data = new ReferData();
        Cursor cur = db.query("OperOftenDataTotal", new String[]{"OftenTotal3", "OftenTotal4", "OftenTotal5"},
                "OftenTotal23=" + vehicleId, null, null, null, null);

        if (cur.moveToNext()) {
            data.setVehicleInfo(cur.getString(cur.getColumnIndex("OftenTotal3")));
            data.setStartYear(cur.getString(cur.getColumnIndex("OftenTotal4")));
            data.setEndYear(cur.getString(cur.getColumnIndex("OftenTotal5")));
        }

        cur = db.query("OperOftenDataDetail", null, "OftenTotal23=" + vehicleId, null, null, null, "id");

        if (cur.getCount() == 17) {
            //前束
            cur.moveToPosition(0);
            data.setFrontTotalToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(1);
            data.setLeftFrontToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(2);
            data.setRightFrontToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //前束
            cur.moveToPosition(3);
            data.setLeftFrontCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(4);
            data.setRightFrontCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //后倾
            cur.moveToPosition(5);
            data.setLeftCaster(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(6);
            data.setRightCaster(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //内倾
            cur.moveToPosition(7);
            data.setLeftKpi(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(8);
            data.setRightKpi(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //前束
            cur.moveToPosition(9);
            data.setRearTotalToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(10);
            data.setLeftRearToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(11);
            data.setRightRearToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //前束
            cur.moveToPosition(12);
            data.setLeftRearCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(13);
            data.setRightRearCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")),
                    cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            cur.moveToPosition(14);
            data.setWheelbase(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail2"))));
            cur.moveToPosition(15);
            data.setFrontWheel(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail2"))));
            cur.moveToPosition(16);
            data.setRearWheel(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail2"))));
        }

        cur.close();
        db.close();
        return data;
    }


    /**
     * 根据vehicleId 获取 厂商Id
     */
    public String getManId(String vehicleId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        String OftenTotal1;
        String where = String.format("OftenTotal2='%s'", vehicleId);
        Cursor cur = db.query("OperOftenDataTotal", new String[]{"OftenTotal1"}, where, null, null, null, null);

        if (cur.moveToNext()) {
            OftenTotal1 = cur.getString(cur.getColumnIndex("OftenTotal1"));

        } else {
            cur = db.query("OperOftenDataTotal", new String[]{"Max(cast (OftenTotal1 as int)) max"}, null, null, null, null, null);
            if (cur.moveToNext()) {
                OftenTotal1 = String.valueOf(cur.getInt(cur.getColumnIndex("max")) + 1);
            } else {
                OftenTotal1 = "1";
            }
        }
        cur.close();
        db.close();
        return OftenTotal1;
    }

    public String getVehicleId() {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        String OftenTotal23;
        Cursor cur = db.query("OperOftenDataTotal", new String[]{"Max(cast (OftenTotal23 as int)) max"}, null, null, null, null, null);

        if (cur.moveToNext()) {
            OftenTotal23 = String.valueOf(cur.getInt(cur.getColumnIndex("max")) + 1);
        } else {
            OftenTotal23 = "1";
        }

        cur.close();
        db.close();
        return OftenTotal23;
    }

    public boolean insertVehicleInfo(OperOftenDataTotalModel data) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        ContentValues content = new ContentValues();
        content.put("OftenTotal1", data.getManId());
        content.put("OftenTotal2", data.getManInfo());
        content.put("OftenTotal23", data.getVehicleId());
        content.put("OftenTotal3", data.getVehicleInfo());
        content.put("OftenTotal4", data.getStartYear());
        content.put("OftenTotal5", data.getEndYear());
        content.put("OftenTotal6", data.getOftenTotal6());
        content.put("OftenTotal7", data.getDate());
        content.put("languageid", data.getLanguage());
        content.put("TyreModel", data.getWheelType());
        content.put("InchValue", data.getDiameterInch());
        content.put("mmValue", data.getDiameterMM());
        content.put("Pyindex", data.getPyIndex());
        content.put("RefCount", data.getRefCount());
        content.put("LevelFlag", data.getLevelFlag());

        long flag = db.insert("OperOftenDataTotal", null, content);
        db.close();
        return flag != -1;
    }

    public boolean insertVehicleData(ReferData data) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        ContentValues content = new ContentValues();
        int id = 1;
        int OftenDetail4 = 0;
        Cursor cur = db.query("OperOftenDataDetail", new String[]{"Max(id) max"}, null, null, null, null, null);

        if (cur.moveToNext()) {
            id = cur.getInt(cur.getColumnIndex("max")) + 1;
        }

        content.put("OftenTotal1", data.getManId());
        content.put("OftenTotal23", data.getVehicleId());
        content.put("OftenTotal4", data.getStartYear());
        content.put("OftenTotal5", data.getEndYear());
        content.put("languageid", 1);

        //row1
        content.put("id", id++);
        content.put("OftenDetail1", data.getFrontTotalToe().getMin());
        content.put("OftenDetail2", data.getFrontTotalToe().getMid());
        content.put("OftenDetail3", data.getFrontTotalToe().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row2
        content.put("id", id++);
        content.put("OftenDetail1", data.getLeftFrontToe().getMin());
        content.put("OftenDetail2", data.getLeftFrontToe().getMid());
        content.put("OftenDetail3", data.getLeftFrontToe().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row3
        content.put("id", id++);
        content.put("OftenDetail1", data.getRightFrontToe().getMin());
        content.put("OftenDetail2", data.getRightFrontToe().getMid());
        content.put("OftenDetail3", data.getRightFrontToe().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row4
        content.put("id", id++);
        content.put("OftenDetail1", data.getLeftFrontCamber().getMin());
        content.put("OftenDetail2", data.getLeftFrontCamber().getMid());
        content.put("OftenDetail3", data.getLeftFrontCamber().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row5
        content.put("id", id++);
        content.put("OftenDetail1", data.getRightFrontCamber().getMin());
        content.put("OftenDetail2", data.getRightFrontCamber().getMid());
        content.put("OftenDetail3", data.getRightFrontCamber().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row6
        content.put("id", id++);
        content.put("OftenDetail1", data.getLeftCaster().getMin());
        content.put("OftenDetail2", data.getLeftCaster().getMid());
        content.put("OftenDetail3", data.getLeftCaster().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row7
        content.put("id", id++);
        content.put("OftenDetail1", data.getRightCaster().getMin());
        content.put("OftenDetail2", data.getRightCaster().getMid());
        content.put("OftenDetail3", data.getRightCaster().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row8
        content.put("id", id++);
        content.put("OftenDetail1", data.getLeftKpi().getMin());
        content.put("OftenDetail2", data.getLeftKpi().getMid());
        content.put("OftenDetail3", data.getLeftKpi().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row9
        content.put("id", id++);
        content.put("OftenDetail1", data.getRightKpi().getMin());
        content.put("OftenDetail2", data.getRightKpi().getMid());
        content.put("OftenDetail3", data.getRightKpi().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row10
        content.put("id", id++);
        content.put("OftenDetail1", data.getRearTotalToe().getMin());
        content.put("OftenDetail2", data.getRearTotalToe().getMid());
        content.put("OftenDetail3", data.getRearTotalToe().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row11
        content.put("id", id++);
        content.put("OftenDetail1", data.getLeftRearToe().getMin());
        content.put("OftenDetail2", data.getLeftRearToe().getMid());
        content.put("OftenDetail3", data.getLeftRearToe().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row12
        content.put("id", id++);
        content.put("OftenDetail1", data.getRightRearToe().getMin());
        content.put("OftenDetail2", data.getRightRearToe().getMid());
        content.put("OftenDetail3", data.getRightRearToe().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row13
        content.put("id", id++);
        content.put("OftenDetail1", data.getLeftRearCamber().getMin());
        content.put("OftenDetail2", data.getLeftRearCamber().getMid());
        content.put("OftenDetail3", data.getLeftRearCamber().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row14
        content.put("id", id++);
        content.put("OftenDetail1", data.getRightRearCamber().getMin());
        content.put("OftenDetail2", data.getRightRearCamber().getMid());
        content.put("OftenDetail3", data.getRightRearCamber().getMax());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row15
        content.put("id", id++);
        content.put("OftenDetail1", data.getWheelbase().getMid());
        content.put("OftenDetail2", data.getWheelbase().getMid());
        content.put("OftenDetail3", data.getWheelbase().getMid());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row16
        content.put("id", id++);
        content.put("OftenDetail1", data.getFrontWheel().getMid());
        content.put("OftenDetail2", data.getFrontWheel().getMid());
        content.put("OftenDetail3", data.getFrontWheel().getMid());
        content.put("OftenDetail4", OftenDetail4++);
        db.insert("OperOftenDataDetail", null, content);

        //row17
        content.put("id", id);
        content.put("OftenDetail1", data.getRearWheel().getMid());
        content.put("OftenDetail2", data.getRearWheel().getMid());
        content.put("OftenDetail3", data.getRearWheel().getMid());
        content.put("OftenDetail4", OftenDetail4);
        long flag = db.insert("OperOftenDataDetail", null, content);

        cur.close();
        db.close();
        return flag != -1;
    }

    public LorryDataItem queryLorryTestData(String customerId, String testSerial) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;
        String[] params = {
                "FirstTotalToe", "FirstLeftToe", "FirstRightToe", "FirstLeftCamber", "FirstRightCamber",
                "FirstLeftCaster", "FirstRightCaster", "FirstLeftKpi", "FirstRightKpi",

                "SecondTotalToe", "SecondLeftToe", "SecondRightToe", "SecondLeftCamber", "SecondRightCamber",
                "SecondLeftCaster", "SecondRightCaster", "SecondLeftKpi", "SecondRightKpi",

                "ThirdTotalToe", "ThirdLeftToe", "ThirdRightToe", "ThirdLeftCamber", "ThirdRightCamber",
                "ThirdLeftCaster", "ThirdRightCaster", "ThirdLeftKpi", "ThirdRightKpi",

                "FourthTotalToe", "FourthLeftToe", "FourthRightToe", "FourthLeftCamber", "FourthRightCamber",
                "FourthLeftCaster", "FourthRightCaster", "FourthLeftKpi", "FourthRightKpi",

                "FifthTotalToe", "FifthLeftToe", "FifthRightToe", "FifthLeftCamber", "FifthRightCamber",
                "FifthLeftCaster", "FifthRightCaster", "FifthLeftKpi", "FifthRightKpi",

                "SixthTotalToe", "SixthLeftToe", "SixthRightToe", "SixthLeftCamber", "SixthRightCamber",
                "SixthLeftCaster", "SixthRightCaster", "SixthLeftKpi", "SixthRightKpi",

                "SeventhTotalToe", "SeventhLeftToe", "SeventhRightToe", "SeventhLeftCamber", "SeventhRightCamber",

                "EighthTotalToe", "EighthLeftToe", "EighthRightToe", "EighthLeftCamber", "EighthRightCamber",

                "NinthTotalToe", "NinthLeftToe", "NinthRightToe", "NinthLeftCamber", "NinthRightCamber",

                "TenthTotalToe", "TenthLeftToe", "TenthRightToe", "TenthLeftCamber", "TenthRightCamber",

                "EleventhTotalToe", "EleventhLeftToe", "EleventhRightToe", "EleventhLeftCamber", "EleventhRightCamber",

                "TwelfthTotalToe", "TwelfthLeftToe", "TwelfthRightToe", "TwelfthLeftCamber", "TwelfthRightCamber",
        };

        LorryDataItem data = new LorryDataItem();
        String where = String.format("Client1=%s And ClientInfo1=%s", customerId, testSerial);
        Cursor cur = db.query("OperClientTestData_L", null, where, null, null, null, "ID");
        if (cur.getCount() == params.length) {
            for (String str : params) {
                cur.moveToNext();
                String preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
                String min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
                String mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
                String max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
                String real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));

                try {
                    Method method = LorryDataItem.class.getMethod("set" + str, ValuesPair.class);
                    method.invoke(data, new ValuesPair(min, mid, max, preReal, real));

                    method = LorryDataItem.class.getMethod("get" + str);
                    ((ValuesPair) method.invoke(data)).format(2);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        cur.close();
        db.close();
        return data;
    }

    public ReferData queryTestData(String customerId, String testSerial) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        ReferData data = new ReferData();
        String where = String.format("Client1=%s And ClientInfo1=%s", customerId, testSerial);
        Cursor cur = db.query("OperClientTestData", null, where, null, null, null, "ID");
        if (cur.getCount() == 23) {
            //row1
            cur.moveToFirst();
            String preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            String min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            String mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            String max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            String real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setFrontTotalToe(new ValuesPair(min, mid, max, preReal, real));
            data.getFrontTotalToe().format(2);

            //row2
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftFrontToe(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftFrontToe().format(2);

            //row3
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightFrontToe(new ValuesPair(min, mid, max, preReal, real));
            data.getRightFrontToe().format(2);

            //row4
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftFrontCamber(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftFrontCamber().format(2);

            //row5
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightFrontCamber(new ValuesPair(min, mid, max, preReal, real));
            data.getRightFrontCamber().format(2);

            //row6
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftCaster(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftCaster().format(2);

            //row7
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightCaster(new ValuesPair(min, mid, max, preReal, real));
            data.getRightCaster().format(2);

            //row8
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftKpi(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftKpi().format(2);

            //row9
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightKpi(new ValuesPair(min, mid, max, preReal, real));
            data.getRightKpi().format(2);

            //row10
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRearTotalToe(new ValuesPair(min, mid, max, preReal, real));
            data.getRearTotalToe().format(2);

            //row11
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftRearToe(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftRearToe().format(2);

            //row12
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightRearToe(new ValuesPair(min, mid, max, preReal, real));
            data.getRightRearToe().format(2);

            //row13
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftRearCamber(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftRearCamber().format(2);

            //row14
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightRearCamber(new ValuesPair(min, mid, max, preReal, real));
            data.getRightRearCamber().format(2);

            //row15
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setMaxThrust(new ValuesPair(min, mid, max, preReal, real));
            data.getMaxThrust().format(2);

            //row16
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setWheelbaseDiff(new ValuesPair(min, mid, max, preReal, real));
            data.getWheelbaseDiff().format(0);

            //row17
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setWheelDiff(new ValuesPair(min, mid, max, preReal, real));
            data.getWheelDiff().format(0);
            //row18
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftIncludeAngle(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftIncludeAngle().format(2);

            //row19
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightIncludeAngle(new ValuesPair(min, mid, max, preReal, real));
            data.getRightIncludeAngle().format(2);

            //row20
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftTurnAngle(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftTurnAngle().format(2);

            //row21
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightTurnAngle(new ValuesPair(min, mid, max, preReal, real));
            data.getRightTurnAngle().format(2);

            //row22
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setLeftMaxTurnAngle(new ValuesPair(min, mid, max, preReal, real));
            data.getLeftMaxTurnAngle().format(2);

            //row23
            cur.moveToNext();
            preReal = cur.getString(cur.getColumnIndex("ClientTestInfo1"));
            min = cur.getString(cur.getColumnIndex("ClientTestInfo2"));
            mid = cur.getString(cur.getColumnIndex("ClientTestInfo3"));
            max = cur.getString(cur.getColumnIndex("ClientTestInfo4"));
            real = cur.getString(cur.getColumnIndex("ClientTestInfo5"));
            data.setRightMaxTurnAngle(new ValuesPair(min, mid, max, preReal, real));
            data.getRightMaxTurnAngle().format(2);
        }

        cur.close();
        db.close();
        return data;
    }


    public boolean insertLorryTestData(LorryDataItem data, String customerId, String plateNo, String testSeries, String date) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;
        long flag = -1;
        Cursor cur = db.query("OperClientTestData_L", new String[]{"Max(ID) max"}, null, null, null, null, null);
        int insertId = cur.moveToFirst()
                ? cur.getInt(cur.getColumnIndex("max")) + 1
                : 1;

        ContentValues content = new ContentValues();
        content.put("Client1", customerId);
        content.put("Client3", plateNo);
        content.put("ClientInfo1", testSeries);
        content.put("ClientInfo2", date);

        db.beginTransaction();
        try {
            //插入实时数据

            for (ValuesPair pair : data.getValuesPairs()) {
                content.put("ID", insertId++);
                content.put("ClientTestInfo1", pair.getPreReal());
                content.put("ClientTestInfo2", pair.getMin());
                content.put("ClientTestInfo3", pair.getMid());
                content.put("ClientTestInfo4", pair.getMax());
                content.put("ClientTestInfo5", pair.getReal());
                flag = db.insert("OperClientTestData_L", null, content);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        cur.close();
        db.close();

        return flag != -1;
    }

    public boolean insertTestVehicleData(ReferData data, String customerId, String plateNo, String testSeries, String date) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;
        long flag = -1;
        Cursor cur = db.query("OperClientTestData", new String[]{"Max(ID) max"}, null, null, null, null, null);
        int insertId = cur.moveToFirst()
                ? cur.getInt(cur.getColumnIndex("max")) + 1
                : 1;
        ContentValues content = new ContentValues();
        content.put("Client1", customerId);
        content.put("Client3", plateNo);
        content.put("ClientInfo1", testSeries);
        content.put("ClientInfo2", date);

        db.beginTransaction();
        try {

            for (ValuesPair pair : data.getValuesPairs()) {
                content.put("ID", insertId++);
                content.put("ClientTestInfo1", pair.getPreReal());
                content.put("ClientTestInfo2", pair.getMin());
                content.put("ClientTestInfo3", pair.getMid());
                content.put("ClientTestInfo4", pair.getMax());
                content.put("ClientTestInfo5", pair.getReal());
                flag = db.insert("OperClientTestData", null, content);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        cur.close();
        db.close();
        return flag != -1;
    }

    public boolean insertTestVehicleInfo(TestVehicleInfoModel data) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        ContentValues content = new ContentValues();
        content.put("Client1", data.getCustomerId());
        content.put("Client3", data.getPlateNum());
        content.put("ClientInfo1", data.getTestSerial());
        content.put("ClientInfo2", data.getTestDate());
        content.put("ClientInfo3", data.getOperator());
        content.put("ClientInfo4", data.getTestTimeLong());
        content.put("ClientInfo5", data.getMalfunction());
        content.put("ClientInfo6", data.getManInfo());
        content.put("ClientInfo7", data.getModel());
        content.put("ClientInfo8", data.getStartYear());
        content.put("ClientInfo9", data.getEndYear());
        content.put("CurUnit", data.getCurUnit());
        content.put("CurFrontToeUnit", data.getCurToeUnit());
        content.put("FrontToemm", data.getTireDiameter());
        content.put("WheelBase", data.getWheelBase());

        content.put("FrontTread", data.getFrontTread());
        content.put("RearTread", data.getRearTread());
        content.put("Remark", data.getRemark());

        long flag = db.insert("OperClientInfo", null, content);
        db.close();
        return flag != -1;
    }

    public boolean insertCustomer(CustomerModel data) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        ContentValues content = new ContentValues();
        content.put("Client1", data.getId());
        content.put("Client2", data.getName());
        content.put("Client3", data.getCompany());
        content.put("Client4", data.getAddress());
        content.put("Client5", data.getTel());
        content.put("Client6", data.getDate());
        content.put("PyIndex", data.getPyIndex());

        long flag = db.insert("OperClient", null, content);
        db.close();
        return flag != -1;
    }

    public String getInsertCustomerId() {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        Cursor cur = db.query("OperClient", new String[]{"Max(cast (Client1 as int)) max"}, null, null, null, null, null);

        String id = cur.moveToFirst()
                ? String.valueOf(cur.getInt(cur.getColumnIndex("max")) + 1)
                : "1";

        cur.close();
        db.close();
        return id;
    }

    public String getInsertTestSeries(String CustomerId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;
        Cursor cur = db.query("OperClientInfo", new String[]{"Max(cast (ClientInfo1 as int)) max"},
                "Client1=" + CustomerId, null, null, null, null);
        String series = cur.moveToFirst()
                ? String.valueOf(cur.getInt(cur.getColumnIndex("max")) + 1)
                : "1";
        cur.close();
        db.close();
        return series;
    }

    public boolean updateCustomer(CustomerModel custom) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        ContentValues content = new ContentValues();
        content.put("Client2", custom.getName());
        content.put("Client3", custom.getCompany());
        content.put("Client4", custom.getAddress());
        content.put("Client5", custom.getTel());
        content.put("Client6", custom.getDate());
        content.put("PyIndex", custom.getPyIndex());

        long flag = db.update("OperClient", content, String.format("client1=%s", custom.getId()), null);
        db.close();
        return flag != -1;
    }

    public List<CustomerModel> queryAllCustomer() {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        List<CustomerModel> data = new ArrayList<>();
        Cursor cur = db.query("OperClient", null, null, null, null, null, null);
        while (cur.moveToNext()) {
            CustomerModel customer = new CustomerModel();
            customer.setId(cur.getString(cur.getColumnIndex("Client1")));
            customer.setName(cur.getString(cur.getColumnIndex("Client2")));
            customer.setCompany(cur.getString(cur.getColumnIndex("Client3")));
            customer.setAddress(cur.getString(cur.getColumnIndex("Client4")));
            customer.setTel(cur.getString(cur.getColumnIndex("Client5")));

            data.add(customer);
        }

        cur.close();
        db.close();
        return data;
    }

    public CustomerModel queryCustomer(String CustomerId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        CustomerModel customer = new CustomerModel();
        String where = String.format("Client1=%s", CustomerId);
        Cursor cur = db.query("OperClient", null, where, null, null, null, null);

        if (cur.moveToFirst()) {
            customer.setId(cur.getString(cur.getColumnIndex("Client1")));
            customer.setName(cur.getString(cur.getColumnIndex("Client2")));
            customer.setCompany(cur.getString(cur.getColumnIndex("Client3")));
            customer.setAddress(cur.getString(cur.getColumnIndex("Client4")));
            customer.setTel(cur.getString(cur.getColumnIndex("Client5")));
        }

        cur.close();
        db.close();
        return customer;
    }

    public boolean deleteCustomer(String customerId) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        long flag = db.delete("OperClient", String.format("Client1=%s", customerId), null);
        db.close();

        return flag != 0;
    }

    public boolean deleteCustomerVehicle(String customerId) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        long flag = db.delete("OperClientInfo", String.format("Client1=%s", customerId), null);
        db.close();
        return flag != 0;
    }

    public boolean deleteCustomerTestData(String customerId) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        long flag = db.delete("OperClientTestData", String.format("Client1=%s", customerId), null);
        db.close();
        return flag != 0;
    }

    public boolean deleteCustomerVehicle(String customerId, String testNo) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        long flag = db.delete("OperClientInfo", String.format("Client1=%s and ClientInfo1=%s", customerId, testNo), null);
        db.close();
        return flag != 0;
    }

    public boolean deleteCustomerTestData(String customerId, String testNo) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return false;

        long flag = db.delete("OperClientTestData", String.format("Client1=%s and ClientInfo1=%s", customerId, testNo), null);
        db.close();
        return flag != 0;
    }

    public List<FormMetaModel> queryFormMeta(String customerId) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        List<FormMetaModel> list = new ArrayList<>();

        String sql = "select OperClientInfo.[Client1], OperClientInfo.[Client3], OperClientInfo.[ClientInfo1],"
                + "OperClientInfo.[ClientInfo2], OperClientInfo.[ClientInfo6], OperClient.[Client2] from "
                + "OperClientInfo left join OperClient on OperClientInfo.[Client1]=OperClient.[Client1] ";
        if (customerId != null) {
            sql = String.format("%s where OperClientInfo.[Client1]=%s", sql, customerId);
        }

        Cursor cur = db.rawQuery(sql, null);
        while (cur.moveToNext()) {
            FormMetaModel model = new FormMetaModel();
            model.setCustomerId(cur.getString(cur.getColumnIndex("Client1")));
            model.setTestNo(cur.getString(cur.getColumnIndex("ClientInfo1")));
            model.setName(cur.getString(cur.getColumnIndex("Client2")));
            model.setVehicle(cur.getString(cur.getColumnIndex("ClientInfo6")));
            model.setPlateNo(cur.getString(cur.getColumnIndex("Client3")));
            model.setDate(cur.getString(cur.getColumnIndex("ClientInfo2")));
            list.add(model);
        }

        cur.close();
        db.close();
        return list;
    }

    public TestVehicleInfoModel queryTestVeclieInfo(String clientId, String testNo) {
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;

        TestVehicleInfoModel data = new TestVehicleInfoModel();
        String where = String.format("Client1=%s and ClientInfo1=%s", clientId, testNo);
        Cursor cur = db.query("OperClientInfo", null, where, null, null, null, null);
        if (cur.moveToFirst()) {
            data.setCustomerId(cur.getString(cur.getColumnIndex("Client1")));
            data.setPlateNum(cur.getString(cur.getColumnIndex("Client3")));
            data.setTestSerial(cur.getString(cur.getColumnIndex("ClientInfo1")));
            data.setTestDate(cur.getString(cur.getColumnIndex("ClientInfo2")));
            data.setOperator(cur.getString(cur.getColumnIndex("ClientInfo3")));

            data.setMalfunction(cur.getString(cur.getColumnIndex("ClientInfo5")));
            data.setManInfo(cur.getString(cur.getColumnIndex("ClientInfo6")));
            data.setModel(cur.getString(cur.getColumnIndex("ClientInfo7")));
            data.setStartYear(cur.getString(cur.getColumnIndex("ClientInfo8")));
            data.setEndYear(cur.getString(cur.getColumnIndex("ClientInfo9")));

            data.setCurUnit(cur.getString(cur.getColumnIndex("CurUnit")));
            data.setRemark(cur.getString(cur.getColumnIndex("Remark")));
            data.setWheelBase(cur.getString(cur.getColumnIndex("WheelBase")));
            data.setFrontTread(cur.getString(cur.getColumnIndex("FrontTread")));
            data.setRearTread(cur.getString(cur.getColumnIndex("RearTread")));
        }

        cur.close();
        db.close();
        return data;
    }

    public void deleteVehicleInfo(String vehicleId) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return;
        //删除车型字段信息
        db.delete("OperOftenDataTotal", "OftenTotal23 = " + vehicleId, null);
        //删除车型参数信息
        db.delete("OperOftenDataDetail", "OftenTotal23 = " + vehicleId, null);

    }


}
