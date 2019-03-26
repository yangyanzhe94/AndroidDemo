package com.yyz.base.http;

import android.content.Context;
import android.os.Environment;

import com.yyz.base.http.download.DownLoadEntity;
import com.yyz.base.http.download.DownLoadRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Administrator on 2017/7/21.
 */

public class DownLoadManager {

    private static Context mContext;

    private DownLoadManager() {
    }

    //下载Task
    private Map<String, DownLoadRequest> mDownLoadRequestMap = new ConcurrentHashMap<>();

    private static DownLoadManager mInstance;

    public static DownLoadManager getInstance(Context context) {
        if (mInstance == null) {
            mContext = context;
            mInstance = new DownLoadManager();
        }
        return mInstance;
    }


    public void start(String tag, String url, String saveName, Callback.ProgressCallback progressCallback) {
        this.start(tag, url, saveName, progressCallback, false);
    }

    public void start(final String tag, final String url, final String saveName, final Callback.ProgressCallback progressCallback, final boolean isHead) {
        DownLoadEntity downLoadEntity = new DownLoadEntity();
        downLoadEntity.tag = tag;
        downLoadEntity.url = url;
        downLoadEntity.saveName = saveName;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            downLoadEntity.savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dongdong/download/";
        } else {
            downLoadEntity.savePath = mContext.getCacheDir().getAbsolutePath() + "/download/";
        }
        DownLoadRequest downLoadRequest = new DownLoadRequest(downLoadEntity, progressCallback);
        downLoadRequest.start();
        mDownLoadRequestMap.put(tag, downLoadRequest);
    }

    //取消所有任务
    public void cancel() {
        if (!mDownLoadRequestMap.isEmpty()) {
            Iterator iterator = mDownLoadRequestMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                cancel(key);
            }
        }
    }

    //取消当前tag所有任务
    public void cancel(String tag) {
        if (!mDownLoadRequestMap.isEmpty()) {
            if (mDownLoadRequestMap.containsKey(tag)) {
                mDownLoadRequestMap.get(tag).stop();
                mDownLoadRequestMap.remove(tag);
            }
        }
    }
}
