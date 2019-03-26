package com.yyz.base.app;

/**
 * Created by Administrator on 2017/10/20.
 */

public @interface ErrorCode {
    /**
     * 账户错误
     */
    int accountCode = 100;
    /**
     * 密码错误
     */
    int passwordCode = 101;

    /**
     * 验证码错误
     */
    int verificationCode = 102;

    /**
     * 请求超时
     */
    int ERR_TIME_OUT = 1;
    /**
     * 解析错误
     */
    int ERR_ANALYSIS = 2;

    /**
     * 无数据
     */
    int ERR_EMPTY = 3;

    /**
     * 无数据
     */
    int ERR_ONFAILURE = 4;

    /**
     * 暂未绑定银行卡
     */
    int NOT_BIND_BANK_CARD = 6607;

}
