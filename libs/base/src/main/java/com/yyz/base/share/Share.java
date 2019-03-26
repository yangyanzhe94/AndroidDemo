package com.yyz.base.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.yyz.base.utils.StringUtils;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/18.
 */

public class Share {

    /**
     *  分享微信
     *
     *  share:分享微信好友（1）、朋友圈（2）\收藏(3)
     *
     WXEntryActivity  onResp 得到回调

     Activity.onActivityResult()调用
     if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
     Tencent.onActivityResultData(requestCode, resultCode, data,iUiListener);
     }
     *
     * **/
    /**
     * 微信分享 文本
     *
     * @param act       上线文
     * @param shareText 分享文本
     * @param share     分享渠道
     */
//    public static void weChatText(@NonNull Activity act, @NonNull String shareText, @NonNull int share) {
//        //初始化一个WXTextObject对象，填写分享内容
//        WXTextObject wxTextObject = new WXTextObject();
//        wxTextObject.text = shareText;
//
//        //用WXTextObject对象初始化WXMediaMessage
//        WXMediaMessage wxMediaMessage = new WXMediaMessage();
//        wxMediaMessage.mediaObject = wxTextObject;
//        wxMediaMessage.description = shareText;
//        //调用分享
//        shareWeChat(act, wxMediaMessage, "text", share);
//    }
//
//    /**
//     * 微信分享 图片
//     *
//     * @param act              上下文
//     * @param imagePath        图片url
//     * @param shareTitle       标题
//     * @param shareDescription 描述
//     * @param share            分享渠道
//     */
//    // TODO 缩略图问题待解决
//    public static void weChatImage(@NonNull Activity act, @NonNull String imagePath, @NonNull String shareTitle, String shareDescription, int share) {
//        WXImageObject wxImageObject = new WXImageObject();
//        wxImageObject.imagePath = imagePath;
//
//        WXMediaMessage wxMediaMessage = new WXMediaMessage();
//        wxMediaMessage.mediaObject = wxImageObject;
//        wxMediaMessage.title = shareTitle;
//        if (shareDescription != null)
//            wxMediaMessage.description = shareDescription;
//        wxMediaMessage.thumbData = new byte[]{};  //缩略图
//        //调用分享
//        shareWeChat(act, wxMediaMessage, "img", share);
//    }
//
//    /**
//     * 微信分享  音乐
//     *
//     * @param act              上下文
//     * @param shareMusic       分享音乐url
//     * @param shareTitle       分享标题
//     * @param shareDescription 分享描述
//     * @param share            渠道
//     */
//    //TODO 缩略图问题
//    public static void weChatMusic(@NonNull Activity act, @NonNull String shareMusic, @NonNull String shareTitle, String shareDescription, int share) {
//        WXMusicObject wxMusicObject = new WXMusicObject();
//        wxMusicObject.musicUrl = shareMusic;
//
//        WXMediaMessage wxMediaMessage = new WXMediaMessage();
//        wxMediaMessage.mediaObject = wxMusicObject;
//        wxMediaMessage.title = shareTitle;
//        if (shareDescription != null)
//            wxMediaMessage.description = shareDescription;
//        wxMediaMessage.thumbData = new byte[]{};  //缩略图
//
//        //调用分享
//        shareWeChat(act, wxMediaMessage, "music", share);
//    }
//
//    /**
//     * 微信分享 视频
//     *
//     * @param act              上下文
//     * @param shareVideo       分享视频url
//     * @param shareTitle       标题
//     * @param shareDescription 描述
//     * @param share            渠道
//     */
//    public static void weChatVideo(@NonNull Activity act, @NonNull String shareVideo, @NonNull String shareTitle, String shareDescription, int share) {
//        WXVideoObject wxMusicObject = new WXVideoObject();
//        wxMusicObject.videoUrl = shareVideo;
//
//        WXMediaMessage wxMediaMessage = new WXMediaMessage();
//        wxMediaMessage.mediaObject = wxMusicObject;
//        wxMediaMessage.title = shareTitle;
//        if (shareDescription != null)
//            wxMediaMessage.description = shareDescription;
//        wxMediaMessage.thumbData = new byte[]{};  //缩略图
//
//        //调用分享
//        shareWeChat(act, wxMediaMessage, "video", share);
//    }
//
//    /**
//     * 微信分享 h5
//     *
//     * @param act              上下文
//     * @param shareWeb         h5 url
//     * @param shareTitle       标题
//     * @param shareDescription 描述
//     * @param share            渠道
//     */
//    public static void weChatWeb(@NonNull Activity act, @NonNull String shareWeb, @NonNull String shareTitle, String shareDescription, String imgUrl, int share) {
//        WXWebpageObject wxWebpageObject = new WXWebpageObject();
//        wxWebpageObject.webpageUrl = shareWeb;
//
//        WXMediaMessage wxMediaMessage = new WXMediaMessage();
//        wxMediaMessage.mediaObject = wxWebpageObject;
//        wxMediaMessage.title = shareTitle;
//        if (shareDescription != null)
//            wxMediaMessage.description = shareDescription;
//        if(imgUrl != null){
//            wxMediaMessage.thumbData = getImageFromNetByUrl(imgUrl);
//        }
////        wxMediaMessage.thumbData = new byte[]{};  //缩略图
//        //调用分享
//        shareWeChat(act, wxMediaMessage, "webpage", share);
//    }
//
//    private static void shareWeChat(Activity act, WXMediaMessage wxMediaMessage, String shareType, int share) {
//        //构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = (shareType == null) ? String.valueOf(System.currentTimeMillis()) : shareType + System.currentTimeMillis();
//        req.message = wxMediaMessage;
//        if (share == 1) {
//            req.scene = SendMessageToWX.Req.WXSceneSession;  //分享会话
//        } else if (share == 2) {
//            req.scene = SendMessageToWX.Req.WXSceneTimeline;  //分享朋友圈
//        } else if (share == 3) {
//            req.scene = SendMessageToWX.Req.WXSceneFavorite;  //收藏
//        } else {
//            req.scene = SendMessageToWX.Req.WXSceneSession;  //分享会话
//        }
//        IWXAPI msgApi = WXAPIFactory.createWXAPI(act, "");
//        msgApi.sendReq(req);
//    }


