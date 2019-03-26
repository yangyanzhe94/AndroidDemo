package com.yyz.base.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yyz.base.base.fragment.BaseFragment;
import com.yyz.base.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/24.
 */

public abstract class BaseMvpFragmentActivity<V, P extends BasePresenter<V>> extends BaseMvpActivity<V, P> {

    private FragmentManager mFManager;
    private Map<BaseFragment, String> mFragmentEntityMap = new HashMap<>();
    private BaseFragment mHideFragment;
    private BaseFragment mShowFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mFManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);

    }

    /**
     * Fragment Id  布局中Fragment的id
     *
     * @return
     */
    protected abstract  @IdRes int getLayoutViewId();

    protected <T extends BaseFragment> T getFragment(Class<T> tClass) {
        return getFragment(tClass, null);
    }


    protected <T extends BaseFragment> T getFragment(Class<T> tClass, Bundle bundle) {
        if (bundle == null) {
            return (T) Fragment.instantiate(this, tClass.getName());
        }
        return (T) Fragment.instantiate(this, tClass.getName(), bundle);
    }

    /**
     * show Fragment default add mode
     *
     * @param targetFragment show Fragment
     * @param <T>
     */
    public <T extends BaseFragment> void startFragment(T targetFragment) {
        startFragment(targetFragment, false);
    }

    /**
     * show Fragment
     *
     * @param targetFragment show Fragment
     * @param isReplace  choice replace or add mode
     * @param <T>
     */
    public <T extends BaseFragment> void startFragment(T targetFragment,boolean isReplace) {
        startFragment(null, targetFragment, isReplace);
    }

    /**
     * show Fragment   default add mode
     *
     * @param nowFragment    hide Fragment
     * @param targetFragment show Fragment
     * @param <T>
     */
    public <T extends BaseFragment> void startFragment(T nowFragment, T targetFragment) {
        startFragment(nowFragment, targetFragment, false);
    }

    /**
     * show Fragment
     *
     * @param nowFragment    hide Fragment
     * @param targetFragment show Fragment
     * @param isReplace      choice replace or add mode
     * @param <T>
     */
    public <T extends BaseFragment> void startFragment(T nowFragment, T targetFragment, boolean isReplace) {
        FragmentTransaction fragmentTransaction = mFManager.beginTransaction();
        if (targetFragment != null) {
            if (isReplace) {
                fragmentTransaction.replace(getLayoutViewId(), targetFragment);
                targetFragment.hashCode();
            } else {
                //上个Fragment 不为空且 add 做隐藏处理。
                if (nowFragment != null && nowFragment.isAdded()) {
                    mHideFragment = nowFragment;
                    nowFragment.onPause();
                    nowFragment.onStop();
                    nowFragment.onHide();
                    fragmentTransaction.hide(nowFragment);
                }else {
                    if (mShowFragment != null && targetFragment != mShowFragment){
                        mShowFragment.onHide();
                        fragmentTransaction.hide(mShowFragment);
                    }
                }
                //targetFragment 已经add 则直接显示即可。
                if (!targetFragment.isAdded()) {
                    fragmentTransaction.add(getLayoutViewId(), targetFragment, targetFragment.getClass().getSimpleName());
                }
                targetFragment.onShow();
                mShowFragment = targetFragment;
                fragmentTransaction.show(targetFragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
