package com.yyz.base.http;


import android.content.Context;
import android.support.annotation.NonNull;

import com.yyz.base.app.AppManager;
import com.yyz.base.http.retrofit.HttpRequest;
import com.yyz.base.utils.SettingHelper;
import com.yyz.base.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/13.
 */

public class HttpManager {
    private static HttpRequest mHttpRequest;
    private static long tokenTime;

    private HttpManager() {
    }

    private static HttpManager mInstance;

    public static HttpManager getInstance(Context context, String baseUrl,boolean isLogRequest) {
        if (mInstance == null) {
            mInstance = new HttpManager();
            mHttpRequest = HttpRequest.getInstance().init(context, baseUrl,isLogRequest);
            tokenTime = SettingHelper.getLong(context, SettingHelper.SettingField.ACCESS_TOKEN_TIME, 0);
        }
        return mInstance;
    }


    public ApiServes getApiServes() {
        return mHttpRequest.getApiServes();
    }


    private synchronized void token() {
//        if (SettingHelper.getBoolean(AppManager.app(), SettingHelper.SettingField.LOGIN_SUCCESS, false) && tokenTime > TimeUtils.getCurTimeMills()) {
//            refreshToken();
//        }
    }


    /**
     * GET 请求
     *
     * @param TAG            页面标记
     * @param url            请求url
     * @param requestCode    请求码
     * @param paramMap       请求参数
     * @param tClass         服务器返回数据Model
     * @param commonCallback 请求回调
     */
    public void get(@NonNull String TAG, String url, int requestCode, Map<String, Object> paramMap, Class tClass, @NonNull Callback.CommonCallback commonCallback) {
        token();
        if (paramMap != null && paramMap.size() > 0) {
//            Map<String, Object> sortMap = getMapSort(paramMap);
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).get(url, AppManager.header().getHeaderMap(paramMap, url), paramMap), tClass, commonCallback);
        } else {
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).get(url, AppManager.header().getHeaderMap(null, url)), tClass, commonCallback);
        }
    }

    /**
     * post 请求
     *
     * @param TAG            页面标记
     * @param url            请求url
     * @param requestCode    请求码
     * @param paramMap       请求参数
     * @param tClass         服务器返回的数据model
     * @param commonCallback 请求回调
     */
    public void post(@NonNull String TAG, String url, int requestCode, Map<String, Object> paramMap, Class tClass, @NonNull Callback.CommonCallback commonCallback) {
        token();
        if (paramMap != null && paramMap.size() > 0) {
//            Map<String, Object> sortMap = getMapSort(paramMap);
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).post(url, AppManager.header().getHeaderMap(paramMap, url), paramMap), tClass, commonCallback);
        } else {
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).post(url, AppManager.header().getHeaderMap(null, url)), tClass, commonCallback);
        }
    }

    /**
     * 上传资料信息，带数组
     *
     * @param TAG
     * @param url
     * @param requestCode
     * @param paramMap
     * @param awards
     * @param experience
     * @param tClass
     * @param commonCallback
     */
    public void postInfo(@NonNull String TAG, String url, int requestCode, Map<String, Object> paramMap, String[] awards, String[] experience, Class tClass, @NonNull Callback.CommonCallback commonCallback) {
        token();
        if (paramMap != null && paramMap.size() > 0) {
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).postInfo(url, AppManager.header().getHeaderMap(paramMap, url), paramMap, awards, experience), tClass, commonCallback);
        } else {
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).postInfo(url, AppManager.header().getHeaderMap(null, url), null, awards, experience), tClass, commonCallback);
        }
    }


    /**
     * 上传json （未调接口）
     *
     * @param TAG            页面标记
     * @param url            请求url
     * @param requestCode    请求码
     * @param json           请求参数Stirng类型json
     * @param tClass         服务器返回的数据model
     * @param commonCallback
     */
    public void json(@NonNull String TAG, String url, int requestCode, String json, Class tClass, @NonNull Callback.CommonCallback commonCallback) {
        token();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).postJson(url, AppManager.header().getHeaderMap(null, url), body), tClass, commonCallback);
    }

    /**
     * 上传文件
     *
     * @param TAG            页面标记
     * @param url            请求url
     * @param requestCode    请求码
     * @param paramMap       请求参数 可为空
     * @param filePath      上传图片路径集合
     * @param tClass         服务器返回数据model
     * @param commonCallback 请求回调
     */
    public void upload(@NonNull String TAG, String url, int requestCode, Map<String, Object> paramMap, String filePath, Class tClass, @NonNull Callback.CommonCallback commonCallback) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        if (paramMap != null && paramMap.size() > 0) {
//            Map<String, Object> sortMap = getMapSort(paramMap);
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).upload(url, AppManager.header().getHeaderMap(paramMap, url), part, paramMap), tClass, commonCallback);
        } else {
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).upload(url, AppManager.header().getHeaderMap(null, url), part), tClass, commonCallback);
        }

    }

    /**
     * 上传文件
     *
     * @param TAG            页面标记
     * @param url            请求url
     * @param requestCode    请求码
     * @param paramMap       请求参数 可为空
     * @param filePaths      上传图片路径集合
     * @param tClass         服务器返回数据model
     * @param commonCallback 请求回调
     */
    public void upload(@NonNull String TAG, String url, int requestCode, Map<String, Object> paramMap, List<String> filePaths, Class tClass, @NonNull Callback.CommonCallback commonCallback) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (!file.exists()) {
                continue;
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("fileArray", file.getName(), requestFile);
            parts.add(part);
        }
        if (paramMap != null && paramMap.size() > 0) {
//            Map<String, Object> sortMap = getMapSort(paramMap);
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).upload(url, AppManager.header().getHeaderMap(paramMap, url), parts, paramMap), tClass, commonCallback);
        } else {
            mHttpRequest.request(TAG, requestCode, HttpRequest.getInstance().create(ApiServes.class).upload(url, AppManager.header().getHeaderMap(null, url), parts), tClass, commonCallback);
        }

    }

    /**
     * 刷新token方法
     */
    private void refreshToken() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("refreshToken", SettingHelper.getString(AppManager.app(), SettingHelper.SettingField.ACCESS_TOKEN_REFRESH, ""));
