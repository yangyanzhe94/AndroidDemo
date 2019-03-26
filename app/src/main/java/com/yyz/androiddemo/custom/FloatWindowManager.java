package com.yyz.androiddemo.custom;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.yyz.androiddemo.PhoneUtil;


/**
 * FloatWindow管理类
 */
public class FloatWindowManager {

    /**
     * FLAG 用于标志创建的悬浮窗的类型
     */
    public static final int SMALL_SQUARE = 1;
    public static final int BIG_SQUARE = 2;
    public static final int SINGLE_CLICK = 3;

    private static WindowManager mWindowManager;

    private static SquareFloatWindow squareFloatWindow;
    private static WindowManager.LayoutParams squareFloatWindowParams;
    private static SingleClickFloatWindow singleClickFloatWindow;
    private static WindowManager.LayoutParams singleClickFloatWindowParams;


    public static View createFloatWindow(Context context, int flag) {

        switch (flag) {
            case SMALL_SQUARE:
                return createSquareFloatWindow(context, SMALL_SQUARE);
            case BIG_SQUARE:
                return createSquareFloatWindow(context, BIG_SQUARE);
            case SINGLE_CLICK:
                return createSingleClickFloatWindow(context);
            default:
        }

        return null;
    }

    public static View createSquareFloatWindow(Context context, final int flag) {

        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (squareFloatWindow == null) {

            switch (flag) {
                case BIG_SQUARE:
                    squareFloatWindow = new SquareFloatWindow(context, SquareFloatWindow.BIG);
                    break;
                case SMALL_SQUARE:
                default:
                    squareFloatWindow = new SquareFloatWindow(context, SquareFloatWindow.SMALL);
            }
            if (squareFloatWindowParams == null) {
                squareFloatWindowParams = new WindowManager.LayoutParams();
                squareFloatWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                squareFloatWindowParams.format = PixelFormat.RGBA_8888;
                squareFloatWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                squareFloatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                squareFloatWindowParams.width = SquareFloatWindow.viewWidth;
                squareFloatWindowParams.height = SquareFloatWindow.viewHeight;
                squareFloatWindowParams.x = 0;
                squareFloatWindowParams.y = screenHeight / 2;
            }
            windowManager.addView(squareFloatWindow, squareFloatWindowParams);
        }
        return squareFloatWindow;
    }

    public static View createSingleClickFloatWindow(Context context) {
        final WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (singleClickFloatWindow == null) {

            singleClickFloatWindow = new SingleClickFloatWindow.Builder(context).
                    setHeight(48).
                    setWidth(48).
                    setStatusBarHeight(PhoneUtil.getStatusBarHeight(context)).
                    build();
            if (singleClickFloatWindowParams == null) {
                int LAYOUT_FLAG;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
                }
                singleClickFloatWindowParams = new WindowManager.LayoutParams();
                singleClickFloatWindowParams.type = LAYOUT_FLAG;
                singleClickFloatWindowParams.format = PixelFormat.RGBA_8888;
                singleClickFloatWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                singleClickFloatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                singleClickFloatWindowParams.width = SingleClickFloatWindow.viewWidth;
                singleClickFloatWindowParams.height = SingleClickFloatWindow.viewHeight;
                singleClickFloatWindowParams.x = 0;
                singleClickFloatWindowParams.y = screenHeight / 2;
            }

            singleClickFloatWindow.setPositionUpdateListener(new IPositionUpdateListener() {
                @Override
                public void onPositionUpdate(int paramsX, int paramsY) {
                    singleClickFloatWindowParams.x = paramsX;
                    singleClickFloatWindowParams.y = paramsY;
                    windowManager.updateViewLayout(singleClickFloatWindow, singleClickFloatWindowParams);
                }
            });
//            singleClickFloatWindow.setParams(singleClickFloatWindowParams);

            windowManager.addView(singleClickFloatWindow, singleClickFloatWindowParams);
        }
        return singleClickFloatWindow;

    }

    /**
     * 返回windowmagaer实例
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeWindow(Context context, int flag) {

        switch (flag) {
            case SMALL_SQUARE:
            case BIG_SQUARE:
                removeSquareWindow(context);
                break;
            case SINGLE_CLICK:
                removeSingleClickWindow(context);
                break;
            default:
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    private static void removeSquareWindow(Context context) {
        if (squareFloatWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(squareFloatWindow);
            squareFloatWindow = null;
            squareFloatWindowParams = null;
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    private static void removeSingleClickWindow(Context context) {
        if (singleClickFloatWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(singleClickFloatWindow);
            singleClickFloatWindow = null;
            singleClickFloatWindowParams = null;
        }
    }


    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeAllWindow(Context context) {
        removeSquareWindow(context);
        removeSingleClickWindow(context);
    }


    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return squareFloatWindow != null;
    }
}