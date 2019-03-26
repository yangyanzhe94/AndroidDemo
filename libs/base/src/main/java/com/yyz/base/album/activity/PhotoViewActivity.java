package com.yyz.base.album.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yyz.base.R;
import com.yyz.base.album.Album;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.app.AppManager;
import com.yyz.base.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class PhotoViewActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<AlbumFile> mAlbumFiles;
    private List<AlbumFile> mCheckedFiles;
    private int mLimitCount;
    private SamplePagerAdapter samplePagerAdapter;
    private int showCount;
    private Toolbar toolbar;
    private MenuItem menuItem;
    private AppCompatCheckBox appCompatCheckBox;
    private int mPageSelectedPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_photo_view;
    }


    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.base_photo_view_toolbar);
        viewPager = (ViewPager) findViewById(R.id.base_photo_view_hacky_view_pager);
        appCompatCheckBox = (AppCompatCheckBox) findViewById(R.id.base_photo_view_appCompatCheckBox);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        mAlbumFiles = bundle.getParcelableArrayList(Album.ALBUM_DATA);
        mCheckedFiles = bundle.getParcelableArrayList(Album.ALBUM_CHECKED_DATA);
        mLimitCount = bundle.getInt(Album.LIMIT_COUNT);
        mLimitCount =( mLimitCount == Integer.MAX_VALUE ? mAlbumFiles.size() : mLimitCount);
        showCount = bundle.getInt(Album.SHOW_COUNT);
        samplePagerAdapter = new SamplePagerAdapter(mAlbumFiles);
        viewPager.setAdapter(samplePagerAdapter);
        mPageSelectedPosition = showCount;
        viewPager.setCurrentItem(showCount, true);
        setTitle(showCount);
        setSupportActionBar(toolbar);
        appCompatCheckBox.setSupportButtonTintList(AppManager.colorStateList());
        appCompatCheckBox.setChecked(mAlbumFiles.get(showCount).isChecked());
    }

    @Override
    protected void initListener() {
        viewPager.addOnPageChangeListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putIntegerArrayListExtra(Album.ALBUM_CHECKED_DATA, (ArrayList) mCheckedFiles);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        appCompatCheckBox.setOnClickListener(this);
    }

    @Override
    public void processClick(View view) {
        if (view == appCompatCheckBox) {
            if (mCheckedFiles == null)  mCheckedFiles = new ArrayList<>();
           boolean isChecked = appCompatCheckBox.isChecked();
            AlbumFile albumFile = mAlbumFiles.get(mPageSelectedPosition);
            albumFile.setChecked(isChecked);
            if (isChecked){
                if (mCheckedFiles.size() >= mLimitCount) {
                    Toast.makeText(getApplicationContext(), String.valueOf("最多只能选择" + mLimitCount + "个"), Toast.LENGTH_SHORT).show();
                    appCompatCheckBox.setChecked(false);
                    albumFile.setChecked(false);
                }else {
                    mCheckedFiles.add(albumFile);
                }
            }else {
                mCheckedFiles.remove(albumFile);
            }
            setCheckedCountUI(mCheckedFiles.size());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_menu_photo_view, menu);
        menuItem = menu.findItem(R.id.album_base_menu_finish);
        setCheckedCountUI(mCheckedFiles == null ? 0 : mCheckedFiles.size());
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.album_base_menu_finish) {
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra(Album.ALBUM_CHECKED_DATA, (ArrayList) mCheckedFiles);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(Album.ALBUM_CHECKED_DATA, (ArrayList) mCheckedFiles);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPageSelectedPosition = position;
        setTitle(position);
        appCompatCheckBox.setChecked(mAlbumFiles.get(position).isChecked());

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置滑动的title 提示
     *
     * @param count
     */
    public void setTitle(int count) {
        if (toolbar != null) {
            toolbar.setTitle(count + 1 + "/" + mAlbumFiles.size());
        }
    }

    /**
     * 设置选中量
     *
     * @param count
     */
    public void setCheckedCountUI(int count) {
        if (menuItem != null) {
            menuItem.setTitle("完成(" + count + " / " + mLimitCount + ")");
        }
    }
}
