package com.yyz.androiddemo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyz.androiddemo.R;


public class SquareFloatWindow extends LinearLayout implements View.OnClickListener {

    /**
     * FLAG 用于标志创建的悬浮窗的类型
     */
    public static final int SMALL = 1;
    public static final int BIG = 2;

    private int flag;
    private Context context;
    private Button btnClose;
    private TextView tvContent;

    /**
     * 悬浮窗的宽、高
     */
    public static int viewWidth;
    public static int viewHeight;

    private ISquareFloatWindowClickListener iSquareFloatWindowClickListener;

    public SquareFloatWindow(Context context) {
        super(context);
    }
    public SquareFloatWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFloatWindow(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    public SquareFloatWindow(Context context, int flag) {
        super(context);

        this.context = context;
        this.flag = flag;

        initView();
        initListener();
    }

    private void initListener() {

        btnClose.setOnClickListener(this);
        tvContent.setOnClickListener(this);

    }

    private void initView() {

        LayoutInflater.from(context).inflate(R.layout.view_float_window_small_square, this);

        LinearLayout llFloatWindow = findViewById(R.id.ll_float_window);
        btnClose = findViewById(R.id.btn_square_float_window_close);
        tvContent = findViewById(R.id.tv_square_float_window_content);


        switch (flag) {
            case BIG:
                ViewGroup.LayoutParams llBigLayoutParams = llFloatWindow.getLayoutParams();
                //动态修改布局 扩展到全屏
                llBigLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                llFloatWindow.setLayoutParams(llBigLayoutParams);
                break;
            //默认为小布局
            case SMALL:
            default:
                ViewGroup.LayoutParams llSmallLayoutParams = llFloatWindow.getLayoutParams();
                llSmallLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                llFloatWindow.setLayoutParams(llSmallLayoutParams);

        }


        viewWidth = llFloatWindow.getLayoutParams().width;
        viewHeight = llFloatWindow.getLayoutParams().height;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_square_float_window_close:

                if (iSquareFloatWindowClickListener != null)
                    iSquareFloatWindowClickListener.onDeleteCLick();

                break;
            case R.id.tv_square_float_window_content:
                if (iSquareFloatWindowClickListener != null)
                    iSquareFloatWindowClickListener.onContentClick();
            default:
        }
    }


    /**
     * 设置自定义监听
     */
    public void setSquareFloatWindowClickListener(ISquareFloatWindowClickListener iSquareFloatWindowClickListener) {
        this.iSquareFloatWindowClickListener = iSquareFloatWindowClickListener;
    }
}