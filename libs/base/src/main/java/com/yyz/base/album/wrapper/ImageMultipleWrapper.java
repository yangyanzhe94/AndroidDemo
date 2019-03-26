package com.yyz.base.album.wrapper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;

import com.yyz.base.album.Album;
import com.yyz.base.album.activity.AlbumActivity;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.base.BaseChoiceWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public final class ImageMultipleWrapper extends BaseChoiceWrapper<ImageMultipleWrapper,List<AlbumFile>, List<AlbumFile>> {

    @IntRange(from = 1, to = Integer.MAX_VALUE)
    private int mLimitCount = Integer.MAX_VALUE;

    public ImageMultipleWrapper(Context context) {
        super(context);
    }


    /**
     * Set the list has been selected.
     */
    public final ImageMultipleWrapper checkedList(List<AlbumFile> checked) {
        if (checked != null){
            this.mChecked = checked;
        }else {
            this.mChecked = new ArrayList<>();
        }
        return this;
    }

    /**
     * Set the maximum number to be selected.
     */
    public ImageMultipleWrapper selectCount(@IntRange(from = 1, to = Integer.MAX_VALUE) int count) {
        this.mLimitCount = count;
        return this;
    }

    @Override
    public void start() {
        AlbumActivity.mAlbumChoiceListener = mAlbumListener;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Album.REQUEST_CODE, mRequestCode);   //请求code
        bundle.putParcelableArrayList(Album.ALBUM_CHECKED_DATA, (ArrayList)mChecked);  //已经选择list
        bundle.putInt(Album.MEDIA_TYPE, Album.MEDIA_TYPE_IMAGE);   //选择类型（图片）
        bundle.putInt(Album.SHOW_TYPE,mShowType);                  //展示文件or文件夹（默认文件）
        bundle.putInt(Album.LIMIT_COUNT,mLimitCount);              //最大选择图片数量
        bundle.putInt(Album.MODE_TYPE,Album.MODE_MULTIPLE);  //多选
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }
}
