package com.yyz.base.album.wrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.yyz.base.album.Album;
import com.yyz.base.album.activity.AlbumActivity;
import com.yyz.base.album.base.BaseCameraWrapper;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ImageCameraWrapper extends BaseCameraWrapper<ImageCameraWrapper> {

    public ImageCameraWrapper(@NonNull Context mContext) {
        super(mContext);
    }

    @Override
    public void start() {
        AlbumActivity.mAlbumCameraListener = mAlbumListener;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Album.REQUEST_CODE, mRequestCode);   //请求code
        bundle.putInt(Album.MEDIA_TYPE, Album.MEDIA_TYPE_CAMERA_IMAGE);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }
}
