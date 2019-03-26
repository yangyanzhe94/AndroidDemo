package com.yyz.base.album.choice;

import android.content.Context;

import com.yyz.base.album.impl.Camera;
import com.yyz.base.album.wrapper.ImageCameraWrapper;
import com.yyz.base.album.wrapper.VideoCameraWrapper;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AlbumCamera implements Camera<ImageCameraWrapper,VideoCameraWrapper> {
    private Context mContext;
    public AlbumCamera(Context context) {
        this.mContext = context;
    }

    @Override
    public ImageCameraWrapper image() {
        return new ImageCameraWrapper(mContext);
    }

    @Override
    public VideoCameraWrapper video() {
        return new VideoCameraWrapper(mContext);
    }
}
