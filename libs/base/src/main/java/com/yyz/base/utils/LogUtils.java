package com.yyz.base.utils;

import android.util.Log;

import com.yyz.base.app.AppManager;

/**
 * Created by Administrator on 2017/7/3.
 */

public class LogUtils {
    public static void i(String str) {
        if (AppManager.isDebug()) {
            Log.i("i", str);
        }
    }


    public static void d(String str) {
        if (AppManager.isDebug()) {
            Log.d("--------d", str);
        }
    }

    public static void http(String str) {
        if (AppManager.isDebug()) {
            Log.d("httpRequest", str);
        }
    }
}
