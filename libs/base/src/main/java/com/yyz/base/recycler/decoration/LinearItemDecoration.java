package com.yyz.base.recycler.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyz.base.R;

/**
 * Created by Administrator on 2016/10/18.
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private Paint mPaint;
    private int mLineHeight;
    private int mOrientation;
    private boolean isHeadView;

    public LinearItemDecoration(Context context) {
        this(context, VERTICAL);
    }

    public LinearItemDecoration(Context context, int orientation) {
        this(context, orientation, false);
    }

    public LinearItemDecoration(Context context, boolean isHeadView) {
        this(context, VERTICAL, isHeadView);
    }

    public LinearItemDecoration(Context context, int orientation, boolean isHeadView) {
        this(context, orientation, R.color.color_recycler_view_lines, R.dimen.recycler_view_lines, isHeadView);
    }

    public LinearItemDecoration(Context context, int orientation, @ColorRes int lineColor, @DimenRes int lineHeight, boolean isHeadView) {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(context, lineColor));
        mLineHeight = context.getResources().getDimensionPixelOffset(lineHeight);
        this.isHeadView = isHeadView;
        setOrientation(orientation);
    }


    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        switch (mOrientation) {
            case HORIZONTAL:
                drawHorizontal(c, parent);
                break;
            case VERTICAL:
                drawVertical(c, parent);
                break;
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            if (isHeadView && i == 0) continue;
            final View child = parent.getChildAt(i);
            final int top = child.getBottom();
            final int bottom = top + mLineHeight;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isHeadView && i == 0) continue;
            final View child = parent.getChildAt(i);
            final int left = child.getRight();
            final int right = left + mLineHeight;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        switch (mOrientation) {
            case HORIZONTAL:
                outRect.right = mLineHeight;
                break;
            case VERTICAL:
                outRect.bottom = mLineHeight;
                break;
        }

    }
}