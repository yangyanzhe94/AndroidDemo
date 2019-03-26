package com.yyz.base.album.base;

import android.content.Context;
import android.support.annotation.Nullable;

import com.yyz.base.album.impl.AlbumListener;

/**
 * Created by Administrator on 2017/8/31.
 */

public abstract class BaseAlbumWrapper<T extends BaseAlbumWrapper, Result, Checked> {

    protected Context mContext;
    protected AlbumListener<Result> mAlbumListener;
    protected int mRequestCode;
    protected
    @Nullable
    Checked mChecked;


    public BaseAlbumWrapper(Context context) {
        this.mContext = context;
    }

    public T setListener(AlbumListener<Result> albumListener) {
        this.mAlbumListener = albumListener;
        return (T) this;
    }


    /**
     * Request tag.
     */
    public T requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return (T) this;
    }



    /**
     * Start up.
     */
    public abstract void start();
}
