package com.yyz.base.recycler.interfaces;

import android.view.View;

/**
 * 点击事件回调
 * Created by Administrator on 2017/3/27.
 */

public interface OnItemClickListener {
    /**
     * item 单击
     *
     * @param view      点击view
     * @param clickType 区分点击模块
     * @param position
     */
    void onItemClick(View view, int clickType, int position);
}
