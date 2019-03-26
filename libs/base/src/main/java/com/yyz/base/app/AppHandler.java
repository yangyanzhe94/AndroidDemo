package com.yyz.base.app;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/25.
 */

public class AppHandler extends Handler {
    private WeakReference<AppCompatActivity> reference;
    private HandlerListener mHandlerListener;

    public AppHandler(Context context, HandlerListener handlerListener) {
        mHandlerListener = handlerListener;
        if (context instanceof AppCompatActivity)
            reference = new WeakReference<AppCompatActivity>((AppCompatActivity) context);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mHandlerListener != null && reference != null) {
            AppCompatActivity appCompatActivity = reference.get();
            if (appCompatActivity != null) {
                mHandlerListener.handleMessage(msg);
            }
        }
    }

}
