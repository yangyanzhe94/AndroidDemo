package com.yyz.androiddemo.custom;

/**
 * SingleClickFloatWindow 位置更新的监听
 */
interface IPositionUpdateListener {
    /**
     *
     * @param paramsX singleClickFloatWindowParams.x
     * @param paramsY singleClickFloatWindowParams.y
     */
    void onPositionUpdate(int paramsX, int paramsY);
}