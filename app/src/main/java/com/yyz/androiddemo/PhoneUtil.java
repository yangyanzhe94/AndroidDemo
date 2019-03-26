package com.yyz.androiddemo;

import android.content.Context;


public class PhoneUtil {

    public static int getStatusBarHeight(Context context) {

//        Rect rectangle = new Rect();
//        Window window = context.getApplicationContext().getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        int statusBarHeight = rectangle.top;
//        int contentViewTop =
//                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;


        int statusBarHeight = 0;
        if (statusBarHeight == 0) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return statusBarHeight;
    }
}
