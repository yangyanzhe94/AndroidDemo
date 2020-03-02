package com.yyz.demo.getappinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yyz.androiddemo.R;
import com.yyz.demo.getappinfo.UseTimeDataManager;
import com.yyz.demo.getappinfo.adapter.UseTimeDetailAdapter;
import com.yyz.demo.getappinfo.adapter.UseTimeEveryDetailAdapter;
import com.yyz.demo.getappinfo.utils.OneTimeDetails;

public class UseTimeDetailActivity extends AppCompatActivity {
    private UseTimeDataManager mUseTimeDataManager;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_time_detail);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mUseTimeDataManager = UseTimeDataManager.getInstance(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_use_time_detail);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        initView(type, intent);
    }


    private void initView(String type, Intent intent) {
        if ("times".equals(type)) {
            //显示为次数统计信息
            showAppOpenTimes(intent.getStringExtra("pkg"));
//            mToolbar.setTitle("应用使用次数信息统计");
        } else if ("details".equals(type)) {
            //显示为activity统计信息
            showAppOpenDetails(intent.getStringExtra("pkg"));
//            mToolbar.setTitle("activity信息统计");
        } else {
            Log.i(UseTimeDataManager.TAG, "   未知类型    ");
        }
    }


    private void showAppOpenTimes(String pkg) {
        UseTimeDetailAdapter useTimeDetailAdapter = new UseTimeDetailAdapter(mUseTimeDataManager.getPkgOneTimeDetailList(pkg));
        useTimeDetailAdapter.setOnItemClickListener(new UseTimeDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, OneTimeDetails details) {
                mUseTimeDataManager.setmOneTimeDetails(details);
                showDetail(details.getPkgName());
            }
        });
        mRecyclerView.setAdapter(useTimeDetailAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void showAppOpenDetails(String pkg) {
        if (!pkg.equals(mUseTimeDataManager.getmOneTimeDetails().getPkgName())) {
            Log.i(UseTimeDataManager.TAG, "  showAppOpenDetails()    包名不一致 ");
        }
        UseTimeEveryDetailAdapter useTimeEveryDetailAdapter = new UseTimeEveryDetailAdapter(mUseTimeDataManager.getmOneTimeDetails().getOneTimeDetailEventList());
        mRecyclerView.setAdapter(useTimeEveryDetailAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void showDetail(String pkg) {
        Intent i = new Intent();
        i.setClassName(this, UseTimeDetailActivity.class.getName());
        i.putExtra("type", "details");
        i.putExtra("pkg", pkg);
        startActivity(i);
    }

}
