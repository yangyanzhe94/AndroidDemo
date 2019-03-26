package com.yyz.base.album.choice;

import android.content.Context;

import com.yyz.base.album.impl.Choice;
import com.yyz.base.album.wrapper.VideoMultipleWrapper;
import com.yyz.base.album.wrapper.VideoSingleWrapper;

/**
 * Created by Administrator on 2017/8/31.
 */

public class VideoChoice implements Choice<VideoMultipleWrapper, VideoSingleWrapper> {
    private Context mContext;

    public VideoChoice(Context context) {
        this.mContext = context;
    }

    @Override
    public VideoMultipleWrapper multipleChoice() {
        return new VideoMultipleWrapper(mContext);
    }

    @Override
    public VideoSingleWrapper singleChoice() {
        return new VideoSingleWrapper(mContext);
    }
}
