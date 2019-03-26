package com.yyz.base.header;

/**
 * Created by Administrator on 2017/5/19.
 */

public class Device {
    //token
    private String authorization;
    //设备Id
    private String deviceId;
    //app版本
    private String appVersion;
    //客服端时间
    private String appTime;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }
}
