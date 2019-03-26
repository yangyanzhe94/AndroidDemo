package com.yyz.base.album.divider;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/9/4.
 */

public abstract class Divider extends RecyclerView.ItemDecoration {
    public abstract int getHeight();

    public abstract int getWidth();
}
