package com.yyz.base.http;

/**
 * 请求回调通用接口
 * Created by Administrator on 2017/7/19.
 */

public interface Callback {

    interface CommonCallback<T> extends Callback {
        /**
         * 请求成功
         *
         * @param requestCode 请求码
         * @param result      数据
         */
        void onSuccess(int requestCode, T result);

        /**
         * 失败
         *
         * @param requestCode 请求码
         * @param errorCode   错误码
         * @param errorMsg    错误信息
         */
        void onError(int requestCode, int errorCode, String errorMsg);

        /**
         * 取消请求
         */
        void onCancel();

    }

    interface ProgressCallback<T> extends Callback {

        /**
         * 开始请求
         */
        void onStarted(String tag);

        /**
         * 下载中
         *
         * @param total   总字节数
         * @param current 每次写入的字节流。
         */
        void onLoading(long total, long current);

        /**
         * 下载完成
         */
        void onCompleted(String tag, int dataId, String savePath, String saveName);

        /**
         * 下载失败
         *
         * @param throwable
         */
        void onError(String tag, Throwable throwable);

        void onCancel(String tag);

    }

    class CancelledException extends RuntimeException {
        public CancelledException(String detailMessage) {
            super(detailMessage);
        }
    }
}
