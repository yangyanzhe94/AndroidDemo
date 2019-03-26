package com.yyz.base.http.factory;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * fastJson response body converter
 * Created by Administrator on 2017/3/10.
 */

public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /**
     *  server responseBody convert model
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        try {
            return JSON.parseObject(tempStr, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}