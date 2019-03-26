package com.yyz.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间工具类
 * Created by Administrator on 2016/1/11.
 */
public final class DateUtils {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy年MM月");
    private final static SimpleDateFormat sdfYearMonthEn = new SimpleDateFormat("yyyy-MM");
    private final static SimpleDateFormat sdfYearMonthPoint = new SimpleDateFormat("yyyy.MM");
    private final static SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfHourMinute = new SimpleDateFormat("HH:mm");
    private final static SimpleDateFormat sdfYMDHM = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
    private final static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    private final static SimpleDateFormat MMdd = new SimpleDateFormat("MM-dd");
    private final static SimpleDateFormat MMddHHmm = new SimpleDateFormat("MM-dd HH:mm");

    /**
     * 获取当时间时间戳
     *
     * @return
     */
    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取GMT 时间
     *
     * @return
     */
    public static String getGMTTime() {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dff.format(new Date());
    }

    /**
     * 获取 GMT
     *
     * @return
     */
    public static String getGMT() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true, true, tz.getRawOffset());
    }

    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

    /**
     * 返回聊天列表的时间
     * 当天用时分（18：00）展示，过去用月日（10-13）展示
     *
     * @param date
     * @return
     */
    public static String getChatListTime(Date date) {
        String time = "";
        if (date != null) {
            if (isToday(date)) {
                time = getHHmm(date);
            } else {
                time = getMMdd(date);
            }
        }

        return time;
    }

    /**
     * 返回聊天列表的时间
     * 当天用时分（18：00）展示，过去用月、日、时、分（10-13 18：00）展示。
     *
     * @param date
     * @return
     */
    public static String getChatTime(Date date) {
        String time = "";
        if (date != null) {
            if (isToday(date)) {
                time = getHHmm(date);
            } else {
                time = getMMddHHmm(date);
            }
        }

        return time;
    }


    /**
     * 时间格式化成：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getyyyyMMddHHmmss(Long time) {
        return yyyyMMddHHmmss.format(time);
    }

    /**
     * 判断时间是不同一天
     *
     * @param time
     * @return
     */
    public static String getTimestampString(long time) {
        long nowTime = getTimeMillis();
        String var1 = "";
        if (nowTime - time > 86400000) {
            var1 = "MM-dd HH:mm";
        } else {
            var1 = "HH:mm";
        }

        return (new SimpleDateFormat(var1, Locale.ENGLISH)).format(time);
    }

    /**
     * 时间格式化成 yyyy-MM-dd HH:mm
     *
     * @param timeMillis
     * @return
     */
    public static String getYYYYMMDDHHmm(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdf.format(date);
        return time;
    }

    /**
     * 获取MMddHHmm
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return MMddHHmm
     */
    public static String getMMddHHmm(String date) {
        if (!StringUtils.isEmpty(date)) {
            try {
                return MMddHHmm.format(new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                return date;
            }
        }
        return date;
    }


    /**
     * 获取MMddHHmm
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return MMddHHmm
     */
    public static String getMMddHHmm(Date date) {
        String time = "";
        if (date != null) {
            time = MMddHHmm.format(date);
        }
        return time;
    }


    /**
     * 获取年月日
     *
     * @param date
     * @return
     */

    public static String getyMD(String date) {
        if (StringUtils.isNotEmpty(date)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").format(new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                return date;
            }
        }
        return date;
    }

    /**
     * 获取
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static String getMMdd(String date) {
        if (StringUtils.isNotEmpty(date))
            try {
                return MMdd.format(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                return date;
            }
        return date;
    }

    /**
     * 获取
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static String getMMdd(Date date) {
        return MMdd.format(date);
    }

    /**
     * 获取毫秒值
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static long getLongyMd(String date) {
        if (!StringUtils.isEmpty(date))
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        return 0;
    }

    /**
     * 获取毫秒值
     *
     * @param timeMillis yyyy.MM
     * @return
     */
    public static String getStringYM(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdfYearMonthPoint.format(date);
        return time;
    }

    /**
     * 获取毫秒值
     *
     * @param date 时间格式:yyyy-MM-dd HH:mm:ss
     * @return 时间毫秒值
     */
    public static long getLong(String date) {
        if (!StringUtils.isEmpty(date))
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        return 0;
    }

    /**
     * yyyy年MM月
     *
     * @param timeMillis
     * @return
     */
    public static String getYYMMCn(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdfYearMonth.format(date);
        return time;
    }

    /**
     * yyyy-MM
     *
     * @param timeMillis
     * @return
     */
    public static String getYYMMEn(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdfYearMonthEn.format(date);
        return time;
    }

    /**
     * yyyy-MM-dd
     *
     * @param timeMillis
     * @return
     */
    public static String getYYYYMMDD(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdfYearMonthDay.format(date);
        return time;
    }

    /**
     * HH:mm
     *
     * @param timeMillis
     * @return
     */
    public static String getHHmm(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdfHourMinute.format(date);
        return time;
    }

    /**
     * HH:mm
     *
     * @param date
     * @return
     */
    public static String getHHmm(Date date) {
        String time = "";
        if (date != null) {
            time = sdfHourMinute.format(date);
        }
        return time;
    }

    /**
     * 返回年号 yyyy
     *
     * @return
     */
    public static String getCurrentYear() {
        Date date = new Date(System.currentTimeMillis());
        String time = sdfYear.format(date);
        return time;
    }

    /**
     * 2014年1月11日 14时20分
     * yyyy年MM月 HH时mm分
     *
     * @return
     */
    public static String getYMDHM(long timeMillis) {
        if (timeMillis <= 0) {
            return "";
        }
        Date date = new Date(timeMillis);
        String time = sdfYMDHM.format(date);
        return time;
    }

    /**
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 相差的年数(endTime - beginTime)。如果beginTime > endTime，则返回相差年数的负值
     * @Title: getIntervalYear
     * @Description: 计算两个时间相差的年数
     */
    public static int getIntervalYear(long beginTime, long endTime) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(beginTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(endTime);
        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    }

    public static int getIntervalDay(long beginTime, long endTime) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(beginTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(endTime);
        return cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @return
     */
    public static int hoursBetween(long beginTime, long endTime) {
        long time = endTime - beginTime;
        long between_days = time / (1000 * 3600);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param time1 the first date(UTC milliseconds)
     * @param time2 the second date(UTC milliseconds)
     * @return true
     * @Title: isSampleDay
     * @Description: 是否是同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if two dates are on the same day ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if two calendars represent the same day ignoring time.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
                .get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * <p>
     * Checks if a date is today.
     * </p>
     *
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    /**
     * <p>
     * Checks if a calendar date is today.
     * </p>
     *
     * @param cal the calendar, not altered, not null
     * @return true if cal date is today
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    /**
     * <p>
     * Checks if the first date is before the second date ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if the first date day is before the second date day.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isBeforeDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isBeforeDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if the first calendar date is before the second calendar date
     * ignoring time.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null.
     * @param cal2 the second calendar, not altered, not null.
     * @return true if cal1 date is before cal2 date ignoring time.
     * @throws IllegalArgumentException if either of the calendars are
     *                                  <code>null</code>
     */
    public static boolean isBeforeDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
            return true;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
            return false;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
            return true;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
            return false;
        return cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p>
     * Checks if the first date is after the second date ignoring time.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if the first date day is after the second date day.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isAfterDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isAfterDay(cal1, cal2);
    }

    /**
     * <p>
     * Checks if the first calendar date is after the second calendar date
     * ignoring time.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null.
     * @param cal2 the second calendar, not altered, not null.
     * @return true if cal1 date is after cal2 date ignoring time.
     * @throws IllegalArgumentException if either of the calendars are
     *                                  <code>null</code>
     */
    public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
            return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
            return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
            return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
            return true;
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * <p>
     * Checks if a date is after today and within a number of days in the
     * future.
     * </p>
     *
     * @param date the date to check, not altered, not null.
     * @param days the number of days.
     * @return true if the date day is after today and within days in the future
     * .
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isWithinDaysFuture(Date date, int days) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isWithinDaysFuture(cal, days);
    }

    /**
     * <p>
     * Checks if a calendar date is after today and within a number of days in
     * the future.
     * </p>
     *
     * @param cal  the calendar, not altered, not null
     * @param days the number of days.
     * @return true if the calendar date day is after today and within days in
     * the future .
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isWithinDaysFuture(Calendar cal, int days) {
        if (cal == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar today = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.add(Calendar.DAY_OF_YEAR, days);
        return (isAfterDay(cal, today) && !isAfterDay(cal, future));
    }

    /**
     * Returns the given date with the time set to the start of the day.
     */
    public static Date getStart(Date date) {
        return clearTime(date);
    }

    /**
     * Returns the given date with the time values cleared.
     */
    public static Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Determines whether or not a date has any time values (hour, minute,
     * seconds or millisecondsReturns the given date with the time values
     * cleared.
     */

    /**
     * Determines whether or not a date has any time values.
     *
     * @param date The date.
     * @return true iff the date is not null and any of the date's hour, minute,
     * seconds or millisecond values are greater than zero.
     */
    public static boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.HOUR_OF_DAY) > 0) {
            return true;
        }
        if (c.get(Calendar.MINUTE) > 0) {
            return true;
        }
        if (c.get(Calendar.SECOND) > 0) {
            return true;
        }
        if (c.get(Calendar.MILLISECOND) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the given date with time set to the end of the day
     */
    public static Date getEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * Returns the maximum of two dates. A null date is treated as being less
     * than any non-null date.
     */
    public static Date max(Date d1, Date d2) {
        if (d1 == null && d2 == null)
            return null;
        if (d1 == null)
            return d2;
        if (d2 == null)
            return d1;
        return (d1.after(d2)) ? d1 : d2;
    }

    /**
     * Returns the minimum of two dates. A null date is treated as being greater
     * than any non-null date.
     */
    public static Date min(Date d1, Date d2) {
        if (d1 == null && d2 == null)
            return null;
        if (d1 == null)
            return d2;
        if (d2 == null)
            return d1;
        return (d1.before(d2)) ? d1 : d2;
    }

    /**
     * The maximum date possible.
     */
    public static Date MAX_DATE = new Date(Long.MAX_VALUE);

}
