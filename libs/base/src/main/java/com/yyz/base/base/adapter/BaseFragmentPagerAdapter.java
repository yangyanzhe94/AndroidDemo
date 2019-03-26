package com.yyz.base.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yyz.base.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类
 * Created by Administrator on 2017/8/31.
 */

public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mBaseFragments = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments) {
        super(fm);
        if (baseFragments != null) {
            mBaseFragments.addAll(baseFragments);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragments.get(position);
    }

    @Override
    public int getCount() {
        return mBaseFragments.size();
    }

}
