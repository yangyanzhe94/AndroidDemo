package com.yyz.base.recycler.refresh.footer;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyz.base.R;
import com.yyz.base.recycler.refresh.api.RefreshFooter;
import com.yyz.base.recycler.refresh.api.RefreshKernel;
import com.yyz.base.recycler.refresh.api.RefreshLayoutImpl;
import com.yyz.base.recycler.refresh.constant.RefreshState;
import com.yyz.base.recycler.refresh.constant.SpinnerStyle;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.getSize;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Administrator on 2017/12/7.
 */

public class RotateLoadMoreFooter extends ViewGroup implements RefreshFooter {
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private View footerView;

    //<editor-fold desc="ViewGroup">
    public RotateLoadMoreFooter(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public RotateLoadMoreFooter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public RotateLoadMoreFooter(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        footerView = LayoutInflater.from(context).inflate(R.layout.base_footview_designer_detail,null);
        addView(footerView, MATCH_PARENT, WRAP_CONTENT);
        setMinimumHeight(100);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = makeMeasureSpec(getSize(widthMeasureSpec), AT_MOST);
        int heightSpec = makeMeasureSpec(getSize(heightMeasureSpec), AT_MOST);
        footerView.measure(widthSpec, heightSpec);
        setMeasuredDimension(
                resolveSize(footerView.getMeasuredWidth(), widthMeasureSpec),
                resolveSize(footerView.getMeasuredHeight(), heightMeasureSpec)
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int pwidth = getMeasuredWidth();
        int pheight = getMeasuredHeight();
        int cwidth = footerView.getMeasuredWidth();
        int cheight = footerView.getMeasuredHeight();
        int left = pwidth / 2 - cwidth / 2;
        int top = pheight / 2 - cheight / 2;
        footerView.layout(left, top, left + cwidth, top + cheight);
    }
    //</editor-fold>

    //<editor-fold desc="RefreshFooter">
    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {
    }

    @Override
    public void onPullReleasing(float percent, int offset, int footerHeight, int extendHeight) {
    }

//    @Override
//    public void onLoadMoreReleased(RefreshLayoutImpl layout, int footerHeight, int extendHeight) {
//
//    }



    @Override
    public void onStartAnimator(RefreshLayoutImpl layout, int height, int extendHeight) {
//        mBallPulseView.startAnim();
    }

    @Override
    public void onStateChanged(RefreshLayoutImpl refreshLayout, RefreshState oldState, RefreshState newState) {
    }

    @Override
    public int onFinish(RefreshLayoutImpl layout, boolean success) {
//        mBallPulseView.stopAnim();
        return 0;
    }

    @Override
    public boolean setLoadMoreFinished(boolean finished) {
        return false;
    }

    @Override@Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
//        if (colors.length > 1) {
//            mBallPulseView.setNormalColor(colors[1]);
//            mBallPulseView.setAnimatingColor(colors[0]);
//        } else if (colors.length > 0) {
//            mBallPulseView.setNormalColor(ColorUtils.compositeColors(0x99ffffff,colors[0]));
//            mBallPulseView.setAnimatingColor(colors[0]);
//        }
    }
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }
    //</editor-fold>

    //<editor-fold desc="API">
    public RotateLoadMoreFooter setSpinnerStyle(SpinnerStyle mSpinnerStyle) {
        this.mSpinnerStyle = mSpinnerStyle;
        return this;
    }

    public RotateLoadMoreFooter setIndicatorColor(@ColorInt int color) {
//        mBallPulseView.setIndicatorColor(color);
        return this;
    }

    public RotateLoadMoreFooter setNormalColor(@ColorInt int color) {
//        mBallPulseView.setNormalColor(color);
        return this;
    }

    public RotateLoadMoreFooter setAnimatingColor(@ColorInt int color) {
//        mBallPulseView.setAnimatingColor(color);
        return this;
    }
}
