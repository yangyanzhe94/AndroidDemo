package com.yyz.base.recycler.refresh.impl;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.yyz.base.recycler.refresh.RefreshLayout;
import com.yyz.base.recycler.refresh.api.RefreshFooter;
import com.yyz.base.recycler.refresh.api.RefreshKernel;
import com.yyz.base.recycler.refresh.api.RefreshLayoutImpl;
import com.yyz.base.recycler.refresh.constant.RefreshState;
import com.yyz.base.recycler.refresh.constant.SpinnerStyle;

/**
 * 刷新底部包装
 */

public class RefreshFooterWrapper implements RefreshFooter {

    private static final String TAG_REFRESH_FOOTER_WRAPPER = "TAG_REFRESH_FOOTER_WRAPPER";

    private View mWrapperView;
    private SpinnerStyle mSpinnerStyle;

    public RefreshFooterWrapper(View wrapper) {
        this.mWrapperView = wrapper;
        this.mWrapperView.setTag(TAG_REFRESH_FOOTER_WRAPPER.hashCode(), TAG_REFRESH_FOOTER_WRAPPER);
    }

    public static boolean isTagedFooter(View view) {
        return TAG_REFRESH_FOOTER_WRAPPER.equals(view.getTag(TAG_REFRESH_FOOTER_WRAPPER.hashCode()));
    }

    @NonNull
    public View getView() {
        return mWrapperView;
    }

    @Override
    public int onFinish(RefreshLayoutImpl layout, boolean success) {
		return 0;
	}

    @Override@Deprecated
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        if (mSpinnerStyle != null) {
            return mSpinnerStyle;
        }
        ViewGroup.LayoutParams params = mWrapperView.getLayoutParams();
        if (params instanceof RefreshLayout.LayoutParams) {
            mSpinnerStyle = ((RefreshLayout.LayoutParams) params).spinnerStyle;
            if (mSpinnerStyle != null) {
                return mSpinnerStyle;
            }
        }
        if (params != null) {
            if (params.height == 0) {
                return mSpinnerStyle = SpinnerStyle.Scale;
            }
        }
        return mSpinnerStyle = SpinnerStyle.Translate;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        ViewGroup.LayoutParams params = mWrapperView.getLayoutParams();
        if (params instanceof RefreshLayout.LayoutParams) {
            kernel.requestDrawBackgroundForFooter(((RefreshLayout.LayoutParams) params).backgroundColor);
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onStartAnimator(RefreshLayoutImpl layout, int footerHeight, int extendHeight) {

    }

    @Override
    public void onStateChanged(RefreshLayoutImpl refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    @Override
    public boolean setLoadMoreFinished(boolean finished) {
        return false;
    }
}
