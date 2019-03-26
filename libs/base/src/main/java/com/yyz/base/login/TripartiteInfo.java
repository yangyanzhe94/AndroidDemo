package com.yyz.base.login;

/**
 * Created by Administrator on 2017/10/17.
 */

public class TripartiteInfo {
    private int loginType;  //登录渠道
    private int loginStatus;  //登录状态  -1登录失败 1登录成功
    private String jsonObject; //返回登录信息
    private String msg;      //提示文案

    public TripartiteInfo() {
    }

    public TripartiteInfo(int loginType, int loginStatus, String msg) {
       this(loginType,loginStatus,null,msg);
    }


    public TripartiteInfo(int loginType, int loginStatus, String msg, String jsonObject) {
        this.loginType = loginType;
        this.loginStatus = loginStatus;
        this.jsonObject = jsonObject;
        this.msg = msg;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
