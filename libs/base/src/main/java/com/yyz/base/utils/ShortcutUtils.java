package com.yyz.base.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 快捷方式生成工具
 * <p>
 * Created by Administrator on 2017/3/20.
 */

public class ShortcutUtils {

    /**
     * 新建快捷方式到桌面
     *
     * @param context      上下文
     * @param shortcutName 快捷方式名称
     * @param actionIntent 快捷方式操作
     * @param iconResource 快捷方式图标
     * @param allowRepeat  是否容许重复操作
     */
    public static void addShortcut(Context context, String shortcutName, Intent actionIntent, int iconResource, boolean allowRepeat) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", allowRepeat);  //是否容许重复创建
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);  //生成快捷方式名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);  //快捷方式的图标
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(shortcutIntent);
    }

    /**
     * 新建快捷方式
     *
     * @param context      上下文
     * @param shortcutName 快捷方式名称
     * @param actionIntent 快捷方式操作
     * @param icon         快捷方式图标
     * @param allowRepeat  是否容许重复操作
     */
    public static void addShortcut(Context context, String shortcutName, Intent actionIntent, Bitmap icon, boolean allowRepeat) {
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", allowRepeat);  //是否容许重复创建
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);  //生成快捷方式名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);  //快捷方式的图标
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(shortcutIntent);
    }

    /**
     * 删除桌面快捷方式
     *
     * @param context      上下文
     * @param shortcutName 快捷操作名称
     * @param actionIntent 快捷方式操作
     * @param isDuplicate  为true时循环删除快捷方式
     */
    public static void deleteShortcut(Context context, String shortcutName, Intent actionIntent, boolean isDuplicate) {
        Intent shortcutIntent = new Intent("com.android.launcher.permission.UNINSTALL_SHORTCUT");
        shortcutIntent.putExtra("duplicate", isDuplicate);  //是否循环删除
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);  //生成快捷方式名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(shortcutIntent);
    }

    /**
     * 更新快捷方式
     *
     * @param context
     * @param title
     * @param intent
     * @param bitmap
     */
    public static void updateShortcutIcon(Context context, String title, Intent intent, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        try {
            final ContentResolver cr = context.getContentResolver();
            StringBuilder uriStr = new StringBuilder();
            String urlTemp = "";
            String authority = LauncherUtils.getAuthorityFromPermissionDefault(context);
            if (authority == null || authority.trim().equals("")) {
                authority = LauncherUtils.getAuthorityFromPermission(context, LauncherUtils.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
            }
            uriStr.append("content://");
            if (TextUtils.isEmpty(authority)) {
                int sdkInt = android.os.Build.VERSION.SDK_INT;
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings");
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings");
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings");
                }
            } else {
                uriStr.append(authority);
            }
            urlTemp = uriStr.toString();
            uriStr.append("/favorites?notify=true");
            Uri uri = Uri.parse(uriStr.toString());
            Cursor c = cr.query(uri, new String[]{"_id", "title", "intent"},"title=?  and intent=? ",new String[]{title, intent.toUri(0)}, null);
            int index = -1;
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                index = c.getInt(0);//获得图标索引
                ContentValues cv = new ContentValues();
                cv.put("icon", flattenBitmap(bitmap));
                Uri uri2 = Uri.parse(urlTemp + "/favorites/" + index + "?notify=true");
                int i = context.getContentResolver().update(uri2, cv, null, null);
                context.getContentResolver().notifyChange(uri, null);//此处不能用uri2，是个坑
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static byte[] flattenBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }




    /**
     * 检查快捷方式是否存在 <br/>
     * <font color=red>注意：</font> 有些手机无法判断是否已经创建过快捷方式<br/>
     * 因此，在创建快捷方式时，请添加<br/>
     * shortcutIntent.putExtra("duplicate", false);// 不允许重复创建<br/>
     * 最好使用{@link #isShortCutExist(Context, String, Intent)}
     * 进行判断，因为可能有些应用生成的快捷方式名称是一样的的<br/>
     * 此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
     * 错误信息已捕获<br/>
     */
    public static boolean isShortCutExist(Context context, String title) {
        boolean result = false;
        try {
            final ContentResolver cr = context.getContentResolver();
            StringBuilder uriStr = new StringBuilder();
            String authority = LauncherUtils.getAuthorityFromPermissionDefault(context);
            if (authority == null || authority.trim().equals("")) {
                authority = LauncherUtils.getAuthorityFromPermission(context, LauncherUtils.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
            }
            uriStr.append("content://");
            if (TextUtils.isEmpty(authority)) {
                int sdkInt = android.os.Build.VERSION.SDK_INT;
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings");
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings");
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings");
                }
            } else {
                uriStr.append(authority);
            }
            uriStr.append("/favorites?notify=true");
            Uri uri = Uri.parse(uriStr.toString());
            Cursor c = cr.query(uri, new String[]{"title"},
                    "title=? ",
                    new String[]{title}, null);
            if (c != null && c.getCount() > 0) {
                result = true;
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br/>
     * 更多请参考{@link #isShortCutExist(Context, String)}<br/>
     * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br/>
     * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br/>
     * 此处需要在AndroidManifest.xml中配置相关的桌面权限信息<br/>
     * 错误信息已捕获<br/>
     */
    public static boolean isShortCutExist(Context context, String title, Intent intent) {
        boolean result = false;
        try {
            final ContentResolver cr = context.getContentResolver();
            StringBuilder uriStr = new StringBuilder();
            String authority = LauncherUtils.getAuthorityFromPermissionDefault(context);
            if (authority == null || authority.trim().equals("")) {
                authority = LauncherUtils.getAuthorityFromPermission(context, LauncherUtils.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
            }
            uriStr.append("content://");
            if (TextUtils.isEmpty(authority)) {
                int sdkInt = android.os.Build.VERSION.SDK_INT;
                if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                    uriStr.append("com.android.launcher.settings");
                } else if (sdkInt < 19) {// Android 4.4以下
                    uriStr.append("com.android.launcher2.settings");
                } else {// 4.4以及以上
                    uriStr.append("com.android.launcher3.settings");
                }
            } else {
                uriStr.append(authority);
            }
            uriStr.append("/favorites?notify=true");
            Uri uri = Uri.parse(uriStr.toString());
            Cursor c = cr.query(uri, new String[]{"title", "intent"},
                    "title=?  and intent=?",
                    new String[]{title, intent.toUri(0)}, null);
            if (c != null && c.getCount() > 0) {
                result = true;
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        }
        return result;
    }
}
