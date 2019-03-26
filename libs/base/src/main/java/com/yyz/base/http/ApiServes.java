package com.yyz.base.http;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/7/19.
 */

public interface ApiServes {

    /**
     * get 无参请求
     *
     * @param url
     * @param headerMap
     * @return
     */
    @GET()
    Call<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headerMap);

    /**
     * get 带参数请求
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     */
    @GET
    Call<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headerMap,
        @QueryMap Map<String, Object> paramMap);


    /**
     * post 请求无参数
     *
     * @param url
     * @param headerMap
     * @return
     */
    @POST
    Call<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headerMap);


    /**
     * post 表单上传，带参数
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headerMap,
        @FieldMap Map<String, Object> paramMap);

    /**
     * post 请求，资料
     * @param url
     * @param headerMap
     * @param paramMap
     * @param awards
     * @param experience
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> postInfo(@Url String url, @HeaderMap Map<String, String> headerMap,
        @FieldMap Map<String, Object> paramMap, @Field("awards[]") String[] awards,
        @Field("experience[]") String[] experience);


    /**
     * 退款申请
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @param imgUrl
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> postRefund(@Url String url, @HeaderMap Map<String, String> headerMap,
        @FieldMap Map<String, Object> paramMap, @Field("imgUrl[]") String[] imgUrl);


    /**
     * post 表单上传，带参数
     *
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> postForDesignerList(@Url String url,
        @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, Object> paramMap,
        @Field("categoryId[]") Integer... categoryIds);

    /**
     * 上传json
     *
     * @param url
     * @param headMap
     * @param requestBody
     * @return
     */
    @POST
    Call<ResponseBody> postJson(@Url String url, @HeaderMap Map<String, String> headMap,
        @Body RequestBody requestBody);

    /**
     * 上传多个图片
     *
     * @param url
     * @param headMap
     * @param multipartBodies
     * @return
     */
    @Multipart
    @POST()
    Call<ResponseBody> upload(@Url String url, @HeaderMap Map<String, String> headMap,
        @Part List<MultipartBody.Part> multipartBodies);

    /**
     * 上传图片、参数
     *
     * @param url
     * @param headMap
     * @param multipartBodies
     * @param paramMap
     * @return
     */
    @Multipart
    @POST()
    Call<ResponseBody> upload(@Url String url, @HeaderMap Map<String, String> headMap,
        @Part List<MultipartBody.Part> multipartBodies, @FieldMap Map<String, Object> paramMap);



    /**
     * 上传单张图片图片、参数
     *
     * @param url
     * @param headMap
     * @param multyipartBody
     * @param paramMap
     * @return
     */
    @Multipart
    @POST()
    Call<ResponseBody> upload(@Url String url, @HeaderMap Map<String, String> headMap,
        @Part MultipartBody.Part multyipartBody, @FieldMap Map<String, Object> paramMap);

    /**
     * 上传多个图片
     *
     * @param url
     * @param headMap
     * @param multipartBodies
     * @return
     */
    @Multipart
    @POST()
    Call<ResponseBody> upload(@Url String url, @HeaderMap Map<String, String> headMap,
        @Part MultipartBody.Part multipartBodies);

    /**
     * 下载文件
     *
     * @param url
     * @param headMap
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @HeaderMap Map<String, String> headMap);


    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);


    /**
     * 下载文件 多线程下载.
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @Header("Range") String range);


    /**
     * 下载文件，获取数据长度
     *
     * @param url
     * @param range
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> getHttpHeader(@Url String url, @Header("Range") String range);

}
