package com.yyz.base.album.activity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.app.AppManager;
import com.yyz.base.photoview.view.PhotoView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class SamplePagerAdapter extends PagerAdapter {
    private List<AlbumFile> mAlbumFiles;

    public SamplePagerAdapter(List<AlbumFile> albumFiles) {
        this.mAlbumFiles = albumFiles;
    }

    @Override
    public int getCount() {
        if (mAlbumFiles != null) return mAlbumFiles.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        AppManager.image().imageStatic(container.getContext(), photoView, mAlbumFiles != null ? mAlbumFiles.get(position).getPath() : "");
        photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
