package com.yyz.base.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.yyz.base.base.BaseInfo;
import com.yyz.base.base.BaseRunnable;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/10/16.
 */

public class Pay {
    private static StringBuilder stringBuilder;
    private static BaseRunnable baseRunnable;
//    private static IWXAPI msgApi;
//    private static PayReq req;

    /**
     * 调用支付
     *
     * @param act      上下文
     * @param payType  支付渠道
     * @param baseInfo 支付参数
     * @return true 调起支付， false 错误
     */
    public static boolean pay(Activity act, int payType, BaseInfo baseInfo) {
        if (act == null) {
            EventBus.getDefault().post(new PayEvent(payType,-1,"支付错误"));
        }
        if (payType == 1) {
            alipay(act, (AlipayInfo) baseInfo);
        } else if (payType == 2) {
//            payWX(act, (WXInfo) baseInfo);
        } else {
            return false;
        }
        return true;
    }


    /**
     * 支付宝支付信息拼装
     *
     * @param alipayInfo
     * @return
     */
    private static void alipay(final Activity act, AlipayInfo alipayInfo) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("partner=");
        stringBuilder.append("\"" + alipayInfo.getPartner() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("seller_id=");
        stringBuilder.append("\"" + alipayInfo.getSellerId() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("out_trade_no=");
        stringBuilder.append("\"" + alipayInfo.getOutTradeNo() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("subject=");
        stringBuilder.append("\"" + alipayInfo.getSubject() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("body=");
        stringBuilder.append("\"" + alipayInfo.getBody() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("total_fee=");
        stringBuilder.append("\"" + alipayInfo.getMoney() + "\"");  //支付金额
        stringBuilder.append("&");
        stringBuilder.append("notify_url=");
        stringBuilder.append("\"" + alipayInfo.getNotifyUrl() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("service=");
        stringBuilder.append("\"" + alipayInfo.getService() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("payment_type=");
        stringBuilder.append("\"" + alipayInfo.getPaymentType() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("_input_charset=");
        stringBuilder.append("\"" + alipayInfo.getInputCharset() + "\"");
        stringBuilder.append("&");
        stringBuilder.append("it_b_pay=");
        stringBuilder.append("\"" + "30m" + "\"");
        stringBuilder.append("&");
        stringBuilder.append("show_url=");
        stringBuilder.append("\"" + "m.alipay.com" + "\"");

        final String payMsg = stringBuilder.toString() + "&sign=\"" + alipayInfo.getSign() + "\"&" + "sign_type=\"" + alipayInfo.getSignType() + "\"";


        baseRunnable = new BaseRunnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(act);
                String alipayMsg = alipay.pay(payMsg, true);
                if (TextUtils.isEmpty(alipayMsg)) {
                    EventBus.getDefault().post(new PayEvent(1,-1,"支付错误"));
                } else {
                    String resultStatus = "";
                    String result = "";
                    String memo;
                    String[] resultParams = alipayMsg.split(";");
                    for (String resultParam : resultParams) {
                        if (resultParam.startsWith("resultStatus")) {
                            resultStatus = gatValue(resultParam, "resultStatus");
                        }
                        if (resultParam.startsWith("result")) {
                            result = gatValue(resultParam, "result");
                        }
                        if (resultParam.startsWith("memo")) {
                            memo = gatValue(resultParam, "memo");
                        }
                    }

                    if (TextUtils.equals(resultStatus, "9000")) {
                        EventBus.getDefault().post(new PayEvent(1,1,"支付成功"));
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        //支付中
                        EventBus.getDefault().post(new PayEvent(1,2,"支付中"));
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        //取消支付
                        EventBus.getDefault().post(new PayEvent(1,-1,1,"取消支付"));

                    } else {
                        //错误
                        EventBus.getDefault().post(new PayEvent(1,-1,"取消支付"));
                    }

                }
            }
        };
        baseRunnable.run();
    }

    private static String gatValue(String content, String key) {
        String prefix = key + "={";
        return content.substring(content.indexOf(prefix) + prefix.length(),
                content.lastIndexOf("}"));
    }

//    /**
//     * 微信支付
//     *
//     * @param context 上下文
//     * @param wxInfo  支付信息
//     *
//     *    wxapi.WXPayEntryActivity  implements IWXAPIEventHandler  onResp回调监听
//     */
//    private static void payWX(Context context, WXInfo wxInfo) {
//        if (msgApi == null) {
//            msgApi = WXAPIFactory.createWXAPI(context, "key");
//        }
//        if (msgApi.isWXAppInstalled() && (msgApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT)) {
//            req = new PayReq();
//            req.appId = wxInfo.getAppId();
//            req.partnerId = wxInfo.getPartnerId();
//            req.prepayId = wxInfo.getPrepayId();
//            req.nonceStr = wxInfo.getNonceStr();
//            req.timeStamp = wxInfo.getTimesTamp();
//            req.packageValue = wxInfo.getPackages();
//            req.sign = wxInfo.getSign();
//            if (!msgApi.sendReq(req)) {
//                EventBus.getDefault().post(new PayEvent(1,-1,"支付错误"));
//            }
//        } else {
//            EventBus.getDefault().post(new PayEvent(1,-1,2,"您尚未安装微信或者微信版本过低，建议去下载或升级至最新版后重试"));
//        }
//    }

}
