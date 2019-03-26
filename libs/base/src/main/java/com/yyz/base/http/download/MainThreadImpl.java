package com.yyz.base.http.download;


import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2016/12/20.
 */

public class MainThreadImpl implements IMainThread {

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    private Handler mHandler;
    private static MainThreadImpl sMMainThreadImpl = new MainThreadImpl();

    public static MainThreadImpl getMainThread() {
        return sMMainThreadImpl;
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}

