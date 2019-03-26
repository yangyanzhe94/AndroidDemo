package com.yyz.base.album.base;

import android.content.Context;

import com.yyz.base.album.Album;

/**
 * Created by Administrator on 2017/8/31.
 */

public abstract class BaseChoiceWrapper<T extends BaseChoiceWrapper, Result, Checked> extends BaseAlbumWrapper<T, Result, Checked> {
    protected
    @Album.ShowType
    int mShowType = Album.SHOW_FILE;

    public BaseChoiceWrapper(Context context) {
        super(context);
    }

    /**
     * 设置展示图片列表还是文件夹列表
     *
     * @param showType
     * @return
     */
    public T showType(@Album.ShowType int showType) {
        mShowType = showType;
        return (T) this;
    }
}
