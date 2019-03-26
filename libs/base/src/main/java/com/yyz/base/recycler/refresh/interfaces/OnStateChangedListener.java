package com.yyz.base.recycler.refresh.interfaces;

import com.yyz.base.recycler.refresh.api.RefreshLayoutImpl;
import com.yyz.base.recycler.refresh.constant.RefreshState;

/**
 * 刷新状态改变监听器
 * Created by Administrator on 2017/8/21.
 */

public interface OnStateChangedListener {
    /**
     * 状态改变事件 {@link  RefreshState}
     *
     * @param refreshLayoutImpl {@link RefreshLayoutImpl}
     * @param oldState      改变之前的状态
     * @param newState      改变之后的状态
     */
    void onStateChanged(RefreshLayoutImpl refreshLayoutImpl, RefreshState oldState,
        RefreshState newState);
}
