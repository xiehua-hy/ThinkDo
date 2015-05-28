package com.thinkdo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thinkdo.entity.GloVariable;
import com.thinkdo.entity.OperOftenDataTotalModel;
import com.thinkdo.entity.ReferData;
import com.thinkdo.entity.ValuesPair;
import com.thinkdo.fragment.PickCusCarFragment;
import com.thinkdo.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDbUtil {

    public SQLiteDatabase getWriteDb() {
        String path = GloVariable.context.getDatabasePath(GloVariable.customSqliteName).getPath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SQLiteDatabase getReadDb() {
        String path = GloVariable.context.getDatabasePath(GloVariable.customSqliteName).getPath();
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
            data.setFrontTotalToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(1);
            data.setLeftFrontToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(2);
            data.setRightFrontToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //前束
            cur.moveToPosition(3);
            data.setLeftFrontCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(4);
            data.setRightFrontCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //后倾
            cur.moveToPosition(5);
            data.setLeftCaster(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(6);
            data.setRightCaster(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //内倾
            cur.moveToPosition(7);
            data.setLeftKpi(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(8);
            data.setRightKpi(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //前束
            cur.moveToPosition(9);
            data.setRearTotalToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(10);
            data.setLeftRearToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(11);
            data.setRightRearToe(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

            //前束
            cur.moveToPosition(12);
            data.setLeftRearCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));
            cur.moveToPosition(13);
            data.setRightRearCamber(new ValuesPair(cur.getFloat(cur.getColumnIndex("OftenDetail1")), cur.getFloat(cur.getColumnIndex("OftenDetail2")), cur.getFloat(cur.getColumnIndex("OftenDetail3")), true, null));

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

    public void insertVehicleInfo(OperOftenDataTotalModel data) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return;

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

        db.insert("OperOftenDataTotal", null, content);
        db.close();
    }

    public void insertVehicleData(ReferData data) {
        SQLiteDatabase db = getWriteDb();
        if (db == null) return;

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
        db.insert("OperOftenDataDetail", null, content);

        cur.close();
        db.close();
    }

}