    /****
     QQ 分享
     if (requestCode == Constants.REQUEST_QQ_SHARE){
     Tencent.onActivityResultData(requestCode, resultCode, data,iUiListener);
     }
     空间：params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
     好友：params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);  或者并设置。
     *****/
    public static void qqZonePicText(@NonNull Activity act, @NonNull String shareTitle, String shareSummary, @NonNull String shareWeb, String shareImage, IUiListener iUiListener) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareTitle);//必填 分享的标题，最多200个字符。
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareSummary);//选填 分享的摘要，最多600字符。
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareWeb);//必填 需要跳转的链接，URL字符串。
        ArrayList<String> arrs = new ArrayList<>();
        if(StringUtils.isNotEmpty(shareImage)){
            arrs.add(shareImage);
        }
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrs);//选填 分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）。
        //注意:QZone接口暂不支持发送多张图片的能力，若传入多张图片，则会自动选入第一张图片作为预览图。多图的能力将会在以后支持。
        shareQQ(act, params, iUiListener);
    }
    /**
     * 分享图文到qq对话
     *
     * @param act          上下文
     * @param shareTitle    标题
     * @param shareSummary 描述
     * @param shareWeb     点击跳转url
     * @param shareImage   分享框的图片logo
     * @param share        分享到  好友 （0）or 空间（1）
     */
    public static void qqPicText(@NonNull Activity act, @NonNull String shareTitle, String shareSummary, @NonNull String shareWeb, String shareImage, int share, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTitle);
        if (shareSummary != null)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareSummary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareWeb);
        if (shareImage != null)
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareImage);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "Daodao");
        if (share == 1)
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        shareQQ(act, params, iUiListener);
    }

    /**
     * 分享图片到qq对话
     *
     * @param act          上下文
     * @param shareImage   分享图片
     * @param shareTile    分享标题
     * @param shareSummary 分享 描述
     * @param share        分享到  好友 （0）or 空间（1）
     */
    public static void qqImage(@NonNull Activity act, @NonNull String shareImage, String shareTile, String shareSummary, int share, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        if (shareTile != null)
            params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
        if (shareSummary != null)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareSummary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareImage);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "Daodao");
        if (share == 1)
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        shareQQ(act, params, iUiListener);
    }

    /**
     * 分享音频到qq对话
     *
     * @param act
     * @param shareAudio
     * @param targetUrl
     * @param shareTile
     * @param shareSummary
     * @param shareImage
     * @param share
     */
    public static void qqAudio(Activity act, @NonNull String shareAudio, @NonNull String targetUrl, @NonNull String shareTile, String shareSummary, String shareImage, int share,  IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareAudio);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
        if (shareSummary != null)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareSummary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        if (shareImage != null)
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareImage);
        if (share == 1)
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        shareQQ(act, params, iUiListener);
    }

    /**
     * 统一分享到QQ
     * @param act
     * @param params
     * @param iUiListener
     */
    private static void shareQQ(Activity act, Bundle params, IUiListener iUiListener) {
        Tencent mTencent = Tencent.createInstance("appId", act);
        if (mTencent != null && !mTencent.isSessionValid()) {
            mTencent.shareToQQ(act, params, iUiListener);
        }
    }

    /**
     * onActivityResult 方法中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void qqResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, getQQListener());
        }
    }

    /**
     * QQ 分享回调监听
     *
     * @return
     */
    private static IUiListener getQQListener() {
        IUiListener iUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
        return iUiListener;
    }


    /**


     新浪分享

     * **/


    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
