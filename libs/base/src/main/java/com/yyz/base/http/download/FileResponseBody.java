package com.yyz.base.http.download;

import android.util.Log;

import com.yyz.base.http.Callback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 请求实体，进度条
 * Created by Administrator on 2017/7/20.
 */

public final class FileResponseBody<T> extends ResponseBody {
    /**
     * 实际请求体
     */
    private ResponseBody mResponseBody;
    /**
     * 下载回调接口
     */
    private Callback.ProgressCallback<T> mCallback;
    /**
     * BufferedSource
     */
    private BufferedSource mBufferedSource;

    public FileResponseBody(ResponseBody responseBody, Callback.ProgressCallback<T> callback) {
        this.mResponseBody = responseBody;
        this.mCallback = callback;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    /**
     * 回调进度接口
     *
     * @param source
     * @return Source
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                Log.i("i","---------------------------------------" + totalBytesRead);
//                mMainThread.post(new Runnable() {
//                    @Override
//                    public void run() {
                if (mCallback != null)
                    mCallback.onLoading(mResponseBody.contentLength(), totalBytesRead);
//                    }
//                });
                return bytesRead;
            }
        };
    }
}
