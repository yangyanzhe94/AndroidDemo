package com.yyz.base.http.download;

import com.yyz.base.app.AppManager;
import com.yyz.base.http.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * Created by Administrator on 2017/7/21.
 */

public class DownLoadRequest implements DownLoadHandleListener {
    private Callback.ProgressCallback mProgressCallback;
    /**
     * 下载参数包含:url、文件保存路径、文件名称、字节流等
     */
    private DownLoadEntity mDownLoadEntity;
    /**
     * 多线程下载文件最低大小10mb
     */
    private long mMultiLine = 10 * 1024 * 1024;

    /**
     * 下载总回调
     */
    private DownCallBackListener mDownCallBackListener;
    /**
     * 下载线程池
     */
    private ExecutorService mExecutorService;
    /**
     * 下载task
     */
    private Map<String, Map<Integer, Future>> mUrlTaskMap = new ConcurrentHashMap<>();

    /**
     * 汇总信息
     */
    private DownLoadHandle mDownLoadHandle;
    /**
     * 最大请求错误重试次数
     */
    private int maxRetryCount = 3;

    private DownLoadRequest() {
    }

    /**
     * 创建实例
     * @param downLoadEntity  下载信息汇总
     * @param progressCallback    回调
     */
    public DownLoadRequest(DownLoadEntity downLoadEntity, Callback.ProgressCallback progressCallback) {
        this.mDownLoadEntity = downLoadEntity;
        this.mProgressCallback = progressCallback;
        this.mExecutorService = AppManager.executor();
        this.mDownLoadHandle = new DownLoadHandle(mExecutorService);
        this.mDownCallBackListener = new DownCallBackListener(mUrlTaskMap,mProgressCallback);
    }

    /**
     *
     */
    public  void start() {
        if (mDownCallBackListener != null) {
            mDownCallBackListener.onStarted(mDownLoadEntity.tag);
        }
        mDownLoadHandle.getDownLoadInformation(mDownLoadEntity, this);
    }

    @Override
    public void success(boolean isBreakpoint, Long fileSize) {
        mDownLoadEntity.total = fileSize;
        down(isBreakpoint && fileSize >= mMultiLine);
    }

    @Override
    public void failed(Throwable throwable) {
        if (maxRetryCount > 0) {
            maxRetryCount--;
            mDownLoadHandle.getDownLoadInformation(mDownLoadEntity, this);
        } else {
            mDownCallBackListener.onError(mDownLoadEntity.tag,throwable);
        }

    }

    /**
     * 判断文件是否存在，并多线程下载。
     */
    private void down(boolean isMulti) {
        //        //判断路径是否存在，不存在则创建路径
        File file = new File(mDownLoadEntity.savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //判断文件是否存在，存在删除。
        file = new File(mDownLoadEntity.savePath, mDownLoadEntity.saveName);
        if (file.exists()) {
            file.delete();
        }
        Map<Integer,Future> integerFutureMap = new HashMap<>();
        if (isMulti) {
            long total = mDownLoadEntity.total;
            long startSize, endSize;
            int threadNum = (int) ((total % mMultiLine == 0) ? total / mMultiLine : total / mMultiLine + 1);
            for (int i = 0; i < threadNum; i++) {
                startSize = i * mMultiLine;
                endSize = startSize + mMultiLine - 1;
                if (i == threadNum - 1) {
                    if (endSize > total) {
                        endSize = total - 1;
                    }
                }
                DownLoadEntity entity = new DownLoadEntity();
                entity.url = mDownLoadEntity.url;
                entity.total = mDownLoadEntity.total;
                entity.start = startSize;
                entity.end = endSize;
                entity.savePath = mDownLoadEntity.savePath;
                entity.saveName = mDownLoadEntity.saveName;
                entity.tag = mDownLoadEntity.tag;
                entity.savePathName = mDownLoadEntity.savePathName;
                entity.dataId = (mDownLoadEntity.url + i).hashCode();
                DownLoadMultiRunnable downRunnable = new DownLoadMultiRunnable(entity, mDownCallBackListener);
                integerFutureMap.put( entity.dataId, mExecutorService.submit(downRunnable));
            }

        } else {
            DownLoadRunnable downLoadRunnable = new DownLoadRunnable(mDownLoadEntity, mDownCallBackListener);
            mDownLoadEntity.dataId = mDownLoadEntity.url.hashCode();
            integerFutureMap.put(mDownLoadEntity.dataId, mExecutorService.submit(downLoadRunnable));
        }

        mUrlTaskMap.put(mDownLoadEntity.tag,integerFutureMap);
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

    //取消所有任务
    public void stop() {
        if (mUrlTaskMap.size() != 0) {
            Iterator iterator = mUrlTaskMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                cancel(key);
                iterator.remove();
            }
        }
    }
}
