package com.yyz.base.album.runnable;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.yyz.base.album.Album;
import com.yyz.base.album.model.AlbumFolder;
import com.yyz.base.album.util.MediaReader;
import com.yyz.base.app.AppHandler;
import com.yyz.base.base.BaseRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AlbumFolderRunnable extends BaseRunnable {
    private Context mContext;
    private int mFunction;
    private AppHandler mAppHandler;
    private List<AlbumFolder> mCheckedFolders;

    public AlbumFolderRunnable(Context context, int function, AppHandler appHandler, List<AlbumFolder> checkedFolders) {
        this.mContext = context;
        this.mFunction = function;
        this.mAppHandler = appHandler;
        this.mCheckedFolders = checkedFolders;

    }

    @Override
    public void run() {
        synchronized (this) {
            ArrayList<AlbumFolder> albumFolders = null;
            switch (mFunction) {
                case Album.MEDIA_TYPE_IMAGE:
                    albumFolders = new MediaReader(mContext).getImage();
                    break;
                case Album.MEDIA_TYPE_VIDEO:
                    albumFolders = new MediaReader(mContext).getVideo();
                    break;
                case Album.MEDIA_TYPE_IMAGE_VIDEO:
                    albumFolders = new MediaReader(mContext).getMedia();
                    break;
            }

            Message message = new Message();
            message.what = 2;
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Album.REQUEST_FOLDER, albumFolders);
            message.setData(bundle);
            if (mAppHandler != null) {
                mAppHandler.sendMessage(message);
            }
        }
    }
}