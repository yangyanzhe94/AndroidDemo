package com.yyz.androiddemo.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yyz.androiddemo.R;


public class SingleClickFloatWindow extends View {

    private Context context;
    private int dpWidth = 48;
    private int dpHeight = 48;
    /**
     * 悬浮窗的宽、高
     */
    public static int viewWidth;
    public static int viewHeight;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    /**
     * 记录系统状态栏的高度
     */
    private int statusBarHeight;


    /**
     * 位置更新监听
     */
    private IPositionUpdateListener iPositionUpdateListener;
    private Paint paint;
    private Bitmap bitmap;

    /**
     * @param dpWidth  dp宽
     * @param dpHeight dp高
     */
    private SingleClickFloatWindow(Context context, int dpWidth, int dpHeight, int statusBarHeight) {
        this(context);
        this.dpHeight = dpHeight;
        this.dpWidth = dpWidth;
        this.statusBarHeight = statusBarHeight;

    }

    public SingleClickFloatWindow(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SingleClickFloatWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleClickFloatWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public static class Builder {

        private Context context;
        private int dpWidth = 48;
        private int dpHeight = 48;
        private int statusBarHeight;

        Builder(Context context) {
            this.context = context;
        }

        public Builder setWidth(int dpWidth) {
            this.dpWidth = dpWidth;
            return this;
        }

        public Builder setHeight(int dpHeight) {
            this.dpHeight = dpHeight;
            return this;
        }

        Builder setStatusBarHeight(int statusBarHeight) {
            this.statusBarHeight = statusBarHeight;
            return this;
        }

        SingleClickFloatWindow build() {

            return new SingleClickFloatWindow(context, dpWidth, dpHeight, statusBarHeight);
        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(viewWidth, viewHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(viewWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, viewHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        viewWidth = getWidth();
        viewHeight = getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initView() {
        //初始化视图
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_games_black_24dp);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        viewWidth = dip2px(context, dpWidth);
        viewHeight = dip2px(context, dpHeight);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - statusBarHeight;
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - statusBarHeight;
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - statusBarHeight;
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    performClick();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        iPositionUpdateListener.onPositionUpdate((int) (xInScreen - xInView), (int) (yInScreen - yInView));
    }


    public void setPositionUpdateListener(IPositionUpdateListener iPositionUpdateListener) {
        this.iPositionUpdateListener = iPositionUpdateListener;
    }
}
