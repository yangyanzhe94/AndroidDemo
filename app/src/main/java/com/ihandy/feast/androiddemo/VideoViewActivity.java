package com.ihandy.feast.androiddemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);


        Uri uri = Uri.parse("http://dev-giftsfeast.appcloudbox.net/h5games/fruitacrush/other/fruits_640*1080_02.mp4");
        VideoView videoView = (VideoView) this.findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();


        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                Toast.makeText(VideoViewActivity.this, "点击", Toast.LENGTH_LONG).show();
                return false;
            }
        });

//        videoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(VideoViewActivity.this,"点击",Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
