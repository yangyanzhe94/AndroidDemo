package com.yyz.base.recycler.refresh.api;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.yyz.base.recycler.refresh.constant.RefreshState;
import com.yyz.base.recycler.refresh.interfaces.OnLoadMoreListener;
import com.yyz.base.recycler.refresh.interfaces.OnMultiPurposeListener;
import com.yyz.base.recycler.refresh.interfaces.OnRefreshListener;
import com.yyz.base.recycler.refresh.interfaces.OnRefreshLoadMoreListener;

/**
 * Created by Administrator on 2017/8/21.
 */

public interface RefreshLayoutImpl {
    RefreshLayoutImpl setFooterHeight(float dp);

    RefreshLayoutImpl setFooterHeightPx(int px);

    RefreshLayoutImpl setHeaderHeight(float dp);

    RefreshLayoutImpl setHeaderHeightPx(int px);

    /**
     * 显示拖动高度/真实拖动高度（默认0.5，阻尼效果）
     */
    RefreshLayoutImpl setDragRate(float rate);

    /**
     * 设置下拉最大高度和Header高度的比率（将会影响可以下拉的最大高度）
     */
    RefreshLayoutImpl setHeaderMaxDragRate(float rate);

    /**
     * 设置上啦最大高度和Footer高度的比率（将会影响可以上啦的最大高度）
     */
    RefreshLayoutImpl setFooterMaxDragRate(float rate);

    /**
     * 设置回弹显示插值器
     */
    RefreshLayoutImpl setReboundInterpolator(Interpolator interpolator);

    /**
     * 设置回弹动画时长
     */
    RefreshLayoutImpl setReboundDuration(int duration);

    /**
     * 设置是否启用上啦加载更多（默认启用）
     */
    RefreshLayoutImpl setEnableLoadMore(boolean enable);

    /**
     * 是否启用下拉刷新（默认启用）
     */
    RefreshLayoutImpl setEnableRefresh(boolean enable);

    /**
     * 设置是否启在下拉Header的同时下拉内容
     */
    RefreshLayoutImpl setEnableHeaderTranslationContent(boolean enable);

    /**
     * 设置是否启在上拉Footer的同时上拉内容
     */
    RefreshLayoutImpl setEnableFooterTranslationContent(boolean enable);

    /**
     * 设置是否开启在刷新时候禁止操作内容视图
     */
    RefreshLayoutImpl setDisableContentWhenRefresh(boolean disable);

    /**
     * 设置是否开启在加载时候禁止操作内容视图
     */
    RefreshLayoutImpl setDisableContentWhenLoading(boolean disable);

    /**
     * 设置是否监听列表在滚动到底部时触发加载事件（默认true）
     */
    RefreshLayoutImpl setEnableAutoLoadMore(boolean enable);

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    RefreshLayoutImpl setLoadMoreFinished(boolean finished);

    /**
     * 设置指定的Footer
     */
    RefreshLayoutImpl setRefreshFooter(RefreshFooter footer);

    /**
     * 设置指定的Footer
     */
    RefreshLayoutImpl setRefreshFooter(RefreshFooter footer, int width, int height);

    /**
     * 设置指定的Header
     */
    RefreshLayoutImpl setRefreshHeader(RefreshHeader header);

    /**
     * 设置指定的Header
     */
    RefreshLayoutImpl setRefreshHeader(RefreshHeader header, int width, int height);

    /**
     * 设置是否启用越界回弹
     */
    RefreshLayoutImpl setEnableOverScrollBounce(boolean enable);

    /**
     * 设置是否开启纯滚动模式
     */
    RefreshLayoutImpl setEnablePureScrollMode(boolean enable);

    /**
     * 设置是否在加载更多完成之后滚动内容显示新数据
     */
    RefreshLayoutImpl setEnableScrollContentWhenLoaded(boolean enable);

    /**
     * 设置在内容不满一页的时候，是否可以上拉加载更多
     */
    RefreshLayoutImpl setEnableLoadMoreWhenContentNotFull(boolean enable);

    /**
     * 设置是会否启用嵌套滚动功能（默认关闭+智能开启）
     */
    RefreshLayoutImpl setEnableNestedScroll(boolean enabled);

    /**
     * 单独设置刷新监听器
     */
    RefreshLayoutImpl setOnRefreshListener(OnRefreshListener listener);

    /**
     * 单独设置加载监听器
     */
    RefreshLayoutImpl setOnLoadMoreListener(OnLoadMoreListener listener);

    /**
     * 同时设置刷新和加载监听器
     */
    RefreshLayoutImpl setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener);

    /**
     * 设置多功能监听器
     */
    RefreshLayoutImpl setOnMultiPurposeListener(OnMultiPurposeListener listener);

    /**
     * 设置主题颜色
     */
    RefreshLayoutImpl setPrimaryColorsId(@ColorRes int... primaryColorId);

    /**
     * 设置主题颜色
     */
    RefreshLayoutImpl setPrimaryColors(int... colors);

    /**
     * 设置滚动边界判断器
     */
    RefreshLayoutImpl setScrollBoundaryDecider(ScrollBoundaryDecider boundary);

    /**
     * 完成刷新
     */
    RefreshLayoutImpl finishRefresh();

    /**
     * 完成加载
     */
    RefreshLayoutImpl finishLoadMore();

    /**
     * 完成刷新
     */
    RefreshLayoutImpl finishRefresh(int delayed);

    /**
     * 完成加载
     *
     * @param success 数据是否成功刷新 （会影响到上次更新时间的改变）
     */
    RefreshLayoutImpl finishRefresh(boolean success);

    /**
     * 完成刷新
     */
    RefreshLayoutImpl finishRefresh(int delayed, boolean success);

    /**
     * 完成加载
     */
    RefreshLayoutImpl finishLoadMore(int delayed);

    /**
     * 完成加载
     */
    RefreshLayoutImpl finishLoadMore(boolean success);

    /**
     * 完成加载
     */
    RefreshLayoutImpl finishLoadMore(int delayed, boolean success);

    /**
     * 获取当前 Header
     */
    @Nullable
    RefreshHeader getRefreshHeader();

    /**
     * 获取当前 Footer
     */
    @Nullable
    RefreshFooter getRefreshFooter();

    /**
     * 获取当前状态
     */
    RefreshState getState();

    /**
     * 获取实体布局视图
     */
    ViewGroup getLayout();

    /**
     * 是否正在刷新
     */
    boolean isRefreshing();

    /**
     * 是否正在加载
     */
    boolean isLoading();

    /**
     * 自动刷新
     */
    boolean autoRefresh();

    /**
     * 自动刷新
     */
    boolean autoRefresh(int delayed);

    /**
     * 自动刷新
     */
    boolean autoRefresh(int delayed, float dragrate);

    /**
     * 自动加载
     */
    boolean autoLoadMore();

    /**
     * 自动加载
     */
    boolean autoLoadMore(int delayed);

    /**
     * 自动加载
     */
    boolean autoLoadMore(int delayed, float dragrate);

    boolean isEnableRefresh();

    boolean isEnableLoadMore();

    boolean isLoadMoreFinished();

    boolean isEnableAutoLoadMore();

    boolean isEnableOverScrollBounce();

    boolean isEnablePureScrollMode();

    boolean isEnableScrollContentWhenLoaded();
}
