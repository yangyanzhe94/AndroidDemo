package com.yyz.base.album.choice;

import android.content.Context;

import com.yyz.base.album.impl.Choice;
import com.yyz.base.album.wrapper.ImageMultipleWrapper;
import com.yyz.base.album.wrapper.ImageSingleWrapper;

/**
 * Created by Administrator on 2017/8/31.
 */

public final class ImageChoice implements Choice<ImageMultipleWrapper,ImageSingleWrapper> {
    private Context mContext;
    public ImageChoice(Context context) {
        this.mContext = context;
    }

    @Override
    public ImageMultipleWrapper multipleChoice() {
        return new ImageMultipleWrapper(mContext);
    }

    @Override
    public ImageSingleWrapper singleChoice() {
        return new ImageSingleWrapper(mContext);
    }
}
