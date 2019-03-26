package com.yyz.base.db.appdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.yyz.base.db.appdb.table.AppData;

/**
 * Created by Administrator on 2017/7/4.
 */

public class AppDatabase {
    private  AppDatabaseHelper helper;

    public AppDatabase(Context context) {
        super();
//        helper = new AppDatabaseHelper(context);
    }

    /**
     * 插入数据
     * @param id
     * @param name
     * @param type
     */
    public synchronized void insert(String id,String name,int type){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(AppData.INSERT_SQL);
        sqLiteDatabase.close();
    }

    /**
     * 更新数据
     * @param id
     * @param name
     * @param type
     */
    public synchronized void update(String id,String name,int type){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(AppData.UPDATE_SQL,new Object[]{id,name,type});
    }

    /**
     * 删除 一条历史查询记录
     *
     * @param id
     */
    public synchronized void delete(int id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(AppData.DELETE_SQL, new Integer[]{id});
        sqLiteDatabase.close();
    }

    /**
     * 删除所有数据
     */
    public synchronized void deleteAll() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.execSQL(AppData.DELETE_ALL_SQL);
        sqLiteDatabase.close();
    }


    public synchronized void destroy() {
        helper.close();
    }
}
