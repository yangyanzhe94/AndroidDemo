package com.yyz.base.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/11.
 */

public class RegexUtils {
    /**
     * 只支持中英文数字
     *
     * @param str
     * @return true 中英文数字组合  false 不符合中英文数字
     */
    public static boolean supportAll(String str) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 只支持 中文
     *
     * @param str
     * @return true 中文组合  false 非中文组合
     */
    public static boolean supportChinese(String str) {
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 只支持 数字、大写字母
     *
     * @param str
     * @return true 数字、大写字母组合  false 非数字、大写字母组合
     */
    public static boolean supportFigureCapital(String str) {
        Pattern pattern = Pattern.compile("^[A-Z0-9]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 只支持 数字、字母、中文、部分特殊字符
     *
     * @param str
     * @return true 符合条件 false 不符合
     */
    public static boolean supportSpecialCharacter(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5（!\"#$%&'\\(\\)*+,-./:;<=>?@\\[\\]^_`{|}~）\\s。，！（）、]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 只支持 中文和特殊字符
     *
     * @param str
     * @return true 符合条件 false 不符合
     */
    public static boolean supportChineseSpecialCharacter(String str) {
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5（!\"#$%&'\\(\\)*+,-./:;<=>?@\\[\\]^_`{|}~）\\s。，！（）、]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * 支持数字、字母、特殊字符
     *
     * @param str
     * @return true 符合条件 false 不符合
     */
    public static boolean isSupportChineseNumberEnglishAlphabetSpecialCharacter(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5（!\"#$%&'\\(\\)*+,-./:;<=>?@\\[\\]^_`{|}~）\\s。，！（）、]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 支持数字、字母、特殊字符
     *
     * @param str
     * @return true 符合条件 false 不符合
     */
    public static boolean isSupportEmail(String str) {
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 只支持中英文
     *
     * @param str 需要判断的字符串
     * @return true 中英文组合  false 不符合中英文数字
     */
    public static boolean supportChineseAndEnglish(String str) {
        Pattern pattern = Pattern.compile("^[A-Za-z\\u4e00-\\u9fa5]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 是否都为空
     *
     * @param str 需要判断的字符串
     * @return 都为空返回true  否则返回false
     */
    public static boolean isAllBlank(String str) {
        Pattern pattern = Pattern.compile("^[\\s]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 是否是正整数
     *
     * @param str 需要判断的字符串
     * @return 都为空返回true  否则返回false
     */
    public static boolean isPositiveInteger(String str) {
        Pattern pattern = Pattern.compile("[1-9][0-9]?");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 数字和英文
     *
     * @param str 需要判断的字符串
     * @return 都为空返回true  否则返回false
     */
    public static boolean isNumAndEnglish(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 中英文和中英文括号
     *
     * @param str 需要判断的字符串
     * @return 都为空返回true  否则返回false
     */
    public static boolean isChineseOrEnglishOrBrackets(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z\\u4e00-\\u9fa5\\(\\)（）]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 数字
     *
     * @param str 需要判断的字符串
     * @return 都为空返回true  否则返回false
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * 前四位后四位显示数字 中间显示*
     *
     * @param str
     */
    public static String replaceStringWithAsterisk(String str) {
        return str.replaceAll("(?<=\\d{0})\\d(?=\\d{4})", "*");
    }

    /**
     * 判断是不是手机号
     * @param mobile
     * @return
     */
    public static  boolean isMobilePhone(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        if (mobile.startsWith("+86")) {
            mobile = mobile.substring(3);

        } else if (mobile.startsWith("86")) {
            mobile = mobile.substring(2);
        }
        Pattern p = Pattern.compile("^[1][34578][0-9]{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
