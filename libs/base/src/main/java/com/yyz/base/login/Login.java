//package com.yyz.base.login;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Build;
//
//import com.tencent.connect.common.Constants;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
//
//import org.greenrobot.eventbus.EventBus;
//
///**
// * Created by Administrator on 2017/10/17.
// */
//
//public class Login {
//    /**
//     * WeChat login
//     *
//     * @param act
//     * @param state     wxapi.WXEntryActivity  implements IWXAPIEventHandler  onResp回调
//     */
//    public static void weChat(Activity act, String state) {
//        final IWXAPI msgApi = WXAPIFactory.createWXAPI(act, "");
//        if (msgApi.isWXAppInstalled() && (msgApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT)) {
//            SendAuth.Req req = new SendAuth.Req();
//            req.scope = "snsapi_userinfo";
//            req.state = state;
//            if (!msgApi.sendReq(req)) {
//                EventBus.getDefault().post(new TripartiteInfo(1, -1, "微信登录错误"));
//            }
//        } else {
//            EventBus.getDefault().post(new TripartiteInfo(1, -1, "您尚未安装微信或者微信版本过低，建议去下载或升级至最新版后重试"));
//        }
//
//    }
//
//    /**
//     * QQ login
//     *
//     * @param act
//     * @param scope     获取权限 应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
//         <activity
//              android:name="com.tencent.tauth.AuthActivity"
//              android:noHistory="true"
//              android:launchMode="singleTask" >
//              <intent-filter>
//                       <action android:name="android.intent.action.VIEW" />
//                       <category android:name="android.intent.category.DEFAULT" />
//                       <category android:name="android.intent.category.BROWSABLE" />
//                       <data android:scheme="appId" />
//              </intent-filter>
//          </activity>
//
//          <activity android:name="com.tencent.connect.common.AssistActivity"
//                       android:theme="@android:style/Theme.Translucent.NoTitleBar"
//                       android:configChanges="orientation|keyboardHidden|screenSize"
//           />
//
//     调用Activity中，onActivityResult方法调用：Tencent.onActivityResultData(requestCode, resultCode, data,iUiListener);  调用loginQQResult方法即可。
//     */
//    public static void qq(Activity act,String scope) {
//        Tencent mTencent = Tencent.createInstance("appId", act);
//        if (!mTencent.isSessionValid()) {
//            mTencent.login(act, "all", getQQListener());
//        }
//    }
//
//    /**
//     * onActivityResult 方法中调用
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    public static void qqResult(int requestCode,int resultCode,Intent data){
//        if (requestCode == Constants.REQUEST_LOGIN ||
//                requestCode == Constants.REQUEST_APPBAR) {
//            Tencent.onActivityResultData(requestCode, resultCode, data,getQQListener());
//        }
//    }
//
//    /**
//     *  qq 登录回调监听
//     * @return
//     */
//    private static IUiListener getQQListener(){
//        IUiListener iUiListener = new IUiListener() {
//            @Override
//            public void onComplete(Object o) {
//                EventBus.getDefault().post(new TripartiteInfo(2, 1, "登录成功", o.toString()));
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//                EventBus.getDefault().post(new TripartiteInfo(2, -1, uiError.errorMessage));
//            }
//
//            @Override
//            public void onCancel() {
//                EventBus.getDefault().post(new TripartiteInfo(2, -1, "取消登陆"));
//            }
//        };
//
//        return iUiListener;
//
//    }
//
//
//    public void sina(Activity act, int loginType) {
//        WbAuthListener wbAuthListener = new WbAuthListener() {
//            @Override
//            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
//
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//
//            @Override
//            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
//
//            }
//        };
//        //创建微博实例
//        // mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
//        // Constants.REDIRECT_URL, Constants.SCOPE);
//        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
//        AuthInfo mAuthInfo = new AuthInfo(act, "Constants.APP_KEY", "Constants.REDIRECT_URL", "Constants.SCOPE");
//        SsoHandler mSsoHandler = new SsoHandler(act);
//        mSsoHandler.authorize(wbAuthListener);
//    }
//
//    /**
//     *  Activity.onActivityResult() 调用
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    public void sinaResult(int requestCode, int resultCode, Intent data){
////        if (mSsoHandler != null){
////             mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
////        }
//    }
//
//}
