package com.yyz.base.http.download;


import com.yyz.base.app.AppManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/20.
 */

public class DownLoadRunnable implements Runnable {
    private DownLoadRunnable() {
    }

    private DownCallBackListener mDownCallBackListener;
    private Call<ResponseBody> mResponseCall;
    private DownLoadEntity mDownLoadEntity;

    public DownLoadRunnable(DownLoadEntity downLoadEntity, DownCallBackListener downCallBackListener) {
        this.mDownCallBackListener = downCallBackListener;
        this.mDownLoadEntity = downLoadEntity;
    }


    @Override
    public void run() {
        //设置线程优先级
//        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        mResponseCall = AppManager.http().getApiServes().download(mDownLoadEntity.url);
        ResponseBody result = null;
        try {
            Response response = mResponseCall.execute();
            if (response.isSuccessful()) {
                result = (ResponseBody) response.body();
                writeTo(result);
            } else {
                if (mDownCallBackListener != null)
                    if (response.errorBody() != null) {
                        final String error = response.errorBody().string();
                        mDownCallBackListener.onError(mDownLoadEntity.tag, new Throwable(error));
                    } else {
                        mDownCallBackListener.onError(mDownLoadEntity.tag, new Throwable("未知错误"));
                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param responseBody
     */
    private void writeTo(ResponseBody responseBody) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        File file;
        try {
            try {
                file = new File(mDownLoadEntity.savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(mDownLoadEntity.savePath, mDownLoadEntity.saveName);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                inputStream = responseBody.byteStream();
                fileOutputStream = new FileOutputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] bytes = new byte[1024 * 200];
                int len;
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                    if (mDownCallBackListener != null) {
                        mDownCallBackListener.onLoading(mDownLoadEntity.total, len);
                    }
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bufferedInputStream.close();
                inputStream.close();
                if (mDownCallBackListener != null) {
                    mDownCallBackListener.onCompleted(mDownLoadEntity.tag, mDownLoadEntity.dataId, mDownLoadEntity.savePath, mDownLoadEntity.saveName);
                }
                return;
            } catch (final IOException e) {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (bufferedInputStream != null)
                    bufferedInputStream.close();
                if (inputStream != null)
                    inputStream.close();
                if (mDownCallBackListener != null) {
                    mDownCallBackListener.onError(mDownLoadEntity.tag, e);
                }
            }
        } catch (final IOException e) {
            if (mDownCallBackListener != null) {
                mDownCallBackListener.onError(mDownLoadEntity.tag, e);
            }
        }

    }
}
