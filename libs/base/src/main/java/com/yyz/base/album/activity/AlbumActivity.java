package com.yyz.base.album.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.yyz.base.R;
import com.yyz.base.album.Album;
import com.yyz.base.album.fragment.AlbumFileFragment;
import com.yyz.base.album.fragment.AlbumFolderFragment;
import com.yyz.base.album.impl.AlbumCallback;
import com.yyz.base.album.impl.AlbumListener;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.util.AlbumUtil;
import com.yyz.base.base.activity.BaseMvpFragmentActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 相册、视频、拍照、录像调用界面
 * Created by Administrator on 2017/8/31.
 */

public class AlbumActivity extends BaseMvpFragmentActivity<AlbumView, AlbumPresenter> implements AlbumCallback {

    private int mMediaType;
    /**
     * 选择本地
     */
    public static AlbumListener<List<AlbumFile>> mAlbumChoiceListener;
    /**
     * 调用相机
     */
    public static AlbumListener<String> mAlbumCameraListener;
    private int mRequestCode;
    private int mShowType;
    @IntRange(from = 0, to = 1)
    private int mQuality = 1;
    @IntRange(from = 1, to = Long.MAX_VALUE)
    private long mLimitDuration = Long.MAX_VALUE;
    private long mLimitBytes = Long.MAX_VALUE;
    private String filePath;


    @Override
    protected AlbumPresenter initPresenter() {
        return new AlbumPresenter();
    }

    @Override
    protected int getLayoutViewId() {
        return R.id.album_frame_layout;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_album;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        //初始化权限
        if (mBundle != null) {
            mMediaType = mBundle.getInt(Album.MEDIA_TYPE);
            mRequestCode = mBundle.getInt(Album.REQUEST_CODE);
            mShowType = mBundle.getInt(Album.SHOW_TYPE);
            switch (mMediaType) {
                case Album.MEDIA_TYPE_IMAGE:
                case Album.MEDIA_TYPE_VIDEO:
                case Album.MEDIA_TYPE_IMAGE_VIDEO:
                    requestPermission(Album.MEDIA_TYPE_FILE);  //本地选择图片or视频
                    break;
                case Album.MEDIA_TYPE_CAMERA_IMAGE:  //拍照
                    requestPermission(Album.MEDIA_TYPE_CAMERA);
                    break;
                case Album.MEDIA_TYPE_CAMERA_VIDEO:  //录制视频
                    mQuality = mBundle.getInt(Album.CAMERA_VIDEO_QUALITY);
                    mLimitDuration = mBundle.getLong(Album.CAMERA_VIDEO_LIMIT_DURATION);
                    mLimitBytes = mBundle.getLong(Album.CAMERA_VIDEO_LIMIT_BYTES);
                    requestPermission(Album.MEDIA_TYPE_CAMERA);
                    break;
                default:
                    onAlbumCancel();
                    break;
            }
        } else {
            onAlbumCancel();
        }

    }

    @Override
    protected void initListener() {

    }


    private synchronized void requestPermission(int permissionType) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = null;
            //区分为拍照、选择图片视频
            switch (permissionType) {
                case Album.MEDIA_TYPE_FILE:  //本地选择权限
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    break;
                case Album.MEDIA_TYPE_CAMERA: //拍照、录制权限
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO};
                    break;
            }
            List<String> permissionLists = new ArrayList<>();
            //判断是否赋予这些权限
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionLists.add(permission);
                }
            }
            permissions = permissionLists.toArray(new String[permissionLists.size()]);
            if (permissions.length > 0) {
                // TODO 调用权限
                ActivityCompat.requestPermissions(
                        this,
                        permissions,
                        permissionType);

            } else {
                //TODO 已经赋予权限，直接调用
                dispatchGrantedPermission(permissionType);
            }

        } else {
            //TODO 直接调用
            dispatchGrantedPermission(permissionType);
        }

    }


    @Override
    public synchronized void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Album.MEDIA_TYPE_FILE || requestCode == Album.MEDIA_TYPE_CAMERA) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    onAlbumCancel();
                    return;
                }
            }
            // TODO 赋予权限了
            dispatchGrantedPermission(requestCode);
        }
    }

    /**
     * 权限赋予调用Fragment 或者直接调用 相机
     *
     * @param requestCode
     */
    private void dispatchGrantedPermission(int requestCode) {
        switch (requestCode) {
            case Album.MEDIA_TYPE_FILE:  //本地选择
                showLoading();
                if (mShowType == Album.SHOW_FILE) {
                    startFragment(getFragment(AlbumFileFragment.class, mBundle));
                } else {
                    startFragment(getFragment(AlbumFolderFragment.class, mBundle));
                }
                break;
            case Album.MEDIA_TYPE_CAMERA:  //相机
                if (mMediaType == Album.MEDIA_TYPE_CAMERA_IMAGE) {
                    //拍照
                    filePath = AlbumUtil.randomImagePath(this);
                    AlbumUtil.imageCapture(this, mMediaType, new File(filePath));
                } else if (mMediaType == Album.MEDIA_TYPE_CAMERA_VIDEO) {
                    //录像
                    filePath = AlbumUtil.randomVideoPath(this);
                    AlbumUtil.videoCapture(this, mMediaType, new File(filePath),
                            mQuality, mLimitDuration, mLimitBytes);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //用于调用相机，实现拍照、录制回调
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Album.MEDIA_TYPE_CAMERA_IMAGE:  //照相
                    if (mAlbumCameraListener != null) {
                        mAlbumCameraListener.onAlbumResult(mRequestCode, filePath);
                    }
                    finish();
                    break;
                case Album.MEDIA_TYPE_CAMERA_VIDEO:  //录制视频
                    if (mAlbumCameraListener != null) {
                        mAlbumCameraListener.onAlbumResult(mRequestCode, filePath);
                    }
                    finish();
                    break;

                case Album.VIEW_BIG_PICTURE:  //查看大图回调，会有回调但是requestCode并不是传递过去的requestCode。
                    /**
                     * 注意： 在AlbumFileFragment调用Fragment.startActivityForResult()方法的时候与Activity.startActivityForResult()存在区别
                     * Fragment.startActivityForResult()调用到新的Activity，当回调回来时候Activity 和 Fragment 都执行 onActivityResult 但是Activity requestCode并是不Fragment调用时候传递的requestCode，而 Fragment中的onActivityResult requestCode 是调用传递的requestCode。
                     */
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (mAlbumChoiceListener != null)
                mAlbumChoiceListener.onAlbumCancel(mRequestCode);
            finish();
        }
    }

    @Override
    public void onAlbumResult(List<AlbumFile> albumFiles) {
        if (mAlbumChoiceListener != null)
            mAlbumChoiceListener.onAlbumResult(mRequestCode, albumFiles);
        finish();
    }

    @Override
    public void onAlbumCancel() {
        if (mAlbumChoiceListener != null)
            mAlbumChoiceListener.onAlbumCancel(mRequestCode);
        finish();
    }

    @Override
    protected void onDestroy() {
        mAlbumChoiceListener = null;
        super.onDestroy();
    }
}
