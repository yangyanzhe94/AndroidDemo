package com.yyz.base.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yyz.base.base.BasePresenter;

/**
 * Created by Administrator on 2017/6/28.
 */

public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach(this,(V)this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }


    protected abstract P initPresenter();
}
