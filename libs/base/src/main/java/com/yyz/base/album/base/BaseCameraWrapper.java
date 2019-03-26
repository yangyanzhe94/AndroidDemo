package com.yyz.base.album.base;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/8/31.
 */

public abstract class BaseCameraWrapper<T extends BaseCameraWrapper> extends BaseAlbumWrapper<T, String, String> {

    @Nullable
    String mFilePath;

    public BaseCameraWrapper(Context context) {
        super(context);
    }

    /**
     * Set the image storage path.
     */
    public T filePath(@Nullable String filePath) {
        this.mFilePath = filePath;
        return (T) this;
    }


}
