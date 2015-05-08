package com.thinkdo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.thinkdo.entity.GloVariable;

/**
 * Created by xh on 15/5/8.
 */
public class CustomDbUtil {

    public SQLiteDatabase getWriteDb() {
        String path = GloVariable.context.getDatabasePath(GloVariable.customSqliteName).getAbsolutePath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SQLiteDatabase getReadDb() {
        String path = GloVariable.context.getDatabasePath(GloVariable.customSqliteName).getAbsolutePath();
        try {
            return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }


}
