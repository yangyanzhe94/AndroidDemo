package com.yyz.base.recycler.refresh.interfaces;

import com.yyz.base.recycler.refresh.api.RefreshFooter;
import com.yyz.base.recycler.refresh.api.RefreshHeader;

/**
 * Created by Administrator on 2017/8/21.
 */

public interface OnMultiPurposeListener extends OnRefreshLoadMoreListener, OnStateChangedListener {
    /**
     * 下拉调用
     *
     * @param header
     * @param percent
     * @param offset
     * @param headerHeight
     * @param extendHeight
     */
    void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight,
        int extendHeight);

    /**
     * 下拉释放时 调用
     *
     * @param header
     * @param percent
     * @param offset
     * @param headerHeight
     * @param extendHeight
     */
    void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight,
        int extendHeight);

    /**
     * 下拉动画开始执行 调用
     *
     * @param header
     * @param headerHeight
     * @param extendHeight
     */
    void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight);

    /**
     * 下拉刷新完成 调用
     *
     * @param header
     * @param success
     */
    void onHeaderFinish(RefreshHeader header, boolean success);

    /**
     * 加载更多，上拉时候 调用
     *
     * @param footer
     * @param percent
     * @param offset
     * @param footerHeight
     * @param extendHeight
     */
    void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight,
        int extendHeight);

    /**
     * 加载更多，释放 调用
     *
     * @param footer
     * @param percent
     * @param offset
     * @param footerHeight
     * @param extendHeight
     */
    void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight,
        int extendHeight);

    /**
     * 加载更多执行动画 调用
     *
     * @param footer
     * @param footerHeight
     * @param extendHeight
     */
    void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight);

    /**
     * 加载更多完成 调用
     *
     * @param footer
     * @param success
     */
    void onFooterFinish(RefreshFooter footer, boolean success);
}