//        Map<String, Object> sortMap = getMapSort(paramMap);
        mHttpRequest.request("token", 1, HttpRequest.getInstance().create(ApiServes.class).post("/v1/user/refresh/token",
                AppManager.header().getHeaderMap(paramMap, "/v1/user/refresh/token"), paramMap), TokenInfo.class,
                new Callback.CommonCallback<TokenInfo>() {
                    @Override
                    public void onSuccess(int requestCode, TokenInfo result) {
                        tokenTime = result.getValidityPeriod() * 1000 + TimeUtils.getCurTimeMills();
                        SettingHelper.setString(AppManager.app(), SettingHelper.SettingField.ACCESS_TOKEN, result.getToken());
                        SettingHelper.setString(AppManager.app(), SettingHelper.SettingField.ACCESS_TOKEN_REFRESH, result.getRefreshToken());
                        SettingHelper.setLong(AppManager.app(), SettingHelper.SettingField.ACCESS_TOKEN_TIME, tokenTime);
                    }

                    @Override
                    public void onError(int requestCode, int errorCode, String errorMsg) {
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    /**
     * 取消页面指定请求码请求
     *
     * @param TAG
     * @param code
     */
    public void cancelCall(String TAG, Integer code) {
        mHttpRequest.cancelCall(TAG, code);
    }

    /**
     * 取消页面所有请求
     *
     * @param TAG
     */
    public void cancelCall(String TAG) {
        mHttpRequest.cancelTagCall(TAG);
    }


}
