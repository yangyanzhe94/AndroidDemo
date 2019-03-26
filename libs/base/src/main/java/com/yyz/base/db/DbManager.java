package com.yyz.base.db;

import android.content.Context;

import com.yyz.base.db.appdb.AppDatabase;

/**
 * Created by Administrator on 2017/6/30.
 */

public final class DbManager {
    private Context mContext;
    private AppDatabase mAppDataBase;

    private DbManager(Context context) {
        mContext = context;
    }

    private static DbManager mInstance;

    public static DbManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new DbManager(context);
        return mInstance;
    }

    public AppDatabase appData() {
        if (mAppDataBase == null)
            mAppDataBase = new AppDatabase(mContext);
        return mAppDataBase;
    }

}
