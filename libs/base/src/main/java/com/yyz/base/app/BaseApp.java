package com.yyz.base.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.yyz.base.utils.ToastUtils;

/**
 * Created by Administrator on 2017/9/26.
 */

public abstract class BaseApp  extends Application implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        CrashHandler.getInstance().init(this);
        ToastUtils.init(getApplicationContext());
        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppManager.addAct(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppManager.removeAct(activity);

    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        AppManager.removeActs();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}