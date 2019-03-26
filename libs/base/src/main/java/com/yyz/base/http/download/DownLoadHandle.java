package com.yyz.base.http.download;

import com.yyz.base.app.AppManager;

import java.util.concurrent.ExecutorService;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 获取下载文件的数据信息
 *
 * Created by Administrator on 2017/7/24.
 */

public class DownLoadHandle {

    private ExecutorService mExecutorService;
    DownLoadHandle(ExecutorService executorService){
        mExecutorService = executorService;
    }


    void getDownLoadInformation(DownLoadEntity downLoadEntity, DownLoadHandleListener downLoadHandleListener){
        Call<ResponseBody> call =  AppManager.http().getApiServes().getHttpHeader(downLoadEntity.url,"bytes=0-0");
        DownLoadHandleRunnable countRunnable = new DownLoadHandleRunnable(call, downLoadHandleListener);
        mExecutorService.submit(countRunnable);
    }

}
