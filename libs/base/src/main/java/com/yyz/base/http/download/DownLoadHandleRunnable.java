package com.yyz.base.http.download;

import android.text.TextUtils;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/24.
 */

public class DownLoadHandleRunnable implements Runnable {
    private Call<ResponseBody> mResponseCall;
    private DownLoadHandleListener mDownLoadHandleListener;

    DownLoadHandleRunnable(Call<ResponseBody> responseCall, DownLoadHandleListener downLoadHandleListener) {
        this.mResponseCall = responseCall;
        this.mDownLoadHandleListener = downLoadHandleListener;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Response response = null;
        try {
            response = mResponseCall.execute();
            if (response.isSuccessful()) {
                if (mDownLoadHandleListener != null) {
                    if (response.code() == 206 && !TextUtils.isEmpty(response.headers().get("Content-Range"))){
                        mDownLoadHandleListener.success(true,Long.parseLong(response.headers().get("Content-Range").split("/")[1]));
                    }else {
                        mDownLoadHandleListener.success(false,Long.parseLong(response.headers().get("Content-Length")));
                    }
                }
            } else {
                if (mDownLoadHandleListener != null) {
                    mDownLoadHandleListener.failed(new Throwable(response.errorBody() != null ? response.errorBody().string(): "网络异常"));
                }
            }

        } catch (IOException e) {
            if (mDownLoadHandleListener != null) {
                mDownLoadHandleListener.failed(e);
            }
        }finally {
            if (response.body() != null) {
                ((ResponseBody) response.body()).close();
            }
        }
    }
}
