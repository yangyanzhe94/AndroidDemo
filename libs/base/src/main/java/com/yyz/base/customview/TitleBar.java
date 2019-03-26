package com.yyz.base.customview;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yyz.base.R;


/**
 * Created by lifengmei on 2017/10/10.
 * 通用的titlebar
 * 1,结构：左（文字或图标），中（文字或搜索框,搜索框默认不可编辑，可设置为可编辑状态），右（文字或图标）；
 * 2,设置每个部分的展示内容，设置则展示，不设置不展示；
 * 3,暴露出每个部分的点击事件，设置则有，不设置则没有；
 * 4,可获得每一部分的view。
 * 5,或许还要修改文字颜色，默认给一个通用的颜色值。
 * 6,改变整个title的背景色是否透明
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener{

    private Context mContext;

    private AppCompatTextView tvLeft;
    private AppCompatImageView ivLeft;
    private AppCompatTextView tvCenter;
    private AppCompatEditText etCenter;
    private AppCompatTextView tvRight;
    private AppCompatImageView ivRight;
    private OnTitleBarClickListener titleBarClickListener;
    private RelativeLayout rlWhole;
    private View vLine;
    private AppCompatImageView ivRedPoint;
    private FrameLayout flLeft;
    private RelativeLayout rlRight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_custom_title_bar,this,true);//将xml加载到该RelativeLayout
        rlWhole = (RelativeLayout) findViewById(R.id.title_rl_whole);
        vLine = findViewById(R.id.title_v_line);
        flLeft = findViewById(R.id.title_fl_left);
        rlRight = findViewById(R.id.title_rl_right);

        tvLeft =  findViewById(R.id.title_tv_left);
        ivLeft = findViewById(R.id.title_iv_left);
        tvCenter = findViewById(R.id.title_tv_center);
        etCenter = findViewById(R.id.title_et_center);
        tvRight = findViewById(R.id.title_tv_right);
        ivRight = findViewById(R.id.title_iv_right);
        ivRedPoint = findViewById(R.id.title_iv_red_point);

    }

    /***
     * 设置左侧文字内容，设置了则展示
     * @param str
     * @return
     */
    public TitleBar setTvLeftContent(String str){
        if(tvLeft != null && !TextUtils.isEmpty(str)){
            tvLeft.setText(str);
            flLeft.setVisibility(VISIBLE);
            tvLeft.setVisibility(VISIBLE);
        }
        return this;
    }

    public TitleBar setTvLeftContent(@StringRes int res){
        if(tvLeft != null){
            tvLeft.setText(res);
            flLeft.setVisibility(VISIBLE);
            tvLeft.setVisibility(VISIBLE);
        }
        return this;
    }

    /**
     * 设置左侧文字颜色
     * @param res
     * @return
     */
    public TitleBar setTvLeftColor(@ColorRes int res){
        if(tvLeft != null){
            flLeft.setVisibility(VISIBLE);
            tvLeft.setTextColor(ContextCompat.getColor(mContext,res));
        }
        return this;
    }

    /**
     * 设置左侧图标内容，设置了内容才展示
     * @param src
     * @return
     */
    public TitleBar setIvLeftContent(int src){
        if(ivLeft != null){
            ivLeft.setImageResource(src);
            flLeft.setVisibility(VISIBLE);
            ivLeft.setVisibility(VISIBLE);
        }
        return this;
    }


    /***
     * 设置title文字内容，设置了才展示
     * @param str
     * @return
     */
    public TitleBar setTvCenterContent(String str){
        if(tvCenter != null && !TextUtils.isEmpty(str)){
            tvCenter.setText(str);
            tvCenter.setVisibility(VISIBLE);
        }
        return this;
    }

    public TitleBar setTvCenterContent(int res){
        if(tvCenter != null){
            tvCenter.setText(res);
            tvCenter.setVisibility(VISIBLE);
        }
        return this;
    }

    /**
     * 设置中间文字颜色
     * @param res
     * @return
     */
    public TitleBar setTvCenterColor(int res){
        if(tvCenter != null){
            tvCenter.setTextColor(ContextCompat.getColor(mContext,res));
        }
        return this;
    }

    /***
     * 设置中间默认展示文字，设置了才展示
     * @param str
     * @return
     */
    public TitleBar setEtCenterHint(String str){
        if(etCenter != null && !TextUtils.isEmpty(str)){
            etCenter.setHint(str);
            etCenter.setVisibility(VISIBLE);
        }
        return this;
    }

    public TitleBar setEtCenterHint(int res){
        if(etCenter != null){
            etCenter.setHint(res);
            etCenter.setVisibility(VISIBLE);
        }
        return this;
    }

    /***
     * 设置中间编辑框中文字
     * @param str
     * @return
     */
    public TitleBar setEtCenterText(String str){
        if(etCenter != null && !TextUtils.isEmpty(str)){
            etCenter.setHint(str);
        }
        return this;
    }
    /***
     * 设置中间的搜索框是否可编辑，根据需求，默认情况下是不可编辑的
     * @param editeable
     * @return
     */
    public TitleBar setEtCenterEditeable(boolean editeable){
        if(editeable){
            etCenter.setFocusableInTouchMode(true);
            etCenter.setFocusable(true);
            etCenter.setCursorVisible(true);
            etCenter.requestFocus();
        }else{
            etCenter.setFocusable(false);
            etCenter.setFocusableInTouchMode(false);
            etCenter.setCursorVisible(false);
        }
        return this;
    }

    /***
     * 设置右侧文字内容，设置了才展示
     * @param str
     * @return
     */
    public TitleBar setTvRightContent(String str){
        if(tvRight != null && !TextUtils.isEmpty(str)){
            tvRight.setText(str);
            rlRight.setVisibility(VISIBLE);
            tvRight.setVisibility(VISIBLE);
        }
        return this;
    }

    public TitleBar setTvRightContent(int res){
        if(tvRight != null){
            tvRight.setText(res);
            rlRight.setVisibility(VISIBLE);
            tvRight.setVisibility(VISIBLE);
        }
        return this;
    }

    /**
     * 设置右侧文字颜色
     * @param res
     * @return
     */
    public TitleBar setTvRightColor(int res){
        if(tvRight != null){
            tvRight.setTextColor(ContextCompat.getColor(mContext,res));
        }
        return this;
    }


    /**
     * 设置右侧图标内容，设置了内容才展示
     * @param src
     * @return
     */
    public TitleBar setIvRightContent(int src){
        if(ivRight != null){
            ivRight.setImageResource(src);
            rlRight.setVisibility(VISIBLE);
            ivRight.setVisibility(VISIBLE);
        }
        return this;
    }

    /**
     * 设置右侧消息红点展示与否
     * @param
     * @return
     */
    public TitleBar setRedPointVisible(boolean isVisible){
        if(isVisible){
            ivRight.setVisibility(VISIBLE);
            ivRedPoint.setVisibility(VISIBLE);
        }else{
            ivRedPoint.setVisibility(GONE);
        }
        return this;
    }

    /***
     * 设置整个title条目的背景颜色，默认是白色，有的地方背景刚开始是透明的，滑动后是白色的
     * @param color
     * @return
     */
    public TitleBar setWholeBgColor(int color){
        if(rlWhole != null){
            rlWhole.setBackgroundResource(color);
        }
        return this;
    }

    /**
     * 设置title背景的透明度
     * @param alpha
     * @return
     */
    public TitleBar setWholeBgAlpha(int alpha){
        if(rlWhole != null){
            rlWhole.getBackground().setAlpha(alpha);
        }
        return this;
    }

    /**
     * 设置title底部的那条线是否可见,透明时不可见，不透明可见
     * @param isVisible
     * @return
     */
    public TitleBar setBottomLineVisible(boolean isVisible){
        if(vLine != null && isVisible){
            vLine.setVisibility(VISIBLE);
        }else if(vLine != null && !isVisible){
            vLine.setVisibility(GONE);
        }
        return this;
    }

    /***
     * 设置每一部分的点击事件，需要哪个部分的点击事件就重新哪个方法
     * @param onclickListener
     * @return
     */
    public TitleBar setOnTitleClickListener(OnTitleBarClickListener onclickListener){
        this.titleBarClickListener = onclickListener;
        return this;
    }

    public static class OnTitleBarClickListener{
        public void onLeftClick(){}//左侧点击事件
        public void onRightClick(){}//右侧点击事件
        public void onTvCenterClick(){}
        public void onEtCenterClick(){}
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.title_fl_left){
            if(titleBarClickListener != null){
                titleBarClickListener.onLeftClick();
            }
        }else if(v.getId() == R.id.title_rl_right){
            if(titleBarClickListener != null){
                titleBarClickListener.onRightClick();
            }
        }else if(v.getId() == R.id.title_tv_center){
            if(titleBarClickListener != null){
                titleBarClickListener.onTvCenterClick();
            }
        }if(v.getId() == R.id.title_et_center){
            if(titleBarClickListener != null){
                titleBarClickListener.onEtCenterClick();
            }
        }
    }

    /**
     * 设置左侧可点击
     * @param isClick
     * @return
     */
    public TitleBar setLeftOnclick(boolean isClick){
        if(flLeft != null && isClick){
            flLeft.setClickable(true);
            flLeft.setOnClickListener(this);
        }else if(flLeft != null && !isClick){
            flLeft.setClickable(false);
            flLeft.setOnClickListener(null);
        }
        return this;
    }

    /**
     * 设置右侧可点击
     * @param isClick
     * @return
     */
    public TitleBar setRightOnclick(boolean isClick){
        if(rlRight != null && isClick){
            rlRight.setClickable(true);
            rlRight.setOnClickListener(this);
        }else if(rlRight != null && !isClick){
            rlRight.setClickable(false);
            rlRight.setOnClickListener(null);
        }
        return this;
    }

    /** 使用时用户自己设置，（设置时才会给点击事件？？--此处） */
    public TitleBar setTvCenterOnclick(boolean isClick){
        if(tvCenter != null && isClick){
            tvCenter.setClickable(true);
            tvCenter.setOnClickListener(this);
        }else if(tvCenter != null && !isClick){
            tvCenter.setClickable(false);
            tvCenter.setOnClickListener(null);
        }
        return this;
    }
    public TitleBar setEtCenterOnclick(boolean isClick){
        if(etCenter != null && isClick){
            etCenter.setClickable(true);
            etCenter.setOnClickListener(this);
        }else if(etCenter != null && !isClick){
            etCenter.setClickable(false);
        }
        return this;
    }

    public TextView getTvLeft() {
        if(tvLeft == null){
            tvLeft = findViewById(R.id.title_tv_left);
        }
        return tvLeft;
    }

    public ImageView getIvLeft() {
        if(ivLeft == null){
            ivLeft = findViewById(R.id.title_iv_left);
        }
        return ivLeft;
    }

    public TextView getTvCenter() {
        if(tvCenter == null){
            tvCenter = findViewById(R.id.title_tv_center);
        }
        return tvCenter;
    }

    public EditText getEtCenter() {
        if(etCenter == null){
            etCenter = findViewById(R.id.title_et_center);
        }
        return etCenter;
    }

    public TextView getTvRight() {
        if(tvRight == null){
            tvRight = findViewById(R.id.title_tv_right);
        }
        return tvRight;
    }

    public ImageView getIvRight() {
        if(ivRight == null){
            ivRight = findViewById(R.id.title_iv_right);
        }
        return ivRight;
    }

    public ImageView getIvRedPoint() {
        if(ivRedPoint == null){
            ivRedPoint = findViewById(R.id.title_iv_red_point);
        }
        return ivRedPoint;
    }

    public RelativeLayout getRlRight() {
        return rlRight;
    }

    public void setRlRight(RelativeLayout rlRight) {
        this.rlRight = rlRight;
    }

    public FrameLayout getFlLeft() {
        return flLeft;
    }

    public void setFlLeft(FrameLayout flLeft) {
        this.flLeft = flLeft;
    }
}
