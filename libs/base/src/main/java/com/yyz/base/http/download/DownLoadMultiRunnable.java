package com.yyz.base.http.download;

import com.yyz.base.app.AppManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/21.
 */

public class DownLoadMultiRunnable implements Runnable {
    private DownLoadEntity mDownLoadEntity;

    private DownLoadMultiRunnable() {
    }

    private DownCallBackListener mDownCallBackListener;
    private Call<ResponseBody> mResponseCall;

    public DownLoadMultiRunnable(DownLoadEntity downLoadEntity, DownCallBackListener downCallBackListener) {
        this.mDownLoadEntity = downLoadEntity;
        this.mDownCallBackListener = downCallBackListener;
    }

    @Override
    public void run() {
        //设置线程优先级
//        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        mResponseCall = AppManager.http().getApiServes().download(mDownLoadEntity.url, "bytes=" + mDownLoadEntity.start + "-" + mDownLoadEntity.end);
        ResponseBody result = null;
        try {
            Response response = mResponseCall.execute();
            if (response.isSuccessful()) {
                result = (ResponseBody) response.body();
                writeTo(result, mDownLoadEntity.start, mDownLoadEntity.end);
            } else {
                if (response.errorBody() != null) {
                    String error = response.errorBody().string();
                    onError(mDownLoadEntity.tag,new Throwable(error));

                } else {
                    onError(mDownLoadEntity.tag,new Throwable("未知错误"));
                }
            }

        } catch (IOException e) {
            if (e instanceof InterruptedIOException && !(e instanceof SocketTimeoutException)) {
                onCancel(mDownLoadEntity.tag);
            } else {
                onError(mDownLoadEntity.tag,e);
            }
        }
    }

    /**
     * 写入数据
     *
     * @param responseBody
     * @param start
     * @param end
     */
    private void writeTo(ResponseBody responseBody, long start, long end) {
        InputStream inputStream = null;
        RandomAccessFile oSavedFile = null;
        File futureStudioIconFile;
        try {
            try {
                futureStudioIconFile = new File(mDownLoadEntity.savePath);
                if (!futureStudioIconFile.exists()){
                    futureStudioIconFile.mkdirs();
                }
                futureStudioIconFile = new File(mDownLoadEntity.savePath, mDownLoadEntity.saveName);
                if (!futureStudioIconFile.exists()) {
                    futureStudioIconFile.createNewFile();
                }
                oSavedFile = new RandomAccessFile(futureStudioIconFile, "rw");
                oSavedFile.seek(start);
                inputStream = responseBody.byteStream();
                byte[] bytes = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    oSavedFile.write(bytes, 0, len);
                    onLoading(mDownLoadEntity.total, len);
                }
                onCompleted(mDownLoadEntity);
                return;
            } catch (IOException e) {
                onError(mDownLoadEntity.tag,e);
            } finally {
                if (oSavedFile != null) {
                    oSavedFile.close();
                }
                if (inputStream != null)
                    inputStream.close();
            }

        } catch (final IOException e) {
            onError(mDownLoadEntity.tag,e);
        }

    }

    private void onLoading(long total, long current) {
        mDownCallBackListener.onLoading(total,current);
    }

    private void onCompleted(DownLoadEntity downLoadEntity) {
        mDownCallBackListener.onCompleted(downLoadEntity.tag,downLoadEntity.dataId,downLoadEntity.savePath,downLoadEntity.saveName);
    }

    private void onCancel(String tag) {
        mDownCallBackListener.onCancel(tag);
    }

    private void onError(String tag,Throwable throwable) {
        mDownCallBackListener.onError(tag,throwable);
    }
}
