package com.yyz.base.statistics;

import android.content.Context;

/**
 * 统计管理器
 * Created by Administrator on 2017/7/25.
 */

public class EventManager {
    private EventManager() {
    }
    private static EventManager mInstance;

    public static EventManager getInstance() {
        if (mInstance == null) {
            mInstance = new EventManager();
        }
        return mInstance;
    }

    /**
     * 第三方数据统计
     * @param context    上下文
     * @param appKey     申请的appKey
     * @param channelId   渠道id
     * @param isActName    是否采用Activity名称统计
     * @return
     */
    public EventManager init(Context context, String appKey, String channelId, boolean isActName){

        return this;
    }

    /**
     * 设置是否使用Activity名称统计
     */
    public void setActNameStatistics(boolean isActName){

    }

    /**
     * 设置是否启用加密
     * @param isEnableEncrypt
     */
    public void setEnableEncrypt(boolean isEnableEncrypt){

    }

    /**
     * 统计应用时长
     *  配合onPause使用
     * @param context
     */
    public void onResume(Context context){

    }

    /**
     * 统计应用时长
     *   配合onResume使用
     * @param context
     */
    public void onPause(Context context){

    }

    /**
     *  统计页面跳转
     *    配合onPageEnd 使用
     *    在Activity生命周期 onResume方法调用，同时在本类中onResume之前调用
     * @param pageName
     */
    public void onPageStart(String pageName){

    }
    /**
     *  统计页面跳转
     *    onPageStart 使用
     *    在Activity生命周期 onPause，同时在本类中 onPause 之前调用
     * @param pageName
     */
    public void onPageEnd(String pageName){

    }

    /**
     * 统计自定义事件
     * @param context
     * @param eventId
     */
    public void onEvent(Context context,String eventId){

    }

    /**
     * 统计账户信息
     * @param Provider  帐号来源
     * @param ID        账户Id
     */
    public void signIn(String Provider, String ID){

    }
    /**
     * 不再统计账户信息
     */
    public void signOff(){

    }

    /**
     * 杀死进程时候调用，确保数据统计
     * @param context
     */
    public void onKillProcess(Context context){

    }
}
