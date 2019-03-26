package com.yyz.base.base.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ViewStubCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.yyz.base.R;
import com.yyz.base.base.RequestEnum;
import com.yyz.base.base.BaseView;
import com.yyz.base.utils.ToastUtils;

import java.lang.reflect.Method;


/**
 * 基类
 * 鉴于EventBus 订阅采用反射机制，而且在架构中会有部分Activity使用，所以在Base里面，我们关闭EventBus 订阅和发布
 * <p>
 * loading、timeOut、error、empty 状态下展示
 * Created by Administrator on 2017/6/28.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, View.OnClickListener {

    private long clickTime = 0L;
    protected boolean translucentStatus = false;
    private ViewStubCompat loading;
    private ViewStubCompat timeOut;
    private ViewStubCompat error;
    private ViewStubCompat empty;
    private FrameLayout base;
    protected Bundle mBundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBundle = getIntent().getExtras();
//        setTranslucentStatus();
        if (getLayoutId() != 0) {
            init(savedInstanceState);
        }
    }

    /**
     * 初始化 view、data、listener
     */
    private void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_base);
        base = (FrameLayout) findViewById(R.id.act_base);
        View mView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        base.addView(mView, base.getLayoutParams());
        initView();
        initData(savedInstanceState);
        initListener();
    }

    /**
     * 获取布局Layout
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int getLayoutId();


    /**
     * 初始化 View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * 设置监听
     */
    protected abstract void initListener();

    @Override
    public void onClick(View v) {
        if (clickInterval())
            processClick(v);
    }


    public void processClick(View view) {
    }


    @Override
    public void showLoading() {
        showLoading(0, 0);
    }

    @Override
    public void showLoading(@LayoutRes int loadRes, @IdRes int clickId) {
        onHideInput();
        if (loading == null) {
            loading = (ViewStubCompat) findViewById(R.id.act_base_loading);
            loading.inflate();
        } else {
                loading.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void hideLoading() {
        if (loading != null ) {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmpty() {
        showEmpty(0, null, null);
    }

    @Override
    public void showEmpty(@DrawableRes int resId, String textTitle, String textContent) {
        if (empty == null) {
            empty = (ViewStubCompat) findViewById(R.id.act_base_empty);
            empty.inflate();
            //设置空数据界面显示icon
            AppCompatImageView imageView = (AppCompatImageView) findViewById(R.id.base_empty_iv_icon);
            if (imageView != null && resId != 0) {
                imageView.setBackgroundResource(resId);
            }
            //设置空数据界面显示 title
            AppCompatTextView tvTitle = (AppCompatTextView) findViewById(R.id.base_empty_tv_title);
            if (tvTitle != null && textTitle != null) {
                tvTitle.setText(textTitle);
            }
            //设置空数据界面显示 content
            AppCompatTextView tvContent = (AppCompatTextView) findViewById(R.id.base_empty_tv_content);
            if (tvContent != null && textContent != null) {
                tvContent.setText(textContent);
            }
            //设置空数据界面监听
            View view = findViewById(R.id.base_empty);
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

    @Override
    public void showError() {
        showError(0, null, null);
    }

    @Override
    public void showError(@DrawableRes int resId, String textTitle, String textContent) {
        if (error == null) {
            error = (ViewStubCompat) findViewById(R.id.act_base_error);
            error.inflate();
            //设置显示icon
            AppCompatImageView imageView = (AppCompatImageView) findViewById(R.id.base_error_iv_icon);
            if (imageView != null && resId != 0) {
                imageView.setBackgroundResource(resId);
            }
            //设置显示 title
            AppCompatTextView tvTitle = (AppCompatTextView) findViewById(R.id.base_error_tv_title);
            if (tvTitle != null && textTitle != null) {
                tvTitle.setText(textTitle);
            }
            //设置显示 content
            AppCompatTextView tvContent = (AppCompatTextView) findViewById(R.id.base_error_tv_content);
            if (tvContent != null && textContent != null) {
                tvContent.setText(textContent);
            }
            View view = findViewById(R.id.base_error);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onErrorClick(RequestEnum.ERROR);
                    }
                });
            }
        } else {
                error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideError() {
        if (error != null ) {
            error.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTimeOut() {
        showTimeOut(0, null, null);
    }

    @Override
    public void showTimeOut(@DrawableRes int resId, String textTitle, String textContent) {
        if (timeOut == null) {
            timeOut = (ViewStubCompat) findViewById(R.id.act_base_time_out);
            timeOut.inflate();
            //设置显示icon
            AppCompatImageView imageView = (AppCompatImageView) findViewById(R.id.base_time_out_iv_icon);
            if (imageView != null && resId != 0) {
                imageView.setBackgroundResource(resId);
            }
            //设置显示 title
            AppCompatTextView tvTitle = (AppCompatTextView) findViewById(R.id.base_time_out_tv_title);
            if (tvTitle != null && textTitle != null) {
                tvTitle.setText(textTitle);
            }
            //设置显示 content
            AppCompatTextView tvContent = (AppCompatTextView) findViewById(R.id.base_time_out_tv_content);
            if (tvContent != null && textContent != null) {
                tvContent.setText(textContent);
            }
            View view = findViewById(R.id.base_time_out);
            if (view != null)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onErrorClick(RequestEnum.TIME_OUT);
                    }
                });
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
        ToastUtils.showToast(base,toastMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public  void onMainEvent(EventInfo eventInfo){
//
//    }
//
//    @Subscribe(threadMode = ThreadMode.ASYNC)
//    public void onAsyncEvent(EventInfo eventInfo){
//
//    }
//
//    @Subscribe(threadMode =  ThreadMode.BACKGROUND)
//    public void onBackgroundEvent(EventInfo eventInfo){
//
//    }
//
//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void onPostingEvent(EventInfo eventInfo){
//
//    }

//    /**
//     * Finds a view that was identified by the id attribute from the XML that
//     *
//     * @param viewId
//     * @param <T>
//     * @return
//     */
//    protected <T extends View> T findViewByIds(@IdRes int viewId) {
//        return (T) findViewById(viewId);
//    }


    @Override
    public void startNewActivity(Class<?> clz) {
        startNewActivity(clz, null);
    }

    /**
     * Activity 界面跳转
     *
     * @param clz    目标Activity
     * @param bundle 参数
     */
    @Override
    public void startNewActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startNewActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
//    /**
//     * 点击软键盘之外的空白处，隐藏软件盘
//     * @param ev
//     * @return
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (ToolUtil.isShouldHideInput(v, ev)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }


    @Override
    public void onShowInput() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onHideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 点击事件的时间间隔，用于防止频繁操作
     *
     * @return
     */
    @Override
    public boolean clickInterval() {
        if (System.currentTimeMillis() - clickTime <= 500) {
            return false;
        }
        clickTime = System.currentTimeMillis();
        return true;
    }


    /**
     * 设置沉浸式
     */
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (translucentStatus) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)   //设置状态背景颜色
//                win.setStatusBarColor(getResources().getColor(R.color.color_00000000));
                win.setAttributes(winParams);
        }
    }

    /**
     * 虚拟键盘
     *
     * @param context
     * @return
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }


    /**
     * 隐藏虚拟键盘
     */
    public void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
