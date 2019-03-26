package com.yyz.base.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Toast
 * 避免同样的信息多次触发重复弹出的问题
 * Created by Administrator on 2017/3/10.
 */

public class ToastUtils {
    private static Context mContext;
    private static String oldMsg;
    private static Snackbar snackbar;

    private ToastUtils() {
        throw new RuntimeException("ToastUtils cannot be initialized!");
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static void showToast(View view, String s) {
        if (mContext == null || s == null || view == null) {
            return;
        }
        if (snackbar == null) {
            snackbar = Snackbar.make(view, s, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            if (s.equals(oldMsg)) {
                if (!snackbar.isShown()) {
                    snackbar = Snackbar.make(view, s, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            } else {
                oldMsg = s;
                snackbar = Snackbar.make(view, s, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
    }
}