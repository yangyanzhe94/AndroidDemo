package com.yyz.base.recycler.interfaces;

import android.view.View;

/**
 * 长按回调
 * Created by Administrator on 2017/3/27.
 */

public interface OnItemLongListener {
    /**
     * item 长按
     *
     * @param view
     * @param position
     */
    boolean onItemLongClick(View view, int position);
}
