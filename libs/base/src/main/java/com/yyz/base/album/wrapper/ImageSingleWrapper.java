package com.yyz.base.album.wrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yyz.base.album.Album;
import com.yyz.base.album.activity.AlbumActivity;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.base.BaseChoiceWrapper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public final class ImageSingleWrapper extends BaseChoiceWrapper<ImageSingleWrapper,List<AlbumFile>, AlbumFile> {
    public ImageSingleWrapper(Context context) {
        super(context);
    }

    @Override
    public void start() {
        AlbumActivity.mAlbumChoiceListener = mAlbumListener;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Album.REQUEST_CODE, mRequestCode);   //请求code
        bundle.putInt(Album.MEDIA_TYPE, Album.MEDIA_TYPE_IMAGE);   //选择类型（图片）
        bundle.putInt(Album.SHOW_TYPE,mShowType);                  //展示文件or文件夹（默认文件）
        bundle.putInt(Album.LIMIT_COUNT,1);              //最大选择图片数量
        bundle.putInt(Album.MODE_TYPE,Album.MODE_SINGLE);  //单选
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
