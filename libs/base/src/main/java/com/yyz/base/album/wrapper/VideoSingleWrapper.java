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

public class VideoSingleWrapper extends BaseChoiceWrapper<VideoSingleWrapper, List<AlbumFile>, AlbumFile> {
    public VideoSingleWrapper(Context context) {
        super(context);
    }

    @Override
    public void start() {
        //TODO 视频选择
        AlbumActivity.mAlbumChoiceListener = mAlbumListener;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Album.REQUEST_CODE, mRequestCode);
        bundle.putInt(Album.MEDIA_TYPE, Album.MEDIA_TYPE_VIDEO);
        bundle.putInt(Album.SHOW_TYPE, mShowType);
        bundle.putInt(Album.LIMIT_COUNT, 1);
        bundle.putInt(Album.MODE_TYPE,Album.MODE_SINGLE);  //单选
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
