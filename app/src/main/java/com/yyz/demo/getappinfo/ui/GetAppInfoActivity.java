package com.yyz.demo.getappinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yyz.androiddemo.R;
import com.yyz.demo.MainActivity;
import com.yyz.demo.getappinfo.UseTimeDataManager;
import com.yyz.demo.getappinfo.adapter.UseTimeAdapter;
import com.yyz.demo.getappinfo.utils.DateTransUtils;

import java.util.ArrayList;

public class GetAppInfoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<String> mDateList;
    private UseTimeDataManager mUseTimeDataManager;
    private UseTimeAdapter mUseTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_app_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initData(0);
        initView();
    }

    private void initData(int dayNum) {
        mDateList = DateTransUtils.getSearchDays();
        mUseTimeDataManager = UseTimeDataManager.getInstance(getApplicationContext());
        mUseTimeDataManager.refreshData(dayNum);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_get_app_info_activity_show_statistics);
        showView(0);
    }

    private void showView(int dayNumber) {

        mUseTimeAdapter = new UseTimeAdapter(this, mUseTimeDataManager.getmPackageInfoListOrderByTime());
        mUseTimeAdapter.setOnItemClickListener(new UseTimeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String pkg) {
                Intent i = new Intent();
                i.setClassName(GetAppInfoActivity.this, UseTimeDetailActivity.class.getName());
                i.putExtra("type", "times");
                i.putExtra("pkg", pkg);
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(mUseTimeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_app_info_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_get_app_info_detail_all) {
            Intent i = new Intent();
            i.setClassName(this, UseTimeDetailActivity.class.getName());
            i.putExtra("type", "times");
            i.putExtra("pkg", "all");
            startActivity(i);
            return true;
        } else if (id == R.id.btn_get_app_info_detail_go_setting) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
