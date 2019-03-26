package com.yyz.base.album.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyz.base.R;
import com.yyz.base.album.Album;
import com.yyz.base.album.impl.AlbumCallback;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.model.AlbumFolder;
import com.yyz.base.album.runnable.AlbumFolderRunnable;
import com.yyz.base.app.AppHandler;
import com.yyz.base.app.AppManager;
import com.yyz.base.app.HandlerListener;
import com.yyz.base.base.fragment.BaseFragment;
import com.yyz.base.recycler.base.BaseAdapter;
import com.yyz.base.recycler.base.BaseViewHolder;
import com.yyz.base.recycler.interfaces.OnItemClickListener;
import java.util.List;

import static com.yyz.base.album.Album.MEDIA_TYPE;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AlbumFolderFragment extends BaseFragment implements OnItemClickListener, HandlerListener {

    private List<AlbumFolder> mAlbumFolders;
    private RecyclerView mRecyclerView;
    private BaseAdapter<AlbumFolder> mBaseAdapter;
    private AppHandler appHandler;
    private int mMediaType;
    private AlbumCallback mAlbumCallback;
    private ColorStateList mSelector;

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
    }

    @Override
    public void initListener() {
        mBaseAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mBaseAdapter = new BaseAdapter<AlbumFolder>(mContext, mAlbumFolders) {
            @Override
            protected int getLayoutId(int viewType) {
                return R.layout.layout_item_base_album_folder_reycer_view;
            }

            @Override
            protected void bindHolder(BaseViewHolder holder, final int position, int viewType) {
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnItemClickListener != null)
                            mOnItemClickListener.onItemClick(view,0, position);
                    }
                });

                AlbumFolder albumFolder = getData(position);
                if (albumFolder != null) {
                    holder.setText(R.id.album_folder_recycler_view_text_view, albumFolder.getName());
                    List<AlbumFile> albumFiles = albumFolder.getAlbumFiles();
                    if (albumFiles != null && albumFiles.size() > 0) {
                        holder.setImageNetwork(R.id.album_folder_recycler_view_image_view, albumFiles.get(0).getPath());
                    } else {
                        holder.setImageResource(R.id.album_folder_recycler_view_image_view, R.drawable.loading_03);
                    }
                }
            }
        };
        mRecyclerView.setAdapter(mBaseAdapter);

        if (mBundle != null) {
            switch (mBundle.getInt(MEDIA_TYPE)) {
                case Album.MEDIA_TYPE_IMAGE:
                case Album.MEDIA_TYPE_VIDEO:
                case Album.MEDIA_TYPE_IMAGE_VIDEO:
                    //本地选择：
                    mMediaType = mBundle.getInt(MEDIA_TYPE);
                    List<AlbumFolder> mCheckedFolders = mBundle.getParcelableArrayList(Album.ALBUM_CHECKED_DATA);
                    appHandler = new AppHandler(mContext, this);
                    AppManager.executor().submit(new AlbumFolderRunnable(mContext, mMediaType, appHandler, mCheckedFolders));
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
            if (mAlbumCallback != null) {  //TODO 文件夹选择
                mAlbumCallback.onAlbumResult(null);
            }
        }
    }

    @Override
    public void onItemClick(View view,int clickType, int position) {
    }

    @Override
    public void handleMessage(Message msg) {
        switch (2) {
            case 2:
                Bundle bundle = msg.getData();
                if (bundle != null) {
                    mAlbumFolders = bundle.getParcelableArrayList(Album.REQUEST_FOLDER);
                    if (mBaseAdapter != null)
                        mBaseAdapter.setDatas(mAlbumFolders);
                }
                hideLoading();
                break;
        }
    }



    @Override
    public void onDestroy() {
        appHandler.removeCallbacksAndMessages(null);
        appHandler = null;
        super.onDestroy();
    }
}
