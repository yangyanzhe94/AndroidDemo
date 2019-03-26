package com.yyz.base.album.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyz.base.R;
import com.yyz.base.album.impl.OnItemCheckedListener;
import com.yyz.base.album.impl.OnItemClickListener;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.util.AlbumUtil;
import com.yyz.base.app.AppManager;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */

public class AlbumFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ColorStateList mColorStateList;
    private List<AlbumFile> mAlbumFiles;
    private LayoutInflater mInflater;
    private OnItemClickListener mItemClickListener;
    private OnItemCheckedListener mItemCheckedListener;


    public AlbumFileAdapter(Context context, ColorStateList colorStateList) {
        this.mContext = context;
        this.mColorStateList = colorStateList;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<AlbumFile> albumFiles) {
        if (albumFiles != null)
            this.mAlbumFiles = albumFiles;
        notifyDataSetChanged();

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItemCheckedListener(OnItemCheckedListener checkListener) {
        this.mItemCheckedListener = checkListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(mInflater.inflate(R.layout.layout_item_base_album_file_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageHolder imageHolder = (ImageHolder) holder;
        AlbumFile albumFile = mAlbumFiles.get(position);
        imageHolder.setData(albumFile);
    }

    @Override
    public int getItemCount() {
        if (mAlbumFiles != null) return mAlbumFiles.size();
        return 0;
    }

    private class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatImageView appCompatImageView;
        private AppCompatCheckBox appCompatCheckBox;
        private AppCompatTextView appCompatTextView;

        public ImageHolder(View itemView) {
            super(itemView);
            appCompatImageView = itemView.findViewById(R.id.album_file_recycler_view_appCompatImageView);
            appCompatCheckBox = itemView.findViewById(R.id.album_file_recycler_view_appCompatCheckBox);
            appCompatTextView = itemView.findViewById(R.id.album_file_recycler_view_tv_ic_video);
            appCompatImageView.setOnClickListener(this);
            appCompatCheckBox.setOnClickListener(this);
        }


        void setData(AlbumFile albumFile) {
            if (albumFile != null) {
                if (albumFile.getMediaType() == AlbumFile.TYPE_VIDEO) {
                    appCompatTextView.setVisibility(View.VISIBLE);
                    appCompatTextView.setText(AlbumUtil.convertDuration(albumFile.getDuration()));
                } else {
                    appCompatTextView.setVisibility(View.GONE);
                }
                if (mColorStateList != null)
                    appCompatCheckBox.setSupportButtonTintList(mColorStateList);
                appCompatCheckBox.setChecked(albumFile.isChecked());
                AppManager.image().imageStatic(mContext, appCompatImageView, albumFile.getPath());
            }
        }

        @Override
        public void onClick(View view) {
            if (view == appCompatImageView) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, getAdapterPosition());
                }
            } else if (view == appCompatCheckBox) {
                if (mItemCheckedListener != null) {
                    boolean isChecked = appCompatCheckBox.isChecked();
                    mItemCheckedListener.onCheckedChanged(appCompatCheckBox, getAdapterPosition(), isChecked);
                }
            }
        }
    }
}
