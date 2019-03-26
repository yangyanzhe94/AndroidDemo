package com.yyz.base.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.yyz.base.utils.CloseUtils;
import com.yyz.base.utils.FileUtils;
import com.yyz.base.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * with():指定了声明周期
 * load():加载资源，String/Uri/File/Integer/URL/byte[]/T,或者 loadFromMediaStore(Uri uri)
 * placeholder(resourceId/drawable)： 设置资源加载过程中的占位Drawable。
 * error()：load失败时显示的Drawable。
 * crossFade()/crossFade(int duration)：imageView改变时的动画，version 3.6.1后默认开启300毫秒
 * dontAnimate()：移除所有的动画。
 * override() ：调整图片大小
 * centerCrop()：图片裁剪，ImageView 可能会完全填充，但图像可能不会完整显示。
 * fitCenter()： 图像裁剪，图像将会完全显示，但可能不会填满整个 ImageView。
 * animate(): 指定加载动画。
 * transform():图片转换。
 * bitmapTransform(): bitmap转换，不可以和(centerCrop() 或 fitCenter())共用。
 * priority(Priority priority):当前线程的优先级,Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL(default)，Priority.LOW
 * thumbnail(): 缩略图.
 * listener():异常监听
 * preload(int width, int height): 预加载resource到缓存中（单位为pixel）
 * fallback(Drawable drawable):设置model为空时显示的Drawable。
 * using() ：为单个的请求指定一个 model
 * asGif()：Gif 检查，如果是图片且加了判断，则会显示error占位图，否则会显示图片
 * asBitmap()：bitmap转化，如果是gif，则会显示第一帧
 * Glide 可以以load(File)的形式播放本地视频，但是如果需要播放网络视屏，则要用VideoView
 * skipMemoryCache(true)一张图片变化很快,需要禁止内存缓存
 * diskCacheStrategy(DiskCacheStrategy.NONE)即使关闭内存缓存，请求图片将会仍然被存储在设备的磁盘缓存中，如果一张图片变化很快,仍需要禁止磁盘缓存
 * DiskCacheStrategy.NONE //什么都不缓存
 * DiskCacheStrategy.SOURCE //仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
 * DiskCacheStrategy.RESULT //仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
 * DiskCacheStrategy.ALL //缓存所有版本的图像（默认行为）
 */

public class ImageManager {
    private ImageManager() {
    }

    private final static ImageManager mInstance = new ImageManager();

    public final static ImageManager getInstance() {
        return mInstance;

    }

    //TODO       Glide 加载图片

    /**
     * 加载静态图片
     *
     * @param context   上下文
     * @param imageView view
     * @param imageUrl  图片地址
     */
    @NonNull
    public void imageStatic(Context context, ImageView imageView, String imageUrl) {
        if (context != null && imageUrl != null && !TextUtils.isEmpty(imageUrl))
            Glide.with(context).load(imageUrl).asBitmap().format(DecodeFormat.PREFER_RGB_565).into(imageView);
    }

    /**
     * 加载静态圆角图片
     * @param context
     * @param imageView
     * @param imageUrl
     * @param radius
     */
    @NonNull
    public void imageStatic(Context context, ImageView imageView, String imageUrl, int radius) {
        if (context != null && imageUrl != null && !TextUtils.isEmpty(imageUrl))
            Glide.with(context).load(imageUrl).bitmapTransform(new RoundedCornersTransformation(context, radius, 0,RoundedCornersTransformation.CornerType.ALL)).into(imageView);
    }

    /**
     * 加载本地资源文件图片
     *
     * @param context
     * @param imageView
     * @param resId
     */
    public void imageStatic(Context context, ImageView imageView, @DrawableRes int resId) {
        if (context != null && resId != 0)
            Glide.with(context).load(resId).asBitmap().format(DecodeFormat.PREFER_RGB_565).into(imageView);
    }

    /**
     * 加载静态图片
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param imageUrl    图片地址
     * @param placeholder 加载中图片
     * @param error       加载失败图片
     */
    public void imageStatic(Context context, ImageView imageView, String imageUrl, int placeholder, int error) {
        if (context != null && imageView != null && imageUrl != null) {
            Glide.with(context).load(imageUrl).asBitmap().format(DecodeFormat.PREFER_RGB_565).placeholder(placeholder).error(error).into(imageView);
        }
    }

