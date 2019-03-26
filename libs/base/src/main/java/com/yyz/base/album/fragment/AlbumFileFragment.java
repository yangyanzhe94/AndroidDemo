package com.yyz.base.album.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yyz.base.R;
import com.yyz.base.album.Album;
import com.yyz.base.album.activity.AlbumFileAdapter;
import com.yyz.base.album.activity.PhotoViewActivity;
import com.yyz.base.album.impl.AlbumCallback;
import com.yyz.base.album.impl.OnItemCheckedListener;
import com.yyz.base.album.impl.OnItemClickListener;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.runnable.AlbumFileRunnable;
import com.yyz.base.album.util.AlbumUtil;
import com.yyz.base.app.AppHandler;
import com.yyz.base.app.AppManager;
import com.yyz.base.app.HandlerListener;
import com.yyz.base.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.yyz.base.album.Album.ALBUM_CHECKED_DATA;
import static com.yyz.base.album.Album.ALBUM_DATA;
import static com.yyz.base.album.Album.LIMIT_COUNT;
import static com.yyz.base.album.Album.MEDIA_TYPE;
import static com.yyz.base.album.Album.SHOW_COUNT;
import static com.yyz.base.album.Album.VIEW_BIG_PICTURE;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AlbumFileFragment extends BaseFragment implements HandlerListener, OnItemCheckedListener, OnItemClickListener {

    private List<AlbumFile> mAlbumFiles;
    private List<AlbumFile> mCheckedFiles;
    private RecyclerView mRecyclerView;
    private AppHandler appHandler;
    private int mMediaType;
    private int mLimitCount;
    private ColorStateList mSelector;
    private AlbumCallback mAlbumCallback;
    private AlbumFileAdapter albumFileAdapter;
    private int modeType;
    private RelativeLayout rlBack;
    private AppCompatTextView tvTitle;
    private RelativeLayout rlRight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAlbumCallback = (AlbumCallback) context;
        mSelector = AppManager.colorStateList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_album;
    }

    @Override
    public void initViews() {
        mRecyclerView = findViewByIds(R.id.album_base_recycler_view);
        rlBack = findViewByIds(R.id.base_album_rl_back);
        tvTitle = findViewByIds(R.id.base_album_tv_title);
        rlRight = findViewByIds(R.id.base_album_rl_confirm);
    }

    @Override
    public void initListener() {
        rlBack.setOnClickListener(this);
        rlRight.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tvTitle.setText("选择图片");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(AlbumUtil.getDivider(Color.WHITE));
        albumFileAdapter = new AlbumFileAdapter(mContext, mSelector);
        albumFileAdapter.setItemCheckedListener(this);
        albumFileAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(albumFileAdapter);

        if (mBundle != null) {
            mMediaType = mBundle.getInt(MEDIA_TYPE);
            //单选or 多选
            modeType = mBundle.getInt(Album.MODE_TYPE);
            switch (mMediaType) {
                case Album.MEDIA_TYPE_IMAGE:
                case Album.MEDIA_TYPE_VIDEO:
                case Album.MEDIA_TYPE_IMAGE_VIDEO:
                    //本地选择：
                    mCheckedFiles = mBundle.getParcelableArrayList(Album.ALBUM_CHECKED_DATA);
                    if (mCheckedFiles == null) mCheckedFiles = new ArrayList<>();
                    mLimitCount = mBundle.getInt(Album.LIMIT_COUNT);
                    appHandler = new AppHandler(mContext, this);
                    AppManager.executor().submit(new AlbumFileRunnable(mContext, mMediaType, appHandler, mCheckedFiles));
                    break;
                case Album.MEDIA_TYPE_CAMERA:
                case Album.MEDIA_TYPE_CAMERA_IMAGE:
                case Album.MEDIA_TYPE_CAMERA_VIDEO:
                    //拍照、录像
                    break;

            }

        }

    }

    @Override
    public void processClick(View v) {
        int i = v.getId();
        if (i == R.id.base_album_rl_back) {
            if (mAlbumCallback != null) {
                mAlbumCallback.onAlbumCancel();
            }
        } else if (i == R.id.base_album_rl_confirm) {
            if (mAlbumCallback != null && mCheckedFiles != null && mCheckedFiles.size() > 0) {
                mAlbumCallback.onAlbumResult(mCheckedFiles);
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (1) {
            case 1:
                Bundle bundle = msg.getData();
                if (bundle != null) {
                    mAlbumFiles = bundle.getParcelableArrayList(Album.REQUEST_FILE);
                    if (albumFileAdapter != null)
                        albumFileAdapter.setData(mAlbumFiles);
                }
                hideLoading();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Album.VIEW_BIG_PICTURE:  //查看大图
                    //TODO 显示转菊
                    mCheckedFiles = data.getParcelableArrayListExtra(ALBUM_CHECKED_DATA);
                    appHandler = new AppHandler(mContext, this);
                    AppManager.executor().submit(new AlbumFileRunnable(mContext, mMediaType, appHandler, mCheckedFiles));
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        appHandler.removeCallbacksAndMessages(null);
        appHandler = null;
        super.onDestroy();
        this.mAlbumCallback = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, int position, boolean isChecked) {
        if (mAlbumFiles != null && mAlbumFiles.size() >= position) {
            AlbumFile albumFile = mAlbumFiles.get(position);
            albumFile.setChecked(isChecked);
            if (isChecked) {
                if (mCheckedFiles.size() >= mLimitCount) {
                    Toast.makeText(mContext, String.valueOf("最多只能选择" + mLimitCount + "个"), Toast.LENGTH_SHORT).show();
                    compoundButton.setChecked(false);
                    albumFile.setChecked(false);
                } else {
                    mCheckedFiles.add(albumFile);
                    if (modeType == Album.MODE_SINGLE) { //单选
                        if (mAlbumCallback != null) {
                            mAlbumCallback.onAlbumResult(mCheckedFiles);
                        }
                    } else if (modeType == Album.MODE_MULTIPLE) {  //多选

                    }
                }
            } else {
                mCheckedFiles.remove(albumFile);
            }
        } else {
            compoundButton.setChecked(false);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ALBUM_DATA, (ArrayList) mAlbumFiles);
        bundle.putParcelableArrayList(ALBUM_CHECKED_DATA, (ArrayList) mCheckedFiles);
        bundle.putInt(LIMIT_COUNT, mLimitCount);
        bundle.putInt(SHOW_COUNT, position);
        startNewActivityForResult(PhotoViewActivity.class, bundle, VIEW_BIG_PICTURE);

    }

}
