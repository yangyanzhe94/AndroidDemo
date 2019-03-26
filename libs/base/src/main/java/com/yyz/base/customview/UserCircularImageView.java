package com.yyz.base.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.yyz.base.R;


public class UserCircularImageView extends AppCompatImageView {
    private int borderWidth;//圆图边的宽度
    private int canvasSize;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;
    private boolean mShowBorder;//圆图是否有边，默认是false没有边。

    public UserCircularImageView(final Context context) {
        this(context, null);
    }

    public UserCircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserCircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
        //是否抗锯齿
        paint.setAntiAlias(true);

        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyle, 0);
        if(attributes != null){
            mShowBorder = attributes.getBoolean(R.styleable.CircularImageView_border, false);

            if (mShowBorder) {//如果设置有边则设置边宽
                paintBorder.setStyle(Paint.Style.FILL_AND_STROKE);//设置为空心
                int defaultBorderSize = (int) (2 * getContext().getResources().getDisplayMetrics().density + 0.5f);//默认边的宽度
                setBorderWidth(attributes.getDimensionPixelOffset(R.styleable.CircularImageView_user_border_width, defaultBorderSize));//根据属性设置边宽，没有设置给默认值
                setBorderColor(attributes.getColor(R.styleable.CircularImageView_user_border_color, Color.WHITE));//根据属性设置边的色值，没有设置默认白色
            }
            attributes.recycle();
        }

//        if (attributes.getBoolean(R.styleable.CircularImageView_shadow, false))
//            addShadow();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        this.requestLayout();
        this.invalidate();
    }

    public void setBorderColor(int borderColor) {
        if (paintBorder != null){
            paintBorder.setColor(borderColor);
        }
        this.invalidate();
    }

    /***
     * 添加阴影
     */
    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//抗锯齿
        image = drawableToBitmap(getDrawable());
        if (image != null) {

            canvasSize = getWidth();
            if (getHeight() < canvasSize)//取小边作为边界
                canvasSize = getHeight();
            int circleCenter = canvasSize / 2;

            //BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize, canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvas.getWidth(), canvas.getHeight(), false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            if (mShowBorder) {//如果有边，画圆环作为边
                //绘制圆环
                paintBorder.setStrokeWidth(borderWidth);
                canvas.drawCircle(circleCenter, circleCenter, (canvasSize / 2)-borderWidth, paintBorder);//注意画圆的时候，border是单独画的，所以半径要将边距宽度borderwidth去掉
            }
            //绘制内圆(图片)
            canvas.drawCircle(circleCenter, circleCenter, ((canvasSize - (borderWidth * 2)) / 2)-borderWidth, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else {
            result = canvasSize;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else {
            result = canvasSize;
        }

        return (result + 2);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            this.measure(0, 0);
            return ((BitmapDrawable) drawable).getBitmap();
        }

        this.measure(0, 0);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}