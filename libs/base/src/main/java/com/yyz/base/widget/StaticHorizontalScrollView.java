package com.yyz.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 屏蔽水平滑动事件
 * Created by Administrator on 2017/11/28.
 */

public class StaticHorizontalScrollView extends HorizontalScrollView {
    public StaticHorizontalScrollView(Context context) {
        super(context);
    }

    public StaticHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
//        }
//        return super.onTouchEvent(ev);
    }
}
