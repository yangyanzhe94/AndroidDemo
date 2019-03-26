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

public class VideoMultipleWrapper extends BaseChoiceWrapper<VideoMultipleWrapper, List<AlbumFile>, List<AlbumFile>> {
    @IntRange(from = 1, to = Integer.MAX_VALUE)
    private int mLimitCount = Integer.MAX_VALUE;

    public VideoMultipleWrapper(Context context) {
        super(context);
    }

    /**
     * Set the list has been selected.
     */
    public final VideoMultipleWrapper checkedList(List<AlbumFile> checked) {
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
    public VideoMultipleWrapper selectCount(@IntRange(from = 1, to = Integer.MAX_VALUE) int count) {
        this.mLimitCount = count;
        return this;
    }

    @Override
    public void start() {
        AlbumActivity.mAlbumChoiceListener = mAlbumListener;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Album.REQUEST_CODE, mRequestCode);
        bundle.putParcelableArrayList(Album.ALBUM_CHECKED_DATA, (ArrayList)mChecked);
        bundle.putInt(Album.MEDIA_TYPE, Album.MEDIA_TYPE_VIDEO);
        bundle.putInt(Album.SHOW_TYPE,mShowType);
        bundle.putInt(Album.LIMIT_COUNT,mLimitCount);
        bundle.putInt(Album.MODE_TYPE,Album.MODE_MULTIPLE);  //多选
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
