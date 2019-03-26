package com.yyz.base.album.impl;

/**
 * Created by Administrator on 2017/8/31.
 */

public interface Choice <Multiple, Single> {
    /**
     * Multiple choice.
     */
    Multiple multipleChoice();

    /**
     * Single choice.
     */
    Single singleChoice();
}
