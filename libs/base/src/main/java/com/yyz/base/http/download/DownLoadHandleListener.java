package com.yyz.base.http.download;

/**
 * Created by Administrator on 2017/7/24.
 */

public interface DownLoadHandleListener {
    void success(boolean isBreakpoint, Long fileSize);

    void failed(Throwable throwable);
}
