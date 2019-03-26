package com.yyz.base.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/7/3.
 */

public class UtilsManager {
    private static Context mContext;

    private UtilsManager() {
    }

    private final static UtilsManager mInstance = new UtilsManager();

    public final static UtilsManager getInstance(Context context) {
        UtilsManager.mContext = context;
        ToastUtils.init(mContext);
        return mInstance;
    }

}
