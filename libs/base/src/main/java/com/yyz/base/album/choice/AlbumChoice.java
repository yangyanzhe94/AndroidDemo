package com.yyz.base.album.choice;

import android.content.Context;

import com.yyz.base.album.impl.Choice;
import com.yyz.base.album.wrapper.AlbumMultipleWrapper;
import com.yyz.base.album.wrapper.AlbumSingleWrapper;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AlbumChoice implements Choice<AlbumMultipleWrapper,AlbumSingleWrapper> {
    private Context mContext;

    public AlbumChoice(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public AlbumMultipleWrapper multipleChoice() {
        return new AlbumMultipleWrapper(mContext);
    }

    @Override
    public AlbumSingleWrapper singleChoice() {
        return new AlbumSingleWrapper(mContext);
    }
}
