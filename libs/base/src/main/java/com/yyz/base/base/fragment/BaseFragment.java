package com.yyz.base.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.yyz.base.R;
import com.yyz.base.base.RequestEnum;
import com.yyz.base.base.BaseView;
import com.yyz.base.base.activity.BaseActivity;
import com.yyz.base.utils.ToastUtils;

/**
 * base fragment
 * Created by Administrator on 2017/3/7.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment implements BaseView, View.OnClickListener {

    protected View mConvertView;
    private SparseArray<View> mViews;
    protected Bundle mBundle;
    private long clickTime;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();


    protected Context mContext;

    protected Toolbar mToolbar;
    protected ViewStubCompat timeOut;
    protected ViewStubCompat error;
    protected ViewStubCompat empty;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mConvertView == null) {
            mViews = new SparseArray<>();
            mConvertView = inflater.inflate(R.layout.fragment_base, container, false);
            FrameLayout base = findViewByIds(R.id.f_base);
            base.addView(LayoutInflater.from(mContext).inflate(getLayoutId(), null), base.getLayoutParams());
            initViews();
            initData();
            initListener();
        }


        return mConvertView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    public void showLoading() {
        showLoading(0, 0);
    }

    @Override
    public void showLoading(@LayoutRes int loadRes, @IdRes int clickId) {
        if (mContext != null)
            ((BaseActivity) mContext).showLoading(loadRes, clickId);
    }

    @Override
    public void hideLoading() {
        if (mContext != null)
            ((BaseActivity) mContext).hideLoading();
    }

    public void showEmpty() {
        showEmpty(0, null, null);
    }

    @Override
    public void showEmpty(@DrawableRes int resId, String textTitle, String textContent) {
        if (empty == null) {
            empty = (ViewStubCompat) findViewByIds(R.id.f_base_empty);
            empty.inflate();
            //设置空数据界面显示icon
            AppCompatImageView imageView = (AppCompatImageView) findViewByIds(R.id.base_empty_iv_icon);
            if (imageView != null && resId != 0) {
                imageView.setBackgroundResource(resId);
            }
            //设置空数据界面显示 title
            AppCompatTextView tvTitle = (AppCompatTextView) findViewByIds(R.id.base_empty_tv_title);
            if (tvTitle != null && textTitle != null) {
                tvTitle.setText(textTitle);
            }
            //设置空数据界面显示 content
            AppCompatTextView tvContent = (AppCompatTextView) findViewByIds(R.id.base_empty_tv_content);
            if (tvContent != null && textContent != null) {
                tvContent.setText(textContent);
            }
            //设置空数据界面监听
            View view = findViewByIdOnClick(R.id.base_empty);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onErrorClick(RequestEnum.EMPTY);
                    }
                });
            }
        } else {
                empty.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideEmpty() {
        if (empty != null ) {
            empty.setVisibility(View.GONE);
        }
    }

    public void showError() {
        showError(0, null, null);
    }


    @Override
    public void showError(@DrawableRes int resId, String textTitle, String textContent) {
        if (error == null) {
            error = (ViewStubCompat) findViewByIds(R.id.f_base_error);
            error.inflate();
            //设置显示icon
            AppCompatImageView imageView = (AppCompatImageView) findViewByIds(R.id.base_error_iv_icon);
            if (imageView != null && resId != 0) {
                imageView.setBackgroundResource(resId);
            }
            //设置显示 title
            AppCompatTextView tvTitle = (AppCompatTextView) findViewByIds(R.id.base_error_tv_title);
            if (tvTitle != null && textTitle != null) {
                tvTitle.setText(textTitle);
            }
            //设置显示 content
            AppCompatTextView tvContent = (AppCompatTextView) findViewByIds(R.id.base_error_tv_content);
            if (tvContent != null && textContent != null) {
                tvContent.setText(textContent);
            }
            findViewByIds(R.id.base_error).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onErrorClick(RequestEnum.ERROR);
                }
            });
        } else {
                error.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideError() {
        if (error != null) {
            error.setVisibility(View.GONE);
        }
    }

    public void showTimeOut() {
        showTimeOut(0, null, null);
    }

    @Override
    public void showTimeOut(@DrawableRes int resId, String textTitle, String textContent) {
        if (timeOut == null) {
            timeOut = (ViewStubCompat) findViewByIds(R.id.f_base_time_out);
            timeOut.inflate();
            //设置显示icon
            AppCompatImageView imageView = (AppCompatImageView) findViewByIds(R.id.base_time_out_iv_icon);
            if (imageView != null && resId != 0) {
                imageView.setBackgroundResource(resId);
            }
            //设置显示 title
            AppCompatTextView tvTitle = (AppCompatTextView) findViewByIds(R.id.base_time_out_tv_title);
            if (tvTitle != null && textTitle != null) {
                tvTitle.setText(textTitle);
            }
            //设置显示 content
            AppCompatTextView tvContent = (AppCompatTextView) findViewByIds(R.id.base_time_out_tv_content);
            if (tvContent != null && textContent != null) {
                tvContent.setText(textContent);
            }
            View view = findViewByIds(R.id.base_time_out);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onErrorClick(RequestEnum.TIME_OUT);
                    }
                });
            }
        } else {
                timeOut.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideTimeOut() {
        if (timeOut != null) {
            timeOut.setVisibility(View.GONE);
        }
    }

    /**
     * 处理网络异常等相关异常回调
     *
     * @param anEnum
     */
    public void onErrorClick(RequestEnum anEnum) {
    }


    @Override
    public void toast(@StringRes int toastMsgId) {
        toast(getString(toastMsgId));
    }

    @Override
    public void toast(String toastMsg) {
        ToastUtils.showToast(mConvertView,toastMsg);
    }

    @Override
    public void onClick(View v) {
        if (clickInterval())
            processClick(v);
    }

    public void processClick(View v) {
    }


    /**
     * Finds a view that was identified by the id attribute from the XML that
     *
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findViewByIds(@IdRes int viewId) {
        if (mConvertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    /**
     * 设置监听
     *
     * @param view
     * @param <E>
     * @return
     */
    public <E extends View> E setOnClick(E view) {
        if (view != null) {
            view.setOnClickListener(this);
        }
        return view;
    }

    /**
     * 初始化view 并设置监听
     *
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findViewByIdOnClick(@IdRes int viewId) {
        return (E) setOnClick(findViewByIds(viewId));
    }

    /**
     * add hide 调用
     */
    public void onHide() {

    }

    public void onShow() {
    }


    /**
     * 关闭当前Act
     */
    protected void onBackPressed() {
        if (mContext != null) {
            ((AppCompatActivity) mContext).onBackPressed();
        }
    }


    protected void setToolbar(@NonNull Toolbar toolbar) {
        this.mToolbar = toolbar;
        onCreateOptionsMenu(mToolbar.getMenu(), new SupportMenuInflater(mContext));
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
    }


    @Override
    public void startNewActivity(Class<?> clz) {
        startNewActivity(clz, null);
    }

    /**
     * Activity 界面跳转
     * 会添加统一5.0后的一些动画效果
     *
     * @param clz    目标Activity
     * @param bundle 参数
     */
    @Override
    public void startNewActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startNewActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, clz);
        if (bundle != null) intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
