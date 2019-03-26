package com.yyz.base.album.impl;

import com.yyz.base.album.model.AlbumFile;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public interface AlbumCallback {
    /**
     * Photo album callback selection result.
     *
     * @param albumFiles file path list.
     */
    void onAlbumResult(List<AlbumFile> albumFiles);

    /**
     * The album canceled the operation.
     */
    void onAlbumCancel();
}
