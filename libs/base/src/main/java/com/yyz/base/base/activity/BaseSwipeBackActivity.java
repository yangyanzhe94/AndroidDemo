package com.yyz.base.base.activity;

import android.os.Bundle;

import com.yyz.base.base.BasePresenter;
import com.yyz.base.base.BaseView;
import com.yyz.base.widget.SwipeBackLayout;

/**
 * base swipe back activity
 * 1、实现向右滑动删除Activity效果;当前页面含有ViewPager 只需要调用SwipeBackLayout的setViewPager()方法即可
 * 2、设置activity的主题为android:theme="@style/CustomTransparentActivity
 * Created by Administrator on 2017/3/3.
 */

public abstract class BaseSwipeBackActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {

    private SwipeBackLayout swipeBackLayout;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach(this, (V) this);
        super.onCreate(savedInstanceState);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.attachToActivity(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }


    protected abstract P initPresenter();
}
