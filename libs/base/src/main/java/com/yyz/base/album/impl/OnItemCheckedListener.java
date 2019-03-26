package com.yyz.base.album.impl;

import android.widget.CompoundButton;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface OnItemCheckedListener {
    void onCheckedChanged(CompoundButton compoundButton, int position, boolean isChecked);
}
