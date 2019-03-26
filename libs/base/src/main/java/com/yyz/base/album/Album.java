package com.yyz.base.album;

import android.app.Activity;
import android.support.annotation.IntDef;

import com.yyz.base.album.choice.AlbumCamera;
import com.yyz.base.album.choice.AlbumChoice;
import com.yyz.base.album.choice.ImageChoice;
import com.yyz.base.album.choice.VideoChoice;
import com.yyz.base.album.impl.Camera;
import com.yyz.base.album.impl.Choice;
import com.yyz.base.album.wrapper.AlbumMultipleWrapper;
import com.yyz.base.album.wrapper.AlbumSingleWrapper;
import com.yyz.base.album.wrapper.ImageCameraWrapper;
import com.yyz.base.album.wrapper.ImageMultipleWrapper;
import com.yyz.base.album.wrapper.ImageSingleWrapper;
import com.yyz.base.album.wrapper.VideoCameraWrapper;
import com.yyz.base.album.wrapper.VideoMultipleWrapper;
import com.yyz.base.album.wrapper.VideoSingleWrapper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/8/31.
 */

public final class Album {

    /**
     * 所有数据
     */
    public static final String ALBUM_DATA = "ALBUM_DATA";
    /**
     * 已选择数据
     */
    public static final String ALBUM_CHECKED_DATA = "ALBUM_CHECKED_DATA";
    /**
     * 拍照、录像、相册、视频 type key
     */
    public static final String MEDIA_TYPE = "MEDIA_TYPE";

    /**
     * 权限 本地读取图片or视频
     */
    public static final int MEDIA_TYPE_FILE = 0;
    /**
     * 本地读取 图片
     */
    public static final int MEDIA_TYPE_IMAGE = 1;
    /**
     * 本地读取 视频
     */
    public static final int MEDIA_TYPE_VIDEO = 2;
    /**
     * 本地读取 图片and 视频
     */
    public static final int MEDIA_TYPE_IMAGE_VIDEO = 3;

    /**
     * 权限 调用摄像头 实现拍照or录像
     */
    public static final int MEDIA_TYPE_CAMERA = 4;
    /**
     * 调用摄像头 拍照
     */
    public static final int MEDIA_TYPE_CAMERA_IMAGE = 5;
    /**
     * 调用摄像头 录制视频
     */
    public static final int MEDIA_TYPE_CAMERA_VIDEO = 6;

    /**
     * 跳转到查看大图界面
     */
    public static final int VIEW_BIG_PICTURE = 7;

    /**
     * 最大文件量
     */
    public static final String LIMIT_COUNT = "LIMIT_COUNT";
    /**
     * 大图展示，选中的图片
     */
    public static final String SHOW_COUNT = "SHOW_COUNT";

    /**
     * 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是1
     */
    public static final String CAMERA_VIDEO_QUALITY = "CAMERA_VIDEO_QUALITY";
    /**
     * 此值设置获取视频的长度，以秒为单位
     */
    public static final String CAMERA_VIDEO_LIMIT_DURATION = "CAMERA_VIDEO_LIMIT_DURATION";
    /**
     * 此值设置获取视频文件的大小，以字节为单位
     */
    public static final String CAMERA_VIDEO_LIMIT_BYTES = "CAMERA_VIDEO_LIMIT_BYTES";


    /**
     * 调用code
     */
    public static final String REQUEST_CODE = "REQUEST_CODE";

    /**
     * 展示是文件夹还是文件
     */
    public static final String SHOW_TYPE = "SHOW_TYPE";
    /**
     * 文件列表展示
     */
    public static final int SHOW_FILE = 10;
    /**
     * 文件夹列表展示
     */
    public static final int SHOW_FOLDER = 11;

    /**
     * 文件的数据
     */
    public static final String REQUEST_FILE = "REQUEST_FILE_DATA";
    /**
     * 文件夹的数据
     */
    public static final String REQUEST_FOLDER = "REQUEST_FOLDER_DATA";

    /**
     * 多选or单选 key
     */
    public static final String MODE_TYPE = "MODE_TYPE";
    /**
     * 单选
     */
    public static final int MODE_SINGLE = 20;

    /**
     * 多选
     */
    public static final int MODE_MULTIPLE = 21;


    @IntDef({SHOW_FILE, SHOW_FOLDER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
    }


    @IntDef({MEDIA_TYPE_IMAGE, MEDIA_TYPE_VIDEO, MEDIA_TYPE_IMAGE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChoiceFunction {
    }

    @IntDef({MEDIA_TYPE_CAMERA_IMAGE, MEDIA_TYPE_CAMERA_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CameraFunction {
    }

    @IntDef({MODE_MULTIPLE, MODE_SINGLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChoiceMode {
    }

    /**
     * Open the camera from the activity.
     *
     * @param act Activity
     * @return
     */
    public static Camera<ImageCameraWrapper, VideoCameraWrapper> camera(Activity act) {
        return new AlbumCamera(act);
    }

    /**
     * Select images.
     *
     * @param act
     * @return
     */
    public static Choice<ImageMultipleWrapper, ImageSingleWrapper> image(Activity act) {
        return new ImageChoice(act);
    }

    /**
     * Select Video
     *
     * @param act
     * @return
     */
    public static Choice<VideoMultipleWrapper, VideoSingleWrapper> video(Activity act) {
        return new VideoChoice(act);
    }

    /**
     * Select image or video
     *
     * @param act
     * @return
     */
    public static Choice<AlbumMultipleWrapper, AlbumSingleWrapper> album(Activity act) {
        return new AlbumChoice(act);
    }


//    voice

}
