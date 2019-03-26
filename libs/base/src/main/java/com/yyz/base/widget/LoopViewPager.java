package com.yyz.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自动循环播放的viewpager
 */
public class LoopViewPager extends ViewPager {

	public float downX = -1;
	public float downY = -1;

	public LoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoopViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 奇怪自定义侧滑菜单有这句话就能解决问题了！！！！！！！！！！！！！！！！！！
		// 可以让子控件优先处理发生在自己身上的触摸事件
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = ev.getX();
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float movex = ev.getX();
			float movey = ev.getY();
			float xdex = movex - downX;
			float ydex = movey - downY;
			if (Math.abs(xdex) > Math.abs(ydex)) {
				if (this.getCurrentItem() == 0 && xdex > 0) {
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
