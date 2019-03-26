package com.yyz.androiddemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yyz.androiddemo.custom.FloatWindowService;
import com.yyz.androiddemo.floatingwindow.MyFloatWindowService;


public class MainActivity extends AppCompatActivity {

    Button btnShowFloatingWindow;
    Button btnShowSmallSquareFW;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        requestPermissions();
    }

    private void requestPermissions() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void init() {

        btnShowFloatingWindow = findViewById(R.id.btn_show_float_window);
        btnShowSmallSquareFW = findViewById(R.id.btn_show_small_square_float_window);

        btnShowFloatingWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(MainActivity.this, MyFloatWindowService.class));
            }
        });

        btnShowSmallSquareFW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(MainActivity.this, FloatWindowService.class));
            }
        });


    }
}
