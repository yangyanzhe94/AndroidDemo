package com.yyz.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 图表工具类
 * Created by Administrator on 2016/1/11.
 */
public final class GraphicUtils {
    private final static String TAG = "GraphicUtils";

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDefaultDisplay(context).getWidth();
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDefaultDisplay(context).getHeight();
    }

    public static Display getDefaultDisplay(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay();
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static Bitmap createRoundImage(Context context, Bitmap originalImage, Bitmap mask) {
        RectF clipRect = new RectF();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Bitmap roundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        canvas.drawBitmap(originalImage, 0, 0, null);
        clipRect.set(0, 0, width, height);
        canvas.drawBitmap(mask, null, clipRect, null);
        return roundBitmap;
    }

    public static Bitmap createRoundImage(Context context, Bitmap originalImage, int dp) {
        final int CONNER = dip2px(context, dp);
        RectF clipRect = new RectF();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Bitmap roundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Path path = new Path();
        clipRect.set(0, 0, width, height);
        path.addRoundRect(clipRect, CONNER, CONNER, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(originalImage, null, clipRect, null);
        return roundBitmap;
    }

    public static Bitmap createReflectedImage(Context context, Bitmap originalImage, Bitmap mask) {
        final int reflectionGap = 1;
        final int CONNER = dip2px(context, 5);
        Path path = new Path();
        RectF clipRect = new RectF();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap
                .createBitmap(originalImage, 0, height * 3 / 4, width, height / 4, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 4), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        clipRect.set(0, 0, width, height);
        canvas.drawBitmap(mask, null, clipRect, null);

        canvas.save();
        clipRect.set(0, height, width, (height + height / 4 + CONNER) + reflectionGap);
        path.addRoundRect(clipRect, CONNER, CONNER, Path.Direction.CCW);
        canvas.clipPath(path);

        Paint point = new Paint();
        point.setColor(Color.LTGRAY);
        canvas.drawRect(clipRect, point);
        canvas.restore();

        canvas.save();
        clipRect.set(1, height + 1, width - 1, (height + height / 4 + CONNER) + reflectionGap);
        path.reset();
        path.addRoundRect(clipRect, CONNER, CONNER, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        canvas.restore();

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, height, 0, bitmapWithReflection.getHeight() + reflectionGap,
                0x50000000, 0x00000000, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public final static int LOCATION_LEFT_TOP = 0;
    public final static int LOCATION_BOTTOM_RIGHT = 1;

    public static final Point getViewPosition(View view, int type) {
        int[] locations = new int[2];
        view.getLocationInWindow(locations);
        Point point = new Point(locations[0], locations[1]);
        if (type == LOCATION_LEFT_TOP)
            return point;
        if (type == LOCATION_BOTTOM_RIGHT) {
            int width = view.getWidth();
            int height = view.getHeight();
            point.x += width;
            point.y += height;
            return point;
        }
        return new Point(0, 0);
    }

    public static Bitmap getBitmapByBitmap(Bitmap backBitmap, Bitmap mask, int x, int y) {
        Bitmap output = Bitmap.createBitmap(backBitmap.getWidth(), backBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawBitmap(mask, x, y, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(backBitmap, rect, rect, paint);
        return output;
    }

    public final static boolean pointInView(Point point, View view) {
        Point left_top = GraphicUtils.getViewPosition(view, GraphicUtils.LOCATION_LEFT_TOP);
        Point bottom_right = GraphicUtils.getViewPosition(view, GraphicUtils.LOCATION_BOTTOM_RIGHT);
        return (point.x >= left_top.x && point.x <= bottom_right.x && point.y >= left_top.y && point.y <= bottom_right.y);
    }

    // 图形配比
    public static Bitmap getImageScale(Bitmap bitmap, int width, int height) {
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        float scaleWidth = ((float) width) / srcWidth;
        float scaleHeight = ((float) height) / srcHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap destbmp = Bitmap.createBitmap(bitmap, 0, 0, srcWidth, srcHeight, matrix, true);
        return destbmp;
    }

    public final static Bitmap getImageScaleMessage(Context context, Bitmap bmp, int width, int height) {
        if (bmp == null) {
            return null;
        }
        int widthMax = GraphicUtils.dip2px(context, width);
        int heightMax = GraphicUtils.dip2px(context, height);
        Bitmap resultBmp = null;

        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;

            int sampleSize = 1;
            option.inJustDecodeBounds = false;
            int widthRatio = option.outWidth / widthMax;
            int heightRatio = option.outHeight / heightMax;
            if (widthRatio > 1 || heightRatio > 1) {
                sampleSize = Math.max(widthRatio, heightRatio);
                option.inSampleSize = sampleSize;
            }
            int newWidth = bmp.getWidth();
            int newHeight = bmp.getHeight();
            if (newWidth <= widthMax && newHeight <= heightMax) {
                return bmp;
            }

            float scaleWidth = ((float) widthMax) / newWidth;
            float scaleHeight = ((float) heightMax) / newHeight;
            float scale = Math.min(scaleWidth, scaleHeight);

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            resultBmp = Bitmap.createBitmap(bmp, 0, 0, newWidth, newHeight, matrix, true);

            if (bmp != null) {
                bmp.recycle();
            }
        } catch (Exception e) {
            return null;
        }
        return resultBmp;
    }

    /**
     * @param context
     * @param uri
     * @param width
     * @param height
     * @return Bitmap
     * @Title: getImageThumbnail
     * @Description: 得到缩略图(单位是像素值)
     */
    public final static Bitmap getImageThumbnail(Context context, Uri uri, int width, int height) {
        if (uri == null) {
            return null;
        }
        return getImageThumbnail(context, uri.getPath(), width, height);
    }

    /**
     * 创建缩略图
     *
     * @param context  上下文
     * @param bitmap   原Bitmap
     * @param dpWidth  缩略图的宽度
     * @param dpHeight 缩略图的高度
     * @return 返回缩略图 Bitmap
     * @Title: getImageThumbnail
     * @Description: TODO
     */
    public final static Bitmap getImageThumbnail(Context context, Bitmap bitmap, int dpWidth, int dpHeight) {
        Bitmap resultBmp = null;
        int widthMax = GraphicUtils.dip2px(context, dpWidth);
        int heightMax = GraphicUtils.dip2px(context, dpHeight);

        int newWidth = bitmap.getWidth();
        int newHeight = bitmap.getHeight();
        if (newWidth <= widthMax && newHeight <= heightMax) {
            return bitmap;
        }
        float scaleWidth = ((float) widthMax) / newWidth;
        float scaleHeight = ((float) heightMax) / newHeight;
        float scale = Math.min(scaleWidth, scaleHeight);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        resultBmp = Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true);

        if (bitmap != null) {
            bitmap.recycle();
        }
        return resultBmp;
    }


    /**
     * 得到缩略图，指定缩略图  宽高
     *
     * @param context
     * @param filePath
     * @param width
     * @param height
     * @return Bitmap
     * @Title: getImageThumbnail
     * @Description: 得到缩略图(单位是dp值)
     */
    @Deprecated
    public final static Bitmap getImageThumbnail(Context context, String filePath, int width, int height) {
        int widthMax = GraphicUtils.dip2px(context, width);
        int heightMax = GraphicUtils.dip2px(context, height);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        if (widthMax > 0 && heightMax > 0) {
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            // 计算图片缩放比例
            final int minSideLength = Math.min(widthMax, heightMax);
            opts.inSampleSize = computeSampleSize(opts, minSideLength, widthMax * heightMax);
        } else {
            opts.inSampleSize = 4;
        }
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inInputShareable = true;
        opts.inPurgeable = true;

        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        Bitmap bmp = null;
        if (fs != null) {
            try {
                bmp = BitmapFactory.decodeStream(fs, null, opts);
            } catch (OutOfMemoryError e) {
                System.gc();
            } finally {
                try {
                    fs.close();
                } catch (IOException e) {
                }
            }
        }
        if (bmp == null) {
            System.gc();
        }
        return bmp;
    }

    public final static Bitmap getImageThumbnailByDip(Context context, String filePath, int width, int height) {
        int widthMax = GraphicUtils.dip2px(context, width);
        int heightMax = GraphicUtils.dip2px(context, height);
        return getImageThumbnailByPixel(context, filePath, widthMax, heightMax);
    }

    public final static Bitmap getImageThumbnailByPixel(Context context, String filePath, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        if (width > 0 && height > 0) {
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            // 计算图片缩放比例
            final int minSideLength = Math.min(width, height);
            opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
        } else {
            opts.inSampleSize = 4;
        }
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inInputShareable = true;
        opts.inPurgeable = true;
        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        Bitmap bmp = null;
        if (fs != null) {
            try {
                bmp = BitmapFactory.decodeStream(fs, null, opts);
            } catch (OutOfMemoryError e) {
                System.gc();
            } finally {
                try {
                    fs.close();
                } catch (IOException e) {
                }
            }
        }
        if (bmp == null) {
            System.gc();
        }
        return bmp;
    }


    public final static int AT_TOP = 0;
    public final static int AT_BOTTOM = 1;
    public final static int OTHER = 2;

    public static final int PULL_TO_REFRESH = 3;
    public static final int RELEASE_TO_REFRESH = 4;
    public static final int REFRESHING = 5;
    public static final int FRESH_OTHER = 6;

    /**
     * @param bmpOriginal
     * @return Bitmap
     * @Title: toGrayscale
     * @Description: 灰度图
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);

        return bmpGrayscale;
    }

    /**
     * @param color
     * @return int
     * @Title: getColor
     * @Description: 得到RGB颜色值
     */
    public static int getColor(int color) {
        return Color.rgb(color >> 16, (color & 0x00ffff) >> 8, color & 0x0000ff);
    }

    /**
     * @param context
     * @param uri
     * @return Bitmap
     * @Title: getImage
     * @Description: 加载图片
     */
    public final static Bitmap getImage(Context context, Uri uri) {
        Bitmap resultBmp = null;
        try {
            String path = uri.getPath();
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            Bitmap bmp;
            option.inJustDecodeBounds = false;
            option.inSampleSize = 2;
            bmp = BitmapFactory.decodeFile(path, option);
            if (bmp == null) {
                bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, option);
            }
            resultBmp = bmp;
            return resultBmp;
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            System.gc();
            return null;
        }
    }

    /**
     * @param imagePaths   要拼接的图片路径
     * @param format       文件格式(jpg、png)
     * @param quality      压缩质量
     * @param destFileName 拼接后图片文件名
     * @return boolean
     * @Title: combineImage
     * @Description: 图片拼接(将多张图片拼接成一个图片)
     */
    public static boolean combineImage(String[] imagePaths, Bitmap.CompressFormat format, int quality, String destFileName) {
        boolean result = false;
        int outHeight = 0;
        int outWidth = 0;
        ArrayList<Bitmap> srcBitmapList = new ArrayList<Bitmap>(imagePaths.length);
        // 计算拼接后图片的高度
        for (int index = 0; index < imagePaths.length; ++index) {
            Bitmap srcBitmap = BitmapFactory.decodeFile(imagePaths[index]);
            if (srcBitmap.getWidth() > outWidth) {
                outWidth = srcBitmap.getWidth();
            }
            outHeight += srcBitmap.getHeight();
            srcBitmapList.add(srcBitmap);
        }
        // 创建拼接结果的缩略图
        Bitmap destBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(destBitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        // 对位图进行滤波处理
        paint.setFilterBitmap(true);

        int top = 0;
        for (int index = 0; index < imagePaths.length; ++index) {
            Bitmap srcBitmap = srcBitmapList.get(index);
            Rect srcRect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
            Rect destRect = new Rect();
            destRect.set(0, top, srcBitmap.getWidth(), srcBitmap.getHeight() + top);
            // 把图片srcBit上srcRect画到destRect的区域内
            canvas.drawBitmap(srcBitmap, srcRect, destRect, paint);
            top += srcBitmap.getHeight();
            srcBitmap.recycle();
        }
        srcBitmapList.clear();
        File destFile = new File(destFileName);
        if (destFile != null) {
            // 经过图像变换之后的Bitmap里的数据可以保存到图像压缩文件里（JPG/PNG）。
            // 参数format可设置JPEG或PNG格式；
            // quality可选择压缩质量；fOut是输出流（OutputStream）
            result = saveBitmapFile(destBitmap, format, quality, destFileName);
        }
        return result;
    }

    /**
     * @param bitmap
     * @param format       图片格式
     * @param quality      压缩质量
     * @param destFileName 文件名
     * @return boolean true:成功
     * @Title: saveBitmapFile
     * @Description: 保存bitmap到磁盘文件
     */
    public static boolean saveBitmapFile(Bitmap bitmap, Bitmap.CompressFormat format, int quality, String destFileName) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(destFileName);
            bitmap.compress(format, quality, fos);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return false;
    }


    /**
     * 高斯模糊
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {

        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /**
     * 先压缩后高斯模糊，避免图片过大出现OOM
     *
     * @param resouce
     * @param radius           处理效果程度
     * @param canReuseInBitmap
     * @param scale            对原图进行缩小的比例，缩小后进行高斯，提高效率
     * @return
     */
    public static Bitmap doBlurWithScale(Bitmap resouce, int radius, boolean canReuseInBitmap, float scale) {

        //radius = (int) (radius / scale);

        //可以尝试吧这里的图片质量改为 Bitmap.Config.RGB_565  或者 Bitmap.Config.ARGB_4444 高斯图片质量要求不高
        //TODO 这种压缩方法会使本身图片质量就很差的图片特别难看，可以更换压缩方法
        Bitmap overlay = Bitmap.createBitmap((int) (resouce.getWidth() / scale), (int) (resouce.getHeight() / scale), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(overlay);
        // canvas.translate(-mLauncherView.getLeft()/scale, -mLauncherView.getTop()/scale);
        canvas.scale(1 / scale, 1 / scale);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);

        canvas.drawBitmap(resouce, 0, 0, paint);

        overlay = doBlur(overlay, (int) radius, canReuseInBitmap);

        return overlay;
    }


    /**
     * 压缩图片获取bitmap
     *
     * @param scrPath
     */
    public static Bitmap compressImage(String scrPath) {
        File file = new File(scrPath);
        Bitmap bitmap;
        if (file.length() / 1024 < 100) {
            bitmap = BitmapFactory.decodeFile(scrPath);
        } else {
            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inPurgeable = true;
            bitmap = BitmapFactory.decodeFile(scrPath, bitmapFactoryOptions);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        return bitmap;
    }


    /**
     * 获取存储路径
     *
     * @param pContext   上下文
     * @param folderName 图片存储文件夹名
     * @return
     */
    public static String getSDCardPath(Context pContext, String folderName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/daodao/" + folderName;
            File PathDir = new File(path);
            if (!PathDir.exists()) {
                PathDir.mkdirs();
            }
            return path;
        } else {
            return pContext.getCacheDir().getAbsolutePath();
        }
    }


    /**
     * 通过Uri返回File文件
     * 注意：通过相机的是类似content://media/external/images/media/97596
     * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_20150423_161955.jpg
     * 通过查询获取实际的地址
     *
     * @param uri
     * @return
     */
    public static File getFileByUri(Context context, Uri uri) {
        String path = null;
        if (uri != null && "file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后 (managedQuery方法被废弃)
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            if (path != null) {
                return new File(path);
            }
        } else {
            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

    /**
     * by lifengmei
     * 旋转图片
     *
     * @param angle  需要旋转的角度
     * @param bitmap 要被旋转的bitmap
     * @return Bitmap 返回旋转过后的bitmap
     */
    public static Bitmap rotaingBitmap(int angle, Bitmap bitmap) {
        if (angle > 0) {

            //旋转图片 动作
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return resizedBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * by lifengmei
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 取得压缩比例
     *
     * @param afterWidth   要压缩到的宽
     * @param afterHeight  要压缩到的高
     * @param beforeWidth  压缩前图片的宽
     * @param beforeHeight 压缩前图片的高
     * @return
     */
    private static int getRatioSize(int afterWidth, int afterHeight, int beforeWidth, int beforeHeight) {
        // 缩放比默认为1
        int ratio = 1;
        if (beforeHeight > afterHeight || beforeWidth > afterWidth) {
            final int heightRatio = Math.round((float) beforeHeight / (float) afterHeight);
            final int widthRatio = Math.round((float) beforeWidth / (float) afterWidth);
            ratio = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        // 最小比率为1
        if (ratio <= 0)
            ratio = 1;
        return ratio;
    }

    /**
     * by yyz
     * Get bitmap from specified image path
     * 压缩图片至固定比例
     *
     * @param imgPath
     * @param width   压缩后的宽
     * @param height  压缩后的高
     * @return
     */
    private static Bitmap getBitmap(String imgPath, int width, int height) {
        Bitmap bitmap = null;
        File file = new File(imgPath);
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        try {
            //取得图片宽高
            newOpts.inJustDecodeBounds = true;//为true的时候表示只读边,不读内容
            BitmapFactory.decodeFile(imgPath, newOpts);
            newOpts.inSampleSize = getRatioSize(width, height, newOpts.outWidth, newOpts.outHeight);

            //压缩图片
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//如果使用ARGB-8888连续压缩两个10M图分分钟oom，而使用4444比565还糊。。
            newOpts.inJustDecodeBounds = false;
            newOpts.inPurgeable = true;// 同时设置才会有效
            newOpts.inInputShareable = true;//当系统内存不够时候图片自动被回收
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        } catch (OutOfMemoryError outOfMemoryError) {//如果3M还oom异常，那么
            newOpts.inSampleSize = newOpts.inSampleSize + 1;
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        }

        int degree = readPictureDegree(imgPath);//将图片转正
        return rotaingBitmap(degree, bitmap);
    }


    /**
     * by lifengmei
     * Get bitmap from specified image path
     * by lifengmei,根据路径获取图片，若图太大就比例压缩到5M以内
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        Bitmap bitmap = null;
        File file = new File(imgPath);
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        try {
            newOpts.inJustDecodeBounds = false;//为true的时候表示只读边,不读内容
            newOpts.inPurgeable = true;// 同时设置才会有效
            newOpts.inInputShareable = true;//当系统内存不够时候图片自动被回收
            // Do not compress
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//如果使用ARGB-8888连续压缩两个10M图分分钟oom，而使用4444比565还糊。。
            if (file.length() / 1024 > 3 * 1024) {
                int a = 2;
                while (file.length() / 1024 / a > 3 * 1024) {
                    a = a + 1;
                }
                newOpts.inSampleSize = a;
            } else {
                newOpts.inSampleSize = 1;
            }
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        } catch (OutOfMemoryError outOfMemoryError) {//如果3M还oom异常，那么
            newOpts.inSampleSize = newOpts.inSampleSize + 1;
            bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        }
        int degree = readPictureDegree(imgPath);//将图片转正
        return rotaingBitmap(degree, bitmap);
    }

    /**
     * by lifengmei
     * Compress by quality,  and generate image to the path specified
     * by lifengmei 质量压缩，bitmap压缩到指定路径
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.PNG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize && options > 0) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 5;
            image.compress(Bitmap.CompressFormat.PNG, options, os);//使用JPEG保存图片时背景为黑色，这时只需要改成用png保存就可以了，bitmap.compress(CompressFormat.PNG, 100, fos);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
        os.close();
        image.recycle();
        System.gc();

    }

    /**
     * by lifengmei
     * Compress by quality,  and generate image to the path specified
     * by lifengmei 质量压缩，指定路径的图片压缩到指定路径
     *
     * @param imgPath
     * @param outPath
     * @param maxSize     target will be compressed to be smaller than this size.(kb)
     * @param needsDelete Whether delete original file after compress
     * @throws IOException
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 压缩图片按照固定比例
     *
     * @param imgPath     源图片地址
     * @param outPath     输出图片地址
     * @param maxSize     最大图片大小
     * @param needsDelete 是否需要删除源图片
     * @throws IOException
     */
    public static void compressImage(String imgPath, String outPath, int maxSize, int width, int height, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath, width, height), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }


    /**
     * by lifengmei
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (filePath != null) {

            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }

    }

    /**
     * 直接根据图片文件地址拿到流，全部质量压缩，带尝试，这样真的好吗，此法失败
     * @param imgPath
     * @param outPath
     * @param maxSize
     * @throws IOException
     */
//    public static void compressImage(String imgPath, String outPath, int maxSize) throws IOException {
//        try{
//            File file = new File(imgPath);
//            FileInputStream fis = new FileInputStream(file);
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = fis.read(b)) != -1)
//            {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            int options = 100;
//
//            //--
////            BitmapFactory.Options opts = new BitmapFactory.Options();
////            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
////            opts.inJustDecodeBounds = true;
////            Bitmap bmp = BitmapFactory.decodeFile(imgPath, opts);
////            bmp.compress(Bitmap.CompressFormat.JPEG, options, bos);
//            //---
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(), 0,100);
//            while ( bos.toByteArray().length / 1024 > maxSize) {
//                // Clean up os
//                bos.reset();
//                // interval 10
//                options -= 10;
////                bmp.compress(Bitmap.CompressFormat.JPEG, options, bos);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
//            }
//
//            // Generate compressed image file
//            FileOutputStream fos = new FileOutputStream(outPath);
//            fos.write(bos.toByteArray());
//            fos.flush();
//            fos.close();
//            bos.close();
//            bmp.recycle();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 功能：
     * 1、将本地图片压缩得到Bitmap，防止oom
     * 2、对图片进行旋转。
     * <p>
     * 获取图pain方式：
     * 1、当图片小于100K 直接转化Bitmap
     * 2、从图片数据库读取图片，得到图片的缩略图Bitmap
     * 3、当图片数据库无法获取，则采用先按照图片尺寸，在按照图片质量压缩得到Bitmap
     *
     * @param c    上下文
     * @param file 图片的路径
     * @return
     */
    @SuppressLint({"NewApi", "InlinedApi"})
    public static Bitmap getxtsldraw(Context c, String file) {
        File f = new File(file);
        Bitmap drawable = null;
        if (f.length() / 1024 < 100) {
            drawable = BitmapFactory.decodeFile(file);
        } else {
            Cursor cursor = c.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID},
                    MediaStore.Images.Media.DATA + " like ?", new String[]{"%" + file}, null);
            if (cursor == null || cursor.getCount() == 0) {
                drawable = getbitmap(file, 480 * 800);
            } else {
                if (cursor.moveToFirst()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                    long videoIdLong = Long.parseLong(videoId);
                    Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(c.getContentResolver(), videoIdLong, MediaStore.Images.Thumbnails.MINI_KIND, options);
                    return bitmap;
                } else {
                    // drawable = BitmapFactory.decodeResource(c.getResources(),
                    // R.drawable.ic_doctor);
                }
            }
        }
        int degree = 0;
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(file);

            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            if (degree != 0 && drawable != null) {
                Matrix m = new Matrix();
                m.setRotate(degree, (float) drawable.getWidth() / 2,
                        (float) drawable.getHeight() / 2);
                drawable = Bitmap.createBitmap(drawable, 0, 0,
                        drawable.getWidth(), drawable.getHeight(), m, true);
            }
        } catch (OutOfMemoryError e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    /**
     * 压缩图片根据尺寸，计算压缩比例
     *
     * @param imageFile
     * @param length
     * @return
     */
    public static Bitmap getbitmap(String imageFile, int length) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opts.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imageFile, opts);
        int ins = computeSampleSize(opts, -1, length);
        opts.inSampleSize = ins;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inJustDecodeBounds = false;
        try {
            bmp = BitmapFactory.decodeFile(imageFile, opts);
            return bmp;
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bmp;
    }

    /**
     * 计算压缩尺寸
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