//        Bundle options = new Bundle();
//        ActivityCompat.startActivityForResult((AppCompatActivity) mContext,intent,requestCode,options);
    }

    /**
     * Create a new instance of a Fragment with the given class name.  This is
     * the same as calling its empty constructor.
     *
     * @param tClass he class name of the fragment to instantiate.
     * @param <T>
     * @return
     */
    public <T extends BaseFragment> T getFragment(Class<T> tClass) {
        return getFragment(tClass, null);
    }

    /**
     * Create a new instance of a Fragment with the given class name.  This is
     * the same as calling its empty constructor.
     *
     * @param tClass he class name of the fragment to instantiate.
     * @param bundle Bundle of arguments to supply to the fragment, which it  can retrieve with {@link #getArguments()}.  May be null.
     * @param <T>
     * @return
     */
    public <T extends BaseFragment> T getFragment(Class<T> tClass, Bundle bundle) {
        if (bundle == null) {
            return (T) instantiate(getContext(), tClass.getName());
        }
        return (T) instantiate(getContext(), tClass.getName(), bundle);
    }

    @Override
    public void onShowInput() {
        if (mContext != null && ((AppCompatActivity) mContext).getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(((AppCompatActivity) mContext).getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onHideInput() {
        if (mContext != null && ((AppCompatActivity) mContext).getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((AppCompatActivity) mContext).getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 点击事件的时间间隔，用于防止频繁操作
     *
     * @return
     */
    @Override
    public boolean clickInterval() {
        if (System.currentTimeMillis() - clickTime <= 1000) {
            return false;
        }
        clickTime = System.currentTimeMillis();
        return true;
    }

}
