package com.yyz.base.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yyz.base.base.BasePresenter;

import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/29.
 */

public abstract class BaseMvpFragment<V, P extends BasePresenter<V>> extends BaseFragment {
    protected P mPresenter;
    public Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach(mContext, (V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    protected abstract P initPresenter();
}
