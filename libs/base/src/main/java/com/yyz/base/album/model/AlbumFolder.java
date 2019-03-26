package com.yyz.base.album.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AlbumFolder  implements Parcelable {
    /**
     * Folder id
     */
    private int id;
    /**
     * Folder name.
     */
    private String name;
    /**
     * Image list in folder.
     */
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    /**
     * checked.
     */
    private boolean isChecked;
    public AlbumFolder() {
    }

    protected AlbumFolder(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mAlbumFiles = in.createTypedArrayList(AlbumFile.CREATOR);
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(mAlbumFiles);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumFolder> CREATOR = new Creator<AlbumFolder>() {
        @Override
        public AlbumFolder createFromParcel(Parcel in) {
            return new AlbumFolder(in);
        }

        @Override
        public AlbumFolder[] newArray(int size) {
            return new AlbumFolder[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ArrayList<AlbumFile> getAlbumFiles() {
        return mAlbumFiles;
    }

    public void addAlbumFile(AlbumFile albumFile) {
        if (!mAlbumFiles.contains(albumFile))  mAlbumFiles.add(albumFile);
    }


}
