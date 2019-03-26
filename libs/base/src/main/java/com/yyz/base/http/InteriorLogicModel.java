package com.yyz.base.http;

public class InteriorLogicModel<T> {
    private String tag;
    private String msg;
    private T t;

    public InteriorLogicModel(String tag) {
        this.tag = tag;
    }



    public InteriorLogicModel(String tag, String msg) {
        this.msg = msg;
        this.tag = tag;
    }

    public InteriorLogicModel(String tag, String msg, T t) {
        this.msg = msg;
        this.t = t;
        this.tag = tag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
