package com.yyz.base.http.download;

import com.yyz.base.http.Callback;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/7/24.
 */

public class DownCallBackListener implements Callback.ProgressCallback {
    private MainThreadImpl mMainThread = MainThreadImpl.getMainThread();
    private Map<String, Map<Integer, Future>> mUrlTaskMap;
    private Callback.ProgressCallback mProgressCallback;
    private boolean isReturnStart;
    private boolean isReturnErr;
    private boolean isReturnCancel;
    private long mDownSize;
    private long mTotal;


    DownCallBackListener(Map<String, Map<Integer, Future>> urlTaskMap, Callback.ProgressCallback progressCallback) {
        this.mUrlTaskMap = urlTaskMap;
        this.mProgressCallback = progressCallback;
    }


    @Override
    public synchronized void onStarted(final String tag) {
        if (!isReturnStart) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mProgressCallback.onStarted(tag);
                }
            });
        }
        isReturnStart = true;
    }

    @Override
    public synchronized void onLoading(final long total, final long current) {
        mTotal = total;
        mDownSize += current;
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mProgressCallback.onLoading(total, mDownSize);
            }
        });
    }

    @Override
    public synchronized void onCompleted(final String tag, final int dataId, final String savePath, final String saveName) {
        removeTask(tag,dataId);
        if (mTotal != 0L && mTotal == mDownSize) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mProgressCallback.onCompleted(tag, dataId, savePath, saveName);
                }
            });
        }
    }

    @Override
    public synchronized void onError(final String tag,final Throwable throwable) {
        cancel(tag);
        if (!isReturnErr) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mProgressCallback.onError(tag,throwable);
                }
            });
        }
        isReturnErr = true;
    }

    @Override
    public synchronized void onCancel(final String tag) {
        cancel(tag);
        if (!isReturnCancel) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mProgressCallback.onCancel(tag);
                }
            });
        }
        isReturnCancel = true;
    }


    //移除map记录 只有在完成后移除
    private boolean removeTask(String tag,int dataId) {
        Map<Integer, Future> map = mUrlTaskMap.get(tag);
        if (map.isEmpty()) {
            return true;
        }
        if (map.containsKey(dataId)) {
            map.remove(dataId);
        }
        if (map.size() == 0) {
            mUrlTaskMap.remove(tag);
        }
        return mUrlTaskMap.size() == 0;
    }

    //取消当前url所有任务
    private void cancel(String tag) {
        Map<Integer, Future> downLoadMap = mUrlTaskMap.get(tag);
        if (downLoadMap != null && downLoadMap.size() > 0) {
            Iterator iterator = downLoadMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Future> map = (Map.Entry<Integer, Future>) iterator.next();
                Future future = map.getValue();
                future.cancel(true);
                iterator.remove();
            }
            mUrlTaskMap.remove(tag);
        }
    }
}
