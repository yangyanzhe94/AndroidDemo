package com.yyz.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yyz.androiddemo.R;
import com.yyz.demo.getappinfo.ui.GetAppInfoActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_main_get_app_info).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.button_main_get_app_info:
                startActivity(new Intent(this, GetAppInfoActivity.class));
                break;
        }

    }
}
