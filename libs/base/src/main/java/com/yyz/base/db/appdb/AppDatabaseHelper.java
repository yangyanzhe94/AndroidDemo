package com.yyz.base.db.appdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 开发者自身的数据库，非服务器下载下来的数据库
 * Created by Administrator on 2017/7/4.
 */

public class AppDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "app_data.db";
    //数据库升级，放弃老数据。
    public static final int DB_VERSION = 1;

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DownLoadDatabase.DownLoad.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(DownLoadDatabase.DownLoad.DROP_SQL);
//        onCreate(db);
    }
}