    /**
     * 加载gif 图片
     *
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void imageGif(Context context, ImageView imageView, String imageUrl) {
        imageGif(context, imageView, imageUrl, android.R.mipmap.sym_def_app_icon, android.R.mipmap.sym_def_app_icon);
    }

    /**
     * 加载gif图片
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param imageUrl    图片地址
     * @param placeholder 加载中图片
     * @param error       加载失败图片
     */
    public void imageGif(Context context, ImageView imageView, String imageUrl, int placeholder, int error) {
        if (context != null && imageView != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).asGif().placeholder(placeholder).error(error).into(imageView);
        }
    }

    /**
     * 先加载缩略图 然后在加载全图
     *
     * @param context   上下文
     * @param imageView 图片控件
     * @param imageUrl  图片地址
     */
    public void imageThumbnail(Context context, ImageView imageView, String imageUrl) {
        imageThumbnail(context, imageView, imageUrl, android.R.mipmap.sym_def_app_icon, android.R.mipmap.sym_def_app_icon);
    }

    /**
     * 先加载缩略图 然后在加载全图
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param imageUrl    图片地址
     * @param placeholder 加载中图片
     * @param error       加载失败图片
     */
    public void imageThumbnail(Context context, ImageView imageView, String imageUrl, int placeholder, int error) {
        if (context != null && imageView != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).thumbnail(0.1f).placeholder(placeholder).error(error).into(imageView);
        }
    }


    /**
     * 加载图片动画
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param imageUrl    图片地址
     * @param placeholder 加载中图片
     * @param error       加载失败图片
     * @param animType    动画 1、默认无动画  2、淡入淡出 3、自定义动画
     * @param animRes     自定义动画Res
     */
    public void imageAnim(Context context, ImageView imageView, String imageUrl, int placeholder, int error, int animType, @RawRes int animRes) {
        if (context != null && imageView != null && !TextUtils.isEmpty(imageUrl)) {
            switch (animType) {
                case 1:
                    Glide.with(context).load(imageUrl).dontAnimate().placeholder(placeholder).error(error).into(imageView);
                    break;
                case 2:
                    Glide.with(context).load(imageUrl).crossFade().placeholder(placeholder).error(error).into(imageView);
                    break;
                case 3:
                    Glide.with(context).load(imageUrl).animate(animRes).placeholder(placeholder).error(error).into(imageView);
                    break;
            }

        }
    }

    /**
     * 高斯模糊图片
     *
     * @param context     上下文
     * @param imageView   图片控件
     * @param imageUrl    图片地址
     * @param placeholder 加载中图片
     * @param error       加载错误图片
     * @param radius      模糊值1-25  值越大，越模糊
     */
    public void imageTransForm(Context context, ImageView imageView, String imageUrl, int placeholder, int error, int radius) {
        if (context != null && imageView != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).bitmapTransform(new BlurTransformation(context, radius)).placeholder(placeholder).error(error).into(imageView);
        }
    }


    //TODO  保存图片

    /**
     * 保存图片
     *
     * @param bitmap   图片源
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return
     */
    public boolean saveImage(Bitmap bitmap, String filePath, Bitmap.CompressFormat format) {
        return saveImage(bitmap, FileUtils.getFileByPath(filePath), format, false);
    }

    /**
     * 保存图片
     *
     * @param bitmap 图片源
     * @param file   要保存到的文件
     * @param format 格式
     * @return
     */
    public boolean saveImage(Bitmap bitmap, File file, Bitmap.CompressFormat format) {
        return saveImage(bitmap, file, format, false);
    }

    /**
     * 保存图片
     *
     * @param bitmap   源图片
     * @param filePath 保存到的路径
     * @param format   格式
     * @param recycle  是否回收
     * @return
     */
    public boolean saveImage(Bitmap bitmap, String filePath, Bitmap.CompressFormat format, boolean recycle) {
        return saveImage(bitmap, FileUtils.getFileByPath(filePath), format, recycle);
    }

    /**
     * 保存图片
     *
     * @param bitmap  源图片
     * @param file    保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return
     */
    public boolean saveImage(Bitmap bitmap, File file, Bitmap.CompressFormat format, boolean recycle) {
        if (isEmptyBitmap(bitmap) || !FileUtils.createOrExistsFile(file)) return false;
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = bitmap.compress(format, 100, os);
            if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(os);
        }
        return ret;
    }

    /**
     * bitmap 是否为空
     *
     * @param bitmap
     * @return
     */
    private static boolean isEmptyBitmap(Bitmap bitmap) {
        return bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0;
    }


    //TODO       压缩

    /**
     * 按缩放压缩
     *
     * @param bitmap    源图片
     * @param newWidth  新高
     * @param newHeight 新宽
     * @return
     */
    public Bitmap compressByScale(Bitmap bitmap, int newWidth, int newHeight) {
        return scale(bitmap, newWidth, newHeight, false);
    }

    /**
     * 按缩放压缩
     *
     * @param bitmap    源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放压缩后的图片
     */
    public Bitmap compressByScale(Bitmap bitmap, int newWidth, int newHeight, boolean recycle) {
        return scale(bitmap, newWidth, newHeight, recycle);
    }


    /**
     * 按缩放压缩
     *
     * @param bitmap      源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放压缩后的图片
     */
    public Bitmap compressByScale(Bitmap bitmap, float scaleWidth, float scaleHeight) {
        return scale(bitmap, scaleWidth, scaleHeight, false);
    }

    /**
     * 按缩放压缩
     *
     * @param bitmap      源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放压缩后的图片
     */
    public Bitmap compressByScale(Bitmap bitmap, float scaleWidth, float scaleHeight, boolean recycle) {
        return scale(bitmap, scaleWidth, scaleHeight, recycle);
    }

    /**
     * 按质量压缩
     *
     * @param bitmap  源图片
     * @param quality 质量
     * @return 质量压缩后的图片
     */
    public Bitmap compressByQuality(Bitmap bitmap, int quality) {
        return compressByQuality(bitmap, quality, false);
    }

    /**
     * 按质量压缩
     *
     * @param bitmap  源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public Bitmap compressByQuality(Bitmap bitmap, int quality, boolean recycle) {
        if (isEmptyBitmap(bitmap) || quality < 0 || quality > 100) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按质量压缩
     *
     * @param bitmap      源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    public Bitmap compressByQuality(Bitmap bitmap, long maxByteSize) {
        return compressByQuality(bitmap, maxByteSize, false);
    }

    /**
     * 按质量压缩
     *
     * @param bitmap      源图片
     * @param maxByteSize 允许最大值字节数 200
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public Bitmap compressByQuality(Bitmap bitmap, long maxByteSize, boolean recycle) {
        if (isEmptyBitmap(bitmap) || maxByteSize <= 0) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length > maxByteSize && quality >= 0) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality -= 5, baos);
        }
        if (quality < 0) return null;
        byte[] bytes = baos.toByteArray();
        if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按采样大小压缩
     *
     * @param bitmap     源图片
     * @param sampleSize 采样率大小  100
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap bitmap, int sampleSize) {
        return compressBySampleSize(bitmap, sampleSize, false);
    }

    /**
     * 按采样大小压缩
     *
     * @param bitmap     源图片
     * @param sampleSize 采样率大小  100
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap bitmap, int sampleSize, boolean recycle) {
        if (isEmptyBitmap(bitmap)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    //TODO   缩略图

    /**
     * 缩放图片
     *
     * @param bitmap    源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap bitmap, int newWidth, int newHeight) {
        return scale(bitmap, newWidth, newHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param bitmap    源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap bitmap, int newWidth, int newHeight, boolean recycle) {
        if (isEmptyBitmap(bitmap)) return null;
        Bitmap ret = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        return ret;
    }

    /**
     * 缩放图片
     *
     * @param bitmap      源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap bitmap, float scaleWidth, float scaleHeight) {
        return scale(bitmap, scaleWidth, scaleHeight, false);
    }

    /**
     * 缩放图片
     *
     * @param bitmap      源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放后的图片
     */
    public static Bitmap scale(Bitmap bitmap, float scaleWidth, float scaleHeight, boolean recycle) {
        if (isEmptyBitmap(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        return ret;
    }


    //TODO 获取Bitmap

    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(is);
        }
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap getBitmap(File file, int maxWidth, int maxHeight) {
        if (file == null) return null;
        InputStream is = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            is = new BufferedInputStream(new FileInputStream(file));
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(is);
        }
    }

    /**
     * 获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public Bitmap getBitmap(String filePath) {
        if (StringUtils.isSpace(filePath)) return null;
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 获取bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap getBitmap(String filePath, int maxWidth, int maxHeight) {
        if (StringUtils.isSpace(filePath)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public Bitmap getBitmap(InputStream is) {
        if (is == null) return null;
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 获取bitmap
     *
     * @param is        输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight) {
        if (is == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 获取bitmap
     *
     * @param data   数据
     * @param offset 偏移量
     * @return bitmap
     */
    public Bitmap getBitmap(byte[] data, int offset) {
        if (data.length == 0) return null;
        return BitmapFactory.decodeByteArray(data, offset, data.length);
    }

    /**
     * 获取bitmap
     *
     * @param data      数据
     * @param offset    偏移量
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap getBitmap(byte[] data, int offset, int maxWidth, int maxHeight) {
        if (data.length == 0) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, offset, data.length, options);
    }

    /**
     * 获取bitmap
     *
     * @param res 资源对象
     * @param id  资源id
     * @return bitmap
     */
    public Bitmap getBitmap(Resources res, int id) {
        if (res == null) return null;
        return BitmapFactory.decodeResource(res, id);
    }

    /**
     * 获取bitmap
     *
     * @param res       资源对象
     * @param id        资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap getBitmap(Resources res, int id, int maxWidth, int maxHeight) {
        if (res == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    /**
     * 获取bitmap
     *
     * @param fd 文件描述
     * @return bitmap
     */
    public Bitmap getBitmap(FileDescriptor fd) {
        if (fd == null) return null;
        return BitmapFactory.decodeFileDescriptor(fd);
    }

    /**
     * 获取bitmap
     *
     * @param fd        文件描述
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap getBitmap(FileDescriptor fd, int maxWidth, int maxHeight) {
        if (fd == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }


    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        if (maxWidth == 0 || maxHeight == 0) return 1;
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((height >>= 1) >= maxHeight && (width >>= 1) >= maxWidth) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }
}
