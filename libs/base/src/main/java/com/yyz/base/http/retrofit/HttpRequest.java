package com.yyz.base.http.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.yyz.base.R;
import com.yyz.base.app.ErrorCode;
import com.yyz.base.base.BaseConstantValue;
import com.yyz.base.http.ApiServes;
import com.yyz.base.http.InteriorLogicModel;
import com.yyz.base.http.config.CookieManager;
import com.yyz.base.utils.LogUtils;
import com.yyz.base.utils.SettingHelper;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/10.
 */

public class HttpRequest {

    private HttpRequest() {

    }

    private final static HttpRequest mInstance = new HttpRequest();

    public final static HttpRequest getInstance() {
        return mInstance;
    }

    private Map<String, Map<Integer, Call>> mRequestMap = new ConcurrentHashMap<>();

    public Context mContext;

    private Retrofit mRetrofit;

    private OkHttpClient mOkHttpClient;

    private OkHttpClient.Builder builder;

    private ApiServes mApiServes;

    private Cache cache;

    /**
     * 初始化Retrofit
     */
    public HttpRequest init(@NonNull Context context, String baseURL, boolean isLogRequest) {
        this.mContext = context;
        synchronized (HttpRequest.this) {
            cache =
                new Cache(new File(context.getExternalCacheDir(), "http_cache"), 1024 * 1024 * 100);
            builder = new OkHttpClient.Builder().cache(cache)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 10, TimeUnit.SECONDS))
                .cookieJar(new CookieManager());

            if (isLogRequest) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            //            if (debug) {
            //                builder.addInterceptor(new Interceptor() {
            //                    @Override
            //                    public okhttp3.Response intercept(Chain chain) throws IOException {
            //                        okhttp3.Response response = chain.proceed(chain.request());
            //                        ResponseBody responseBody = response.body();
            //                        Log.i("i",responseBody != null ? "--------------responseBody-----------------" + responseBody.string() : "空");
            //                        return response;
            //                    }
            //                });
            //            }
            mOkHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                //.addConverterFactory(GsonConverterFactory.create())
                //                    .addConverterFactory(FastJsonConverterFactory.create())  //返回 FastJson
                //                    .addConverterFactory(ScalarsConverterFactory.create())  //返回string
                .addConverterFactory(GsonConverterFactory.create())     //gson
                .baseUrl(baseURL)//主机地址
                .client(mOkHttpClient).build();
            mApiServes = mRetrofit.create(ApiServes.class);
            LogUtils.i("---------HttpRequest请求Url是：" + baseURL);
        }
        return this;
    }

    public ApiServes getApiServes() {
        return mApiServes;
    }

    public <T> T create(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public void clearCookie() {
        ((CookieManager) mOkHttpClient.cookieJar()).clearCookie();
    }

    /**
     * 异步请求
     *
     * @param TAG 请求tag  页面标识  （tag可以关闭页面所有请求）
     * @param requestCode 请求code  页面请求标识  （关闭页面某个请求）
     * @param requestCall 请求实例
     * @param callback 请求回调
     */
    public <T> void request(@NonNull final String TAG, final int requestCode,
        final Call<ResponseBody> requestCall, final Class tClass,
        final com.yyz.base.http.Callback.CommonCallback<T> callback) {
        if (callback == null) {
            return;
        }
        Call<ResponseBody> call;
        if (requestCall.isExecuted()) {
            call = requestCall.clone();
        } else {
            call = requestCall;
        }
        addCall(TAG, requestCode, call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                cancelCall(TAG, requestCode);
                if (response.isSuccessful()) {
                    ResponseBody result = response.body();
                    if (null == result) {
                        callback.onError(requestCode, ErrorCode.ERR_TIME_OUT,
                            mContext.getString(R.string.errDefault));
                        return;
                    }
                    T resultType = null;
                    try {
                        String str = result.string();
                        LogUtils.i("---------" + str);
                        resultType = (T) JSON.parseObject(str, tClass);
                        callback.onSuccess(requestCode, resultType);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onError(requestCode, ErrorCode.ERR_ANALYSIS,
                            mContext.getString(R.string.errAnalysis));
                    }
                } else {
                    try {
                        String errMsg = response.errorBody().string();
                        LogUtils.i(
                            "---------errMsg:" + errMsg + "---------errCode是：" + response.raw()
                                .code() + ";   ---------errorMsg是：" + response.raw().message());

                        JSONObject jsonObject;
                        jsonObject = new JSONObject(errMsg);
                        int errCode = jsonObject.getInt("errorCode");
                        String errorMessage = jsonObject.getString("errorMessage");
                        errCode(requestCode, errCode, errorMessage, callback);
                    } catch (Exception e) {

                        e.printStackTrace();
                        callback.onError(requestCode, ErrorCode.ERR_TIME_OUT,
                            mContext.getString(R.string.errDefault));
                    }
                }
            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                cancelCall(TAG, requestCode);
                callback.onError(requestCode, ErrorCode.ERR_TIME_OUT,
                    mContext.getString(R.string.errDefault));
                LogUtils.i("-----errCode是：固定值1" + ";   errorMsg是：" + t.getMessage());
            }
        });
    }

    /**
     * add call map
     */
    private void addCall(String TAG, Integer code, Call call) {
        if (TAG == null) {
            return;
        }
        if (mRequestMap.get(TAG) == null) {
            Map<Integer, Call> map = new ConcurrentHashMap<>();
            map.put(code, call);
            mRequestMap.put(TAG, map);
        } else {
            mRequestMap.get(TAG).put(code, call);
        }
    }

    /**
     * cancel call
     *
     * @param TAG 页面标记
     * @param code 请求code
     */
    public boolean cancelCall(String TAG, Integer code) {
        if (TAG == null) {
            return false;
        }
        Map<Integer, Call> map = mRequestMap.get(TAG);
        if (map == null) {
            return false;
        }
        if (code == null) {
            //取消整个context请求
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Integer key = (Integer) iterator.next();

                Call call = map.get(key);
                if (call == null) {
                    continue;
                }
                call.cancel();
            }
            mRequestMap.remove(TAG);
            return false;
        } else {
            //取消一个请求
            if (map.containsKey(code)) {
                Call call = map.get(code);
                if (call != null) {
                    call.cancel();
                }
                map.remove(code);
            }
            if (map.size() == 0) {
                mRequestMap.remove(TAG);
                return false;
            }
        }
        return true;
    }

    /**
     * 取消整个tag请求，关闭页面时调用
     */
    public void cancelTagCall(@NonNull String TAG) {
        cancelCall(TAG, null);
    }

    /**
     * 处理需要特殊处理的
     */
    private void errCode(int requestCode, int errCode, String errorMessage,
        com.yyz.base.http.Callback.CommonCallback callback) {
        if (errCode == 1009
            || errCode == 1008
            || errCode == 1007
            || errCode == 1006
            || errCode == 1005
            || errCode == 1004) {
            SettingHelper.setBoolean(mContext, SettingHelper.SettingField.LOGIN_SUCCESS, false);
            EventBus.getDefault()
                .post(new InteriorLogicModel(BaseConstantValue.EVENT_BUS_LOGIN_ANOTHER_DEVICE));
        } else {
            callback.onError(requestCode, errCode, errorMessage);
        }
    }
}
