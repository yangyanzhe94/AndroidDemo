package com.yyz.base.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;

/**
 * @author
 * @ClassName: SettingHelper
 * @Description: Shared Preferences读写数据
 */
public final class SettingHelper {
    public final static String TAG = "SettingHelper";
    private final static String DATA_NAME = "daodao.dat";
    private final static String SPLITCHAR = ",";

    public enum SettingField {

        /**
         * 当前version code
         * int
         * 可用于新特性
         */
        VERSION_CODE,

        /**
         * token
         * String
         */
        ACCESS_TOKEN,
        /**
         * 刷新token
         * String
         */
        ACCESS_TOKEN_REFRESH,
        /**
         * token 有效期
         * <p>
         * Long  token 有效时间，存储进行处理，存储的 tokenTime *1000 + LoginTime
         */
        ACCESS_TOKEN_TIME,

        /**
         * 登录方式
         */
        LOGIN_TYPE,

        /**
         * 登录状态
         * <p>
         * boolean
         */
        LOGIN_SUCCESS,
        /**
         * IM 账户
         * String
         */
        IM_NUMBER,

        /**
         * IM 密码
         * String
         */
        IM_PASSWORD,

        /**
         * IM 登录状态
         * boolean
         */
        IM_LOGIN_STATE,


        /**
         * userId 用户id
         * int
         */
        USER_ID,

        /**
         * 用户手机
         * String
         */
        USER_PHONE,
    }


    public static int getInt(Context context, SettingField field, int defaultValue) {
        return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getInt(field.name(), defaultValue);
    }

    public static void setInt(Context context, SettingField field, int value) {
        Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(field.name(), value);
        editor.commit();
    }

    public static String getString(Context context, SettingField field, String defaultValue) {
        return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
    }

    public static void setString(Context context, SettingField field, String value) {
        Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(field.name(), value);
        editor.commit();
    }

    public static long getLong(Context context, SettingField field, long defaultValue) {
        return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getLong(field.name(), defaultValue);
    }

    public static void setLong(Context context, SettingField field, long value) {
        Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(field.name(), value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, SettingField field, boolean defaultValue) {
        return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getBoolean(field.name(), defaultValue);
    }

    public static void setBoolean(Context context, SettingField field, boolean value) {
        Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(field.name(), value);
        editor.commit();
    }

    public static void setArraylong(Context context, SettingField field, ArrayList<Long> arrayLong) {
        if (arrayLong != null && arrayLong.size() > 0) {
            String str = "";
            for (int index = 0, count = arrayLong.size(); index < count; ++index) {
                str = str + arrayLong.get(index).toString() + SPLITCHAR;
            }
            Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(field.name(), str);
            editor.commit();
        }
    }

    public static void removeKey(Context context, SettingField field) {
        Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(field.name());
        editor.commit();
    }

    public static ArrayList<Long> getArrayLong(Context context, SettingField field, String defaultValue) {
        ArrayList<Long> arrayLong = new ArrayList<Long>();
        String str = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
        if (str != null) {
            String[] ids = str.split(SPLITCHAR);
            for (int index = 0; index < ids.length; ++index) {
                long id = Long.parseLong(ids[index]);
                arrayLong.add(id);
            }
        }
        return arrayLong;
    }

    public static void setArrayString(Context context, SettingField field, ArrayList<String> displayList) {
        String str = "";
        Integer num = 0;
        for (int index = 0, count = displayList.size(); index < count; ++index) {
            str = str + displayList.get(index) + SPLITCHAR;
            num++;
        }
        Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(field.name(), str);
        editor.commit();
    }

    public static ArrayList<String> getArrayString(Context context, SettingField field, String defaultValue) {
        ArrayList<String> displayList = new ArrayList<String>();
        String str = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).getString(field.name(), defaultValue);
        if (str != null) {
            String[] ids = str.split(SPLITCHAR);
            for (int index = 0; index < ids.length; index++) {
                if (!ids[index].equals(""))
                    displayList.add(ids[index]);
            }
        }
        return displayList;
    }

}
