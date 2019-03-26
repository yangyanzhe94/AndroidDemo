package com.yyz.base.base;

import android.content.Context;

import com.yyz.base.app.AppManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28
 */

public abstract class BasePresenter<V> {
    protected V mView;
    protected String mTag = getClassName();
    protected Context mContext;
    protected Map<String, Object> mParamMap = new HashMap<>();


    private String getClassName() {
        return getClass().getSimpleName();
    }

    /**
     * 绑定view
     *
     * @param view
     */
    public void attach(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    /**
     * 解绑View
     */
    public void detach() {
        AppManager.http().cancelCall(mTag);
        this.mView = null;
        this.mContext = null;
        this.mTag = null;
    }

}
