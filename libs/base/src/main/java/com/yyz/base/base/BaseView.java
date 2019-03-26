package com.yyz.base.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface BaseView {

    /**
     * 显示加载布局 loading
     */
    void showLoading(@LayoutRes int loadRes, @IdRes int clickId);

    void showLoading();

    /**
     * 隐藏加载布局 loading
     */
    void hideLoading();

    /**
     * Toast
     *
     * @param toastMsgId
     */
    void toast(@StringRes int toastMsgId);

    void toast(String toastMsg);

    /**
     * 显示空数据布局
     * @param emptyResId  图片Id
     * @param textTitle    textTitle == null ? 默认title : textTitle
     * @param textContent textContent == null ? 默认content : textContent
     */
    void showEmpty(@DrawableRes int emptyResId, String textTitle, String textContent);

    void showEmpty();

    /**
     * 隐藏空数据布局
     */
    void hideEmpty();

    /**
     * 显示加载数据错误布局
     */
    void showError(@DrawableRes int emptyResId, String textTitle, String textContent);

    void showError();

    /**
     * 隐藏加载数据错误布局
     */
    void hideError();

    /**
     * 显示请求超时
     */
    void showTimeOut(@DrawableRes int emptyResId, String textTitle, String textContent);

    void showTimeOut();

    /**
     * 隐藏请求超时
     */
    void hideTimeOut();

    void startNewActivity(Class<?> clz);

    void startNewActivity(Class<?> clz, Bundle bundle);

    void startNewActivityForResult(Class<?> clz, Bundle bundle, int requestCode);

    /**
     * 显示键盘
     */
    void onShowInput();

    /**
     * 隐藏键盘
     */
    void onHideInput();

    /**
     * 屏蔽快速连续点击
     * @return
     */
    boolean clickInterval();

}
