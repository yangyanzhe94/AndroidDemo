package com.yyz.base.recycler.refresh.impl;

import android.view.MotionEvent;
import android.view.View;

import com.yyz.base.recycler.refresh.api.ScrollBoundaryDecider;
import com.yyz.base.recycler.refresh.util.ScrollBoundaryUtil;

/**
 * 滚动边界
 */

@SuppressWarnings("WeakerAccess")
public class ScrollBoundaryDeciderAdapter implements ScrollBoundaryDecider {

    //<editor-fold desc="Internal">
    protected MotionEvent mActionEvent;
    protected ScrollBoundaryDecider boundary;
    protected boolean mEnableLoadMoreWhenContentNotFull;

    void setScrollBoundaryDecider(ScrollBoundaryDecider boundary){
        this.boundary = boundary;
    }

    void setActionEvent(MotionEvent event) {
        mActionEvent = event;
    }
    //</editor-fold>

    //<editor-fold desc="ScrollBoundaryDecider">
    @Override
    public boolean canRefresh(View content) {
        if (boundary != null) {
            return boundary.canRefresh(content);
        }
        return ScrollBoundaryUtil.canRefresh(content, mActionEvent);
    }

    @Override
    public boolean canLoadmore(View content) {
        if (boundary != null) {
            return boundary.canLoadmore(content);
        }
        if (mEnableLoadMoreWhenContentNotFull) {
            return !ScrollBoundaryUtil.canScrollDown(content, mActionEvent);
        }
        return ScrollBoundaryUtil.canLoadmore(content, mActionEvent);
    }

    public void setEnableLoadMoreWhenContentNotFull(boolean enable) {
        mEnableLoadMoreWhenContentNotFull = enable;
    }
    //</editor-fold>
}
