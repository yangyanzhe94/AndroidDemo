package com.yyz.base.album.impl;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/8/31.
 */

public interface AlbumListener<T> {
    /**
     * The results callback, only when the success of the callback.
     *
     * @param requestCode requestCode
     * @param result      it might be a AlbumFile list or a String, depending on what function you use.
     */
    void onAlbumResult(int requestCode, @NonNull T result);

    /**
     * Callback when operation is canceled.
     *
     * @param requestCode requestCode
     */
    void onAlbumCancel(int requestCode);

}
