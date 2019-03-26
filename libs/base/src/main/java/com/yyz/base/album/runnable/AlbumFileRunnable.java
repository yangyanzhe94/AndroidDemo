package com.yyz.base.album.runnable;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.yyz.base.album.Album;
import com.yyz.base.album.model.AlbumFile;
import com.yyz.base.album.model.AlbumFolder;
import com.yyz.base.album.util.MediaReader;
import com.yyz.base.app.AppHandler;
import com.yyz.base.base.BaseRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AlbumFileRunnable extends BaseRunnable {
    private Context mContext;
    @Album.ChoiceFunction
    private int mFunction;
    private AppHandler mAppHandler;
    private List<AlbumFile> mCheckedFiles;
    public AlbumFileRunnable(Context context, int function, AppHandler appHandler, List<AlbumFile> checkedFiles){
        this.mContext = context;
        this.mFunction = function;
        this.mAppHandler = appHandler;
        this.mCheckedFiles = checkedFiles;

    }
    @Override
    public  void run() {
        synchronized (this){
            ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
            ArrayList<AlbumFolder> albumFolders = null;
            switch (mFunction){
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

            //遍历所有文件夹，得到所有文件
            if (albumFolders != null ){
                for (AlbumFolder albumFolder : albumFolders){
                    if ( albumFolder != null){
                        ArrayList<AlbumFile> albumFiles =   albumFolder.getAlbumFiles();
                        for (AlbumFile albumFile : albumFiles){
                            if (mCheckedFiles != null && mCheckedFiles.contains(albumFile)){
                                albumFile.setChecked(true);
                            }
                            mAlbumFiles.add(albumFile);
                        }
                    }
                }
            }
            Message message = new Message();
            message.what = 1;
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Album.REQUEST_FILE,mAlbumFiles);
            message.setData(bundle);
            if (mAppHandler != null){
                mAppHandler.sendMessage(message);
            }
        }

    }
}
