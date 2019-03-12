package com.ihandy.feast.androiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGoVideo;
    private Button mBtnGoSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnGoVideo = findViewById(R.id.btn_go_video_activity);
        mBtnGoSurface = findViewById(R.id.btn_go_surface_activity);

        mBtnGoSurface.setOnClickListener(this);
        mBtnGoVideo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_go_video_activity:
                startActivity(new Intent(this, VideoViewActivity.class));
                break;
            case R.id.btn_go_surface_activity:
                startActivity(new Intent(this, SurfaceActivity.class));
                break;
        }
    }
}
