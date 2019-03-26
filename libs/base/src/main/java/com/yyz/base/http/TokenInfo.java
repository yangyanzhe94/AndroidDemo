package com.yyz.base.http;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TokenInfo {
    private String token;//token
    private String refreshToken; //用于刷新token的key
    private int validityPeriod;//token有效期(秒)

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
