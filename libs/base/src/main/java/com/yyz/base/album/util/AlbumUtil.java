package com.yyz.base.album.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.yyz.base.album.divider.Api20ItemDivider;
import com.yyz.base.album.divider.Api21ItemDivider;
import com.yyz.base.album.divider.Divider;
import com.yyz.base.app.AppManager;

import java.io.File;
import java.util.Random;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AlbumUtil {
    /**
     * take pictures.
     *
     * @param act
     * @param requestCode
     * @param outPath
     */
    public static void imageCapture(@NonNull Activity act, int requestCode, File outPath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = getUri(act, outPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        act.startActivityForResult(intent, requestCode);
    }

    /**
     * record videos.
     *
     * @param act         activity
     * @param requestCode code
     * @param outPath     file path
     * @param quality     currently value 0 means low quality, suitable for MMS messages, and  value 1 means high quality.
     * @param duration    specify the maximum allowed recording duration in seconds.
     * @param limitBytes  specify the maximum allowed size.
     */
    public static void videoCapture(@NonNull Activity act, int requestCode, File outPath, @IntRange(from = 0, to = 1) int quality,
                                    @IntRange(from = 1, to = Long.MAX_VALUE) long duration,
                                    @IntRange(from = 1, to = Long.MAX_VALUE) long limitBytes) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Uri uri = getUri(act, outPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, duration);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, limitBytes);
        act.startActivityForResult(intent, requestCode);
    }

    /**
     * A random name for the image path.
     *
     * @return
     */
    public static String randomImagePath(Context context) {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppManager.name() + "/image";
        } else {
            path = context.getCacheDir().getAbsolutePath() + "/" + AppManager.name() + "/image";
        }
        File PathDir = new File(path);
        if (!PathDir.exists()) {
            PathDir.mkdirs();
        }
        return path + "/" + System.currentTimeMillis() + "-" + new Random().nextInt() + ".png";
    }

    /**
     * A random name for the video path.
     *
     * @return
     */
    public static String randomVideoPath(Context context) {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppManager.name() + "/video";
        } else {
            path = context.getCacheDir().getAbsolutePath() + "/" + AppManager.name() + "/image";
        }
        File PathDir = new File(path);
        if (!PathDir.exists()) {
            PathDir.mkdirs();
        }
        return path + "/" + System.currentTimeMillis() + "-" + new Random().nextInt() + ".mp4";
    }


    /**
     * Generates an externally accessed URI based on path.
     * 需要 验证7.0 之后的
     *
     * @param context
     * @param outPath
     * @return
     */
    public static
    @NonNull
    Uri getUri(Context context, File outPath) {
        Uri uri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 7.0之前获取uri方式
            uri = Uri.fromFile(outPath);
        } else {
            //7.0之后获取uri方式
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".album.camera.provider", outPath);
        }
        return uri;
    }

    /**
     * get available disk size
     * <p>
     * External or Internal
     *
     * @return
     */
    public static long getAvailableDiskSize() {
        File path;
        long blockSize;
        long availableBlocks;
        //判断 SDCard 是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory();
        } else {
            path = Environment.getDataDirectory();
        }
        StatFs stat = new StatFs(path.getPath());
        //判断当前系统版本号
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        } else {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        }
        return availableBlocks * blockSize;
    }


    public static Divider getDivider(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int size = DisplayUtils.dip2px(6);
            return new Api21ItemDivider(color, size, size);
        } else {
            int size = DisplayUtils.dip2px(2);
            return new Api20ItemDivider(color, size, size);
        }
    }


    /**
     * Time conversion.
     *
     * @param duration ms.
     * @return such as: {@code 00:00:00}, {@code 00:00}.
     */
    @NonNull
    public static String convertDuration(@IntRange(from = 1, to = Long.MAX_VALUE) long duration) {
        duration /= 1000;
        int hour = (int) (duration / 3600);
        int minute = (int) ((duration - hour * 3600) / 60);
        int second = (int) (duration - hour * 3600 - minute * 60);

        String hourValue = "";
        String minuteValue;
        String secondValue;
        if (hour > 0) {
            if (hour > 10) {
                hourValue = Integer.toString(hour);
            } else {
                hourValue = "0" + hour;
            }
            hourValue += ":";
        }
        if (minute > 0) {
            if (minute > 10) {
                minuteValue = Integer.toString(minute);
            } else {
                minuteValue = "0" + minute;
            }
        } else {
            minuteValue = "00";
        }
        minuteValue += ":";
        if (second > 0) {
            if (second > 10) {
                secondValue = Integer.toString(second);
            } else {
                secondValue = "0" + second;
            }
        } else {
            secondValue = "00";
        }
        return hourValue + minuteValue + secondValue;
    }
}
