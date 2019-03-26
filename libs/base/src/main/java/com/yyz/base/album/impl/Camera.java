package com.yyz.base.album.impl;

/**
 * Created by Administrator on 2017/8/31.
 */

public interface Camera<Image,Video> {
    /**
     * Take picture.
     */
    Image image();

    /**
     * Record video.
     */
    Video video();
}
