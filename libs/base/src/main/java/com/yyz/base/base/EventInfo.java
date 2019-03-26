package com.yyz.base.base;

/**
 * Created by Administrator on 2017/6/29.
 */

public class EventInfo<T extends BaseInfo> {
    private T t;
    private int tagCode;

    public EventInfo(){}

    public EventInfo(T t, int tagCode){
        this.t = t;
        this.tagCode = tagCode;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public int getTagCode() {
        return tagCode;
    }

    public void setTagCode(int tagCode) {
        this.tagCode = tagCode;
    }
}
