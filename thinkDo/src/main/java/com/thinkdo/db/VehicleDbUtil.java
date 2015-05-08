package com.thinkdo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thinkdo.entity.GloVariable;

/**
 * Created by xh on 15/5/8.
 */
public class VehicleDbUtil {
    public SQLiteDatabase getWriteDb() {

        String path = GloVariable.context.getDatabasePath(GloVariable.carSqliteName).getAbsolutePath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SQLiteDatabase getReadDb() {
        String path = GloVariable.context.getDatabasePath(GloVariable.carSqliteName).getAbsolutePath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String queryManufacturerInfo(int manfacturerId) {

        String info = null;
        SQLiteDatabase db = getReadDb();
        if (db == null) return null;
        String sqlwhere = String.format("Manufact1 = %s", manfacturerId);
        Cursor cur = db.query("StandVehmanfacturers", null, sqlwhere, null, null, null, null);
        if (cur.moveToNext()) {
            info = cur.getString(cur.getColumnIndex("Manufact4"));
        }
        cur.close();
        db.close();
        return info;
    }
}
