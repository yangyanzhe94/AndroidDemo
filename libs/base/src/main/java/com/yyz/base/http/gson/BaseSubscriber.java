//package com.ranran.base.http.gson;
//
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//import com.ranran.base.R;
//import java.io.IOException;
//import org.reactivestreams.Subscriber;
//
///**
// * Created by Administrator on 1/25/2018.
// */
//
////BaseSubscriber.java
//public class BaseSubscriber<T> extends Subscriber<T> {
//    protected Context mContext;
//
//    public BaseSubscriber(Context context) {
//        this.mContext = context;
//    }
//
//    @Override
//    public void onCompleted() {
//
//    }
//
//    @Override
//    public void onError(final Throwable e) {
//        Log.w("Subscriber onError"    , e);
//        if (e instanceof HttpException) {
//            // We had non-2XX http error
//            Toast.makeText(mContext, mContext.getString(R.string.server_internal_error), Toast.LENGTH_SHORT).show();
//        } else if (e instanceof IOException) {
//            // A network or conversion error happened
//            Toast.makeText(mContext, mContext.getString(R.string.cannot_connected_server), Toast.LENGTH_SHORT).show();
//        } else if (e instanceof ApiException) {
//            ApiException exception = (ApiException) e;
//            if (exception.isTokenExpried()) {
//                //处理token失效对应的逻辑
//            } else {
//                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void onNext(T t) {
//
//    }
//
//}
//
//
