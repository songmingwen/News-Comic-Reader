package com.song.sunset.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Desc:    日期工具类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/30 10:13
 */
public class DateUtil {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_FORMAT_DATE_DHM = new SimpleDateFormat("MM-dd HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_DATE_NONE = new SimpleDateFormat("yyyyMMdd");

    public static final SimpleDateFormat DATE_FORMAT_SFM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT_SFM_NONE = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat DATE_FORMAT_SSSFM_NONE = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static final SimpleDateFormat DATE_FORMAT_SFMH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");
    public static final SimpleDateFormat DATE_FORMAT_SSSFMH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    /**
     * 返回当前日期和服务器返回数据的对应值
     *
     * @return
     */
    public static int getWeek() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;

            default:
                return 1;
        }

    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    public static String getTimeSFM(long timeInMillis) {
        return getTime(timeInMillis, DATE_FORMAT_SFM);
    }

    public static String getTimeSFMNone(long timeInMillis) {
        return getTime(timeInMillis, DATE_FORMAT_SFM_NONE);
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static long formatTime(String time) {
        try {
            return DATE_FORMAT_DATE.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static long formatTimeSFM(String time) {
        try {
            return DATE_FORMAT_SFM.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将秒格式化成hh'mm'ss
     *
     * @param time
     * @return
     */
    public static String formatTime(int time) {
        StringBuilder builder = new StringBuilder();
        int h = time / 3600;
        if (h >= 0) {
            builder.append(h).append("'");
        }

        int m = (time - h * 3600) / 60;
        if (m >= 0) {
            builder.append(m).append("''");
        }

        int s = time - h * 3600 - m * 60;
        if (s >= 0) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 将秒格式化成hh:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatTimeNew(int time) {
        StringBuilder builder = new StringBuilder();
        int h = time / 3600;
        if (h >= 0) {
            builder.append(h).append(":");
        }

        int m = (time - h * 3600) / 60;
        if (m >= 0) {
            builder.append(m).append(":");
        }

        int s = time - h * 3600 - m * 60;
        if (s >= 0) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 将秒格式化成hh:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatVodTime(int time) {
        int h = time / 3600;
        int m = (time - h * 3600) / 60;
        int s = time - h * 3600 - m * 60;
        if (time >= 3600) {
            return String.format("%02d:%02d:%02d", h, m, s);
        } else {
            return String.format("%02d:%02d", m, s);
        }
    }

    public static String getCurrentTime() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = DATE_FORMAT_SFM.format(curDate);
        return str;
    }

    public static String getCurrentDateTime() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = DATE_FORMAT_DATE.format(curDate);
        return str;
    }

    /**
     * 获取开始到结束之间时间的分钟差
     *
     * @param beginTime 开始时间 精确到ms
     * @param endTime   结束时间 精确到ms
     * @return 开始到结束分钟数
     */
    public static long getDiffTimeMinutes(String beginTime, String endTime) {
        long minutes = 0;
        try {
            Date d1 = DATE_FORMAT_SFM.parse(beginTime);
            Date d2 = DATE_FORMAT_SFM.parse(endTime);
            long diff = d2.getTime() - d1.getTime();
            minutes = diff / (1000 * 60);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes;
    }

    /**
     * 获取开始到结束之间时间的秒钟差
     *
     * @param beginTime 开始时间 精确到ms
     * @param endTime   结束时间 精确到ms
     * @return 开始到结束之间秒钟数
     */
    public static long getDiffTimeSecond(String beginTime, String endTime) {
        long second = 0;
        try {
            Date d1 = DATE_FORMAT_SFM.parse(beginTime);
            Date d2 = DATE_FORMAT_SFM.parse(endTime);
            long diff = d2.getTime() - d1.getTime();
            second = diff / (1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return second;
    }

    public static String getTimeSFMH(long timeInMillis) {
        return getTime(timeInMillis, DATE_FORMAT_SFMH);
    }

    public static String getTimeSSSFMH(long timeInMillis) {
        return getTime(timeInMillis, DATE_FORMAT_SSSFMH);
    }


    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 date1
     * @param date2 date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }


    public static String getUmengPlayInfoBufferTime(long time) {
        String t = "";
        float f = (float) (time / 1000.0);
        if (f <= 5) {
            f = (float) (Math.ceil(f * 5) / 5.0);
            t = String.format("%.1f", Double.parseDouble(String.valueOf(f)));
        } else if (f <= 10) {
            f = (float) (Math.ceil(f * 2) / 2.0);
            t = String.format("%.1f", Double.parseDouble(String.valueOf(f)));
        } else if (f <= 30) {
            t = String.format("%.0f", Double.parseDouble(String.valueOf(f)));
        } else {
            t = "max";
        }
        return t;
    }

    /**
     * 传入timeformater进行时间输出
     *
     * @param formater
     * @return
     */
    public static String getCurrentTime(SimpleDateFormat formater) {
        if (formater == null) {
            formater = DATE_FORMAT_SFM;
        }
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formater.format(curDate);
        return str;
    }

    /**
     * 获取当前日期的Int值
     * add by corey 2017-05-23
     *
     * @return eg:20150523
     */
    public static int getCurrentDateToInt() {
        int result = 0;
        try {
            Date currentDate = new Date(System.currentTimeMillis());// 获取当前时间
            String str = DATE_FORMAT_DATE_NONE.format(currentDate);
            result = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 两个日期是否间隔一个星期以上
     * add by corey 2017-05-23
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isMoreThanOneWeek(int start, int end) {
        boolean result = false;
        if (end > 0 && start > 0 && end - start >= 7) {
            result = true;
        }
        return result;
    }
}
