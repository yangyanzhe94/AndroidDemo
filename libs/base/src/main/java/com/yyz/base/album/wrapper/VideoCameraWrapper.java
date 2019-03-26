package com.yyz.base.album.wrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.yyz.base.album.Album;
import com.yyz.base.album.activity.AlbumActivity;
import com.yyz.base.album.base.BaseCameraWrapper;
import com.yyz.base.album.util.AlbumUtil;

/**
 * Created by Administrator on 2017/8/31.
 */

public class VideoCameraWrapper extends BaseCameraWrapper<VideoCameraWrapper> {
    @IntRange(from = 0, to = 1)
    private int mQuality = 1;
    @IntRange(from = 1, to = Long.MAX_VALUE)
    private long mLimitDuration = Long.MAX_VALUE;
    private long mLimitBytes = AlbumUtil.getAvailableDiskSize();  //获取剩余存储空间

    public VideoCameraWrapper(@NonNull Context mContext) {
        super(mContext);
    }

    /**
     * Currently value 0 means low quality, suitable for MMS messages, and  value 1 means high quality.
     */
    public VideoCameraWrapper quality(@IntRange(from = 0, to = 1) int quality) {
        this.mQuality = quality;
        return this;
    }

    /**
     * Specify the maximum allowed recording duration in seconds.
     */
    public VideoCameraWrapper limitDuration(@IntRange(from = 1, to = Long.MAX_VALUE) long duration) {
        this.mLimitDuration = duration;
        return this;
    }

    /**
     *  Specify the maximum allowed size.
     *  1mb = 1024 kb = 1024*1024 Byte
     * @param bytes
     * @return
     */
    public VideoCameraWrapper limitBytes(@IntRange(from = 1, to = Long.MAX_VALUE) long bytes) {
        //当设置录制视频文件大于本地磁盘时候，按照剩余磁盘空间录制
        if (bytes < mLimitBytes)
            this.mLimitBytes = bytes;
        return this;
    }

    @Override
    public void start() {
        AlbumActivity.mAlbumCameraListener = mAlbumListener;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Album.REQUEST_CODE, mRequestCode);   //请求code
        bundle.putInt(Album.MEDIA_TYPE, Album.MEDIA_TYPE_CAMERA_VIDEO);
        bundle.putInt(Album.CAMERA_VIDEO_QUALITY, mQuality);
        bundle.putLong(Album.CAMERA_VIDEO_LIMIT_DURATION, mLimitDuration);
        bundle.putLong(Album.CAMERA_VIDEO_LIMIT_BYTES, mLimitBytes);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
