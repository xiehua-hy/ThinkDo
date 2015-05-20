package com.thinkdo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thinkdo.entity.GloVariable;
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
            map.put(PickCusCarFragment.colID, cur.getString(cur.getColumnIndex("OftenTotal2")));
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

}
