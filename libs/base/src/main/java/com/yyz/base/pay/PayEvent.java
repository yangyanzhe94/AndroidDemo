package com.yyz.base.pay;

/**
 * Created by Administrator on 2017/10/16.
 */

public class PayEvent {
    private int payType;  //1支付宝 2微信
    private int payStatus;  // -1错误 1成功 2支付中
    private int errCode;    // -1 默认错误  1取消支付 2微信版本过低
    private String payMsg;  //错误信息

    public PayEvent(){}

    public PayEvent(int payType, int payStatus, String payMsg) {
        this(payType,payStatus,-1,payMsg);
    }

    public PayEvent(int payType, int payStatus, int errCode, String payMsg) {
        this.payType = payType;
        this.payStatus = payStatus;
        this.errCode = errCode;
        this.payMsg = payMsg;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayMsg() {
        return payMsg;
    }

    public void setPayMsg(String payMsg) {
        this.payMsg = payMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
