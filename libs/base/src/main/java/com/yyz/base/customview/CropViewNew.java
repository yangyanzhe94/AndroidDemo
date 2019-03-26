package com.yyz.base.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.yyz.base.R;


/**
 * Created by lifengmei on 2016/3/23 13:54.
 */
public class CropViewNew extends android.support.v7.widget.AppCompatImageView {

    /**
     * 镂空圆的边框宽度
     */
    private float strokeWidth;
    /**
     * 屏幕的宽
     **/
    private int width;
    /**
     * 屏幕的高
     **/
    private int height;
    private Paint paint;
    /**
     * 遮罩颜色
     */
    private int backgroundColor;
    /**
     * 获取镂空圆边框颜色
     */
    private int strokeColor;
    private Path path;
    private RectF rect = new RectF();

    public CropViewNew(Context context) {
        super(context);
        initColor(context, null, 0);
    }

    public CropViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColor(context, attrs, 0);
    }

    public CropViewNew(Context context, AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
        initColor(context, attrs, defStyle);
    }

    private void initColor(Context context, AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CropCircleImageViewNew, defStyle, 0);
            //获取遮罩背景色
            backgroundColor = attributes.getColor(R.styleable.CropCircleImageViewNew_background_color, getResources().getColor(R.color.base_color_66000000));
            //获取镂空圆边框颜色
            strokeColor = attributes.getColor(R.styleable.CropCircleImageViewNew_stroke_color, Color.WHITE);
            strokeWidth = attributes.getDimensionPixelOffset(R.styleable.CropCircleImageViewNew_stroke_width, 2);
            paint = new Paint();
            paint.setAntiAlias(true);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        int rectLeft = 0;
        int rectTop = (height - width) / 2;
        int rectRight = width;
        int rectBottom = rectTop + width;


        paint = new Paint();

        //绘制阴影部分
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        //抗锯齿
        paint.setAntiAlias(true);
        canvas.drawRect(0, 0, rectRight, rectTop, paint);// 正方形
        canvas.drawRect(0, rectBottom, rectRight, height, paint);


        //绘制截图图片部分
        paint.setColor(strokeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawRect(rectLeft + strokeWidth, rectTop + strokeWidth, rectRight - strokeWidth, rectBottom - strokeWidth, paint);// 正方形
    }
}
