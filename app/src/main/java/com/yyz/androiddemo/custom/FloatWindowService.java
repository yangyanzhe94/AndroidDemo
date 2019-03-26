package com.yyz.androiddemo.custom;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;


public class FloatWindowService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (!FloatWindowManager.isWindowShowing()) {
//            创建悬浮窗
//            SquareFloatWindow view = (SquareFloatWindow) FloatWindowManager.createFloatWindow(getApplicationContext(), FloatWindowManager.BIG_SQUARE);
            //根据不同View创建相应的监听方式
//            if (view != null) {
//                view.setSquareFloatWindowClickListener(new ISquareFloatWindowClickListener() {
//                    @Override
//                    public void onContentClick() {
//
//                        Toast.makeText(getApplicationContext(), "内容", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onDeleteCLick() {
//                        FloatWindowManager.removeSquareWindow(getApplicationContext());
//                        closeService();
//                    }
//                });
//            }


            SingleClickFloatWindow singleClickFloatWindow = (SingleClickFloatWindow) FloatWindowManager.createFloatWindow(getApplicationContext(), FloatWindowManager.SINGLE_CLICK);

            singleClickFloatWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FloatWindowManager.removeWindow(getApplicationContext(),FloatWindowManager.SINGLE_CLICK);
                    closeService();
                    Toast.makeText(getApplicationContext(), "内容", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void closeService() {
        Intent intent = new Intent(getApplicationContext(), FloatWindowService.class);
        stopService(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }


}
