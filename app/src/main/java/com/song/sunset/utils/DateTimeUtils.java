package com.song.sunset.utils;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import com.song.sunset.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Song on 2016/9/20 0020.
 * Email:z53520@qq.com
 */
public class DateTimeUtils {

    private static String WEEK_NUMBER[] = {"日", "一", "二", "三","四", "五", "六"};

    public static String CURRENT_TIME = "";

    public static final long ONE_MINUTE = 60 * 1000;

    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    public static final long ONE_DAY = 24 * ONE_HOUR;

    public static final SimpleDateFormat parseFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat parseFormat2 = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm", Locale.CHINA);
    private static final SimpleDateFormat parseFormat3 = new SimpleDateFormat(
            "yyyy/MM/dd", Locale.CHINA);
    private static final SimpleDateFormat channelTimeFormat = new SimpleDateFormat(
            "MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM-dd", Locale.CHINA);

    public static final SimpleDateFormat hourMinFormat = new SimpleDateFormat(
            "HH:mm", Locale.CHINA);
    private static final SimpleDateFormat mSearchTimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat mImportantNewsTimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CHINA);

    private static final SimpleDateFormat mWonderTimeFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss", Locale.CHINA);

    static {
        parseFormat.setTimeZone(TimeZone.getTimeZone("PRC"));// 使用东8区时区
    }

    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                return 0;
//                throw new IllegalArgumentException("Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }

    private String getConstellation(int month, int day) {
        if ((month == 1 && day > 19) || (month == 2 && day < 19)) {
            return "水瓶座";
        } else if ((month == 2 && day > 18) || (month == 3 && day < 21)) {
            return "双鱼座";
        } else if ((month == 3 && day > 20) || (month == 4 && day < 20)) {
            return "白羊座";
        } else if ((month == 4 && day > 19) || (month == 5 && day < 21)) {
            return "金牛座";
        } else if ((month == 5 && day > 20) || (month == 6 && day < 22)) {
            return "双子座";
        } else if ((month == 6 && day > 21) || (month == 7 && day < 23)) {
            return "巨蟹座";
        } else if ((month == 7 && day > 22) || (month == 8 && day < 23)) {
            return "狮子座";
        } else if ((month == 8 && day > 22) || (month == 9 && day < 23)) {
            return "处女座";
        } else if ((month == 9 && day > 22) || (month == 10 && day < 24)) {
            return "天秤座";
        } else if ((month == 10 && day > 23) || (month == 11 && day < 23)) {
            return "天蝎座";
        } else if ((month == 11 && day > 22) || (month == 12 && day < 22)) {
            return "射手座";
        } else if ((month == 12 && day > 21) || (month == 1 && day < 20)) {
            return "摩羯座";
        } else {
            return "";
        }
    }

    public static Date now() {
        // get current time and time zone.
        Date mPhoneNow = new Date();
        TimeZone mPhoneZone = TimeZone.getDefault();
        // Eastern eight zones.
        TimeZone defaultZone = parseFormat.getTimeZone();
        if (mPhoneZone.getID() == defaultZone.getID()) {
            // The time zone is Eastern eight zones.
            return mPhoneNow;
        } else {
            // change curreent time to Bei Jing time.
            int timeOffset = mPhoneZone.getRawOffset()
                    - defaultZone.getRawOffset();
            return new Date(mPhoneNow.getTime() - timeOffset);
        }
    }

    /**
     *
     * @param serverTime
     * @return
     */
    public static String toBriefString(Date serverTime) {
        Date now = now();
        long timeSpan = now.getTime() - serverTime.getTime();
        if (timeSpan < 0)
            timeSpan = Integer.MAX_VALUE;

        if (timeSpan < ONE_MINUTE) {
            return "刚刚";
        } else if (timeSpan < ONE_HOUR) {
            return timeSpan / ONE_MINUTE + "分钟前";
        } else if (timeSpan < 4 * ONE_HOUR) {
            return timeSpan / ONE_HOUR + "小时前";
        } else if (DateUtils.isToday(serverTime.getTime())) {
            return "今天" + DateFormat.format("kk:mm", serverTime);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy年MM月dd日");
            return simpleDateFormat.format(serverTime);
        }
    }

    /**
     * 转化为本机日期相对服务器时间的相对间隔:<br>
     * 策略如下:
     * <ol>
     * <li>与当前日期相比小于一小时间隔的显示在XX分钟前</li>
     * <li>与当前日期相比小于4小时的间隔的显示在xx小时前</li>
     * <li>在当前日期</li>
     * <li>其它情况显示xx年xx月xx日</li>
     * </ol>
     *
     * @param time
     *            时间的字符串形式,使用JDK的Date.parse进行解析。
     * @return
     */
    public static String toBriefString(String time) {
        if (time == null)
            return "";
        return toBriefString(parseServerTime(time));
    }

    /**
     * 使用yyyy-MM-dd HH:mm:ss的format格式来进行解析<br>
     * 使用服务器标准的东八区时区
     *
     * @param dateString
     * @return
     */
    public static Date parseServerTime(String dateString) {
        try {
            return parseFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Date parseWonderTime(String dateString) {
        try {
            return mWonderTimeFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseNormalTime(String dateString) {
        try {
            String str = null;
            Date date = mWonderTimeFormat.parse(dateString);
            long timeSpan = now().getTime() - date.getTime();
            if (timeSpan < ONE_MINUTE * 2) {
                str = "刚刚";
            } else if (timeSpan >= ONE_MINUTE * 2
                    && timeSpan <= ONE_MINUTE * 15) {
                str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
            } else {
                str = channelTimeFormat.format(date);
                if (str.contains(CURRENT_TIME)) {
                    str = str.substring(str.indexOf(" ") + 1, str.length());
                } else {
                    str = str.substring(0, str.indexOf(" "));
                }
            }
            return str;
        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用yyyy-MM-dd HH:mm:ss的format格式来进行解析<br>
     * 使用服务器标准的东八区时区
     *
     * @param dateString
     * @return
     */
    public static long getTimeMillisFromServerTime(String dateString) {
        long currentTime = 0;
        Date date = parseServerTime(dateString);
        if (date != null) {
            currentTime = date.getTime();
        }
        return currentTime;
    }

    public static Date parse(String dateString, java.text.DateFormat formatter) {
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间:System.currentTimeMillis()
     *
     * @return
     */
    public static String getCurrentTime() {
        try {
            String time = String.valueOf(System.currentTimeMillis());
            return time.substring(0, time.length() - 3);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 凤凰快讯列表日期处理函数
     *
     * @param time
     * @return
     */
    public static String getCurrentTime(String time) {
        try {
            long dropVaule = System.currentTimeMillis()
                    - parseFormat.parse(time).getTime();
            String articleTime="";
            if (dropVaule >= ONE_DAY) {
                int days = (int) (dropVaule / ONE_DAY);
                if (days <= 14) {
                    articleTime = days + "天前";
                } else {
                    articleTime = "很久以前";
                }
            } else if (dropVaule >= 0) {
                int hours = (int) (dropVaule / ONE_HOUR);
                articleTime = hours + "小时前";
            }
            return articleTime;
        } catch (ParseException e) {

        }
        return "";
    }

    public static boolean isTodayTime(long beforeTime) {
        String currentTime = parseFormat.format(new Date());
        String beforTime = parseFormat.format(new Date(beforeTime));
        if (currentTime.split(" ")[0].equals(beforTime.split(" ")[0])) {
            return true;
        }
        return false;
    }

    public static boolean isTomorrowTime(long beforeTime) {

        long tomorrowTimeMillis = System.currentTimeMillis() + ONE_DAY;
        String tomorrowTimeStr = parseFormat.format(new Date(tomorrowTimeMillis));
        String beforTime = parseFormat.format(new Date(beforeTime));
        if (tomorrowTimeStr.split(" ")[0].equals(beforTime.split(" ")[0])) {
            return true;
        }
        return false;
    }

    public static long getLongByGMT(String gmtTime) throws ParseException
    {
        SimpleDateFormat Gmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",Locale.ENGLISH);
        return Gmt.parse(gmtTime).getTime();
    }

    /*
     * 今天， 明天， 或者 03-05 时：分
     */
    public static String getLiveTime(long time){
        String res = "";
        if(time<=0){
            return "";
        }
        String strTime = channelTimeFormat.format(new Date(time));
        String now = monthDayFormat.format(new Date());
        if (now.split(" ")[0].equals(strTime.split(" ")[0])){
            res += AppConfig.getApp().getString(R.string.ifeng_today);
            res += " " + strTime.split(" ")[1];
            return res;
        } else {
            long lTimeDay = 0;
            try {
                lTimeDay = mImportantNewsTimeFormat.parse(mImportantNewsTimeFormat.format(new Date())).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
            if ((lTimeDay+ONE_DAY)< time && (lTimeDay+ONE_DAY+ONE_DAY)>time ) {
                res += AppConfig.getApp().getString(R.string.ifeng_tomorrow);
                res += " " + strTime.split(" ")[1];
                return res;
            }
        }
        res += strTime;
        return res;
    }

    /**
     * @param mTimeStr 格式为：2015-05-25样式的[年-月-日]格式。
     * @return true当前日期为今天，false当前日期并非今天。如果格式错误，那么依然返回false。
     */
    public static boolean isTodayTime(String mTimeStr) {

        try {
            Date date = mImportantNewsTimeFormat.parse(mTimeStr);
            return isTodayTime(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getTimeOfList(String time) {
        String articleTime = "";
        try {
            Date date = parseFormat.parse(time);
            SimpleDateFormat newParseFormat = new SimpleDateFormat("yyyy/MM/dd");
            articleTime = newParseFormat.format(date);
        } catch (Exception e) {
            articleTime = "";
        }
        return articleTime;
    }

    public static String getTimeForVote(long time) {
        String articleTime = "";
        try {
            Date date = new Date(time);
            articleTime = new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (Exception e) {
            articleTime = "";
        }
        return articleTime;
    }

    public static String parseVideoAdLength(String mVideoLengthSecs) {

        if (TextUtils.isEmpty(mVideoLengthSecs)) {
            return "00:00";
        }
        int mTimeSecs = 0;
        try {
            mTimeSecs = Integer.valueOf(mVideoLengthSecs);
        } catch (Exception e) {

        }
        if (mTimeSecs <= 0) {
            return "00:00";
        } else {
            int minute = 0;
            int second = 0;
            StringBuilder sBuilder = new StringBuilder();
            minute = mTimeSecs / 60;
            second = mTimeSecs % 60;
            sBuilder.append(minute).append(":").append(second);
            return sBuilder.toString();
        }
    }

    public static String getTimeForDirectSeeding(String time) {
        String articleTime = "";
        try {
            Date date = parseFormat.parse(time);
            SimpleDateFormat newParseFormat = new SimpleDateFormat("HH:mm");
            articleTime = newParseFormat.format(date);
        } catch (Exception e) {
            articleTime = "";
        }
        return articleTime;
    }

    /**
     * 视频列表 视频时长转化
     */
    public static String parseDuration(int duration) {
        long temp = duration * 1000;
        return parseDuration(temp);
    }

    public static String parseDuration(long duration) {
        SimpleDateFormat formater = new SimpleDateFormat("mm:ss");;
        if(duration >= 3600 *1000){
            formater = new SimpleDateFormat("hh:mm:ss");
            int seconds = (int)duration / 1000;
            int hours = seconds / 3600;
            seconds = seconds % 3600;
            int mins = seconds / 60;
            seconds = seconds % 60;
            Date date = new Date();
            date.setHours(hours);
            date.setMinutes(mins);
            date.setSeconds(seconds);
            return formater.format(date);
        }else{
            return formater.format(duration);
        }
    }
    /**
     * 视频相关列表后台获取数据为hh:mm:ss格式字符串，当视频长度超过60分钟时，后台提供的数据没有进位，
     * 而是显示成如下形式：00:80:13，此函数将该字符串格式的时间转化为正确显示hh:mm:ss的格式，上
     * 例中时间最终显示为01:20:13
     *
     * @param duration
     * @return 正确格式的hh:mm:ss时间字符串
     */
    public static String parseDuration(String duration){
        int index1 , index2;
        long durationLong = 0;
        try{
            index1 = duration.indexOf(":");
            index2 = duration.indexOf(":", index1 + 1);
            int hh = Integer.parseInt(duration.substring(0, index1));
            int mm = Integer.parseInt(duration.substring(index1 + 1, index2));
            int ss = Integer.parseInt(duration.substring(index2 + 1));
            durationLong = hh * 3600 + mm * 60 + ss;
        }catch(NullPointerException e){
            e.printStackTrace();
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return parseDuration(durationLong * 1000);
    }

    public static String formatDateTIme(String time) {
        String articleTime = "";
        try {
            Date date = parseFormat.parse(time);
            SimpleDateFormat newParseFormat = new SimpleDateFormat(
                    "yyyy/MM/dd  HH:mm");
            articleTime = newParseFormat.format(date);
        } catch (Exception e) {
            articleTime = "";
        }
        return articleTime;
    }

    /**
     * 获取给定日期date的前nAdvance天。
     *
     * @param date     给定的日期；
     * @param nAdvance 前nAdvance天。
     * @return 前nAdvance天的字符串，格式如2015-05-25.
     */
    public static String getDateParams(Date date, int nAdvance) {
        long timeMillis = date.getTime();
        long mAdvanceTimeMillis = timeMillis - nAdvance * 24 * 60 *60 * 1000;
        return mImportantNewsTimeFormat.format(new Date(mAdvanceTimeMillis));
    }

    /**
     * 图文直播日期处理
     *
     * @param time
     * @return
     */
    public static String getPhotoTextTime(String time) {
        String articleTime = "";
        try {
            Date date = parseFormat.parse(time);
            SimpleDateFormat newParseFormat = null;
            if (isTodayTime(time)) {
                newParseFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            } else {
                newParseFormat = new SimpleDateFormat("MM月dd日 HH:mm",
                        Locale.CHINA);
            }
            articleTime = newParseFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            articleTime = "";
        }
        return articleTime;
    }

    public static long getCurrentHourAndMin() {
        long currentTime = -1L;
        try {
            currentTime = hourMinFormat.parse(hourMinFormat.format(new Date()))
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            currentTime = -1L;
        }
        return currentTime;
    }

    public static long getCurrentHourAndMin(String time) {
        long currentTime = -1L;
        try {
            currentTime = hourMinFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            currentTime = -1L;
        }
        return currentTime;
    }

    public static long getSecond(String timeString) {
        if (!TextUtils.isEmpty(timeString)) {
            String[] times = timeString.split(":");
            if (times.length == 3) {
                String hourStr = times[0];
                String minStr = times[1];
                String secStr = times[2];

                return (Long.parseLong(hourStr) * 60 * 60)
                        + (Long.parseLong(minStr) * 60)
                        + Long.parseLong(secStr);
            }
        }
        return 0;
    }

    public static String getTimeForSportLive(String time) {
        String str = null;

        try {
            Date date = new Date(Long.valueOf(time) * 1000);
            str = hourMinFormat.format(date);
        } catch (Exception e) {
            return str;
        }

        return str;
    }

    /**
     * 550 列表时间调整，如果为当天显示HH:MM
     * 否则不显示时间
     * @param time
     * @return
     */
    public static String getTimeForNewChannel(String time) {
        String str = null;
        try {

            Date date = parseFormat3.parse(time);
            if(parseFormat3.format(date).equals(parseFormat3.format(new Date(System.currentTimeMillis())))){//格式化为相同格式
                str = hourMinFormat.format(parseFormat2.parse(time));
            }else {
                str = null;
            }

        } catch (Exception e) {
            return str;
        }

        return str;
    }

    public static String getTimeForRecommendNews(String time) {
        String str = "";
        try {
            Date date = parseFormat2.parse(time);

            long timeSpan = now().getTime() - date.getTime();
            if (timeSpan < ONE_MINUTE * 2) {
                str = "刚刚";
            } else if (timeSpan >= ONE_MINUTE * 2
                    && timeSpan < ONE_HOUR) {
                str = String.valueOf(timeSpan / ONE_MINUTE) + "分钟前";
            } else if (timeSpan >= ONE_HOUR
                    && timeSpan <= ONE_HOUR * 24) {
                str = String.valueOf(timeSpan / ONE_HOUR) + "小时前";
            } else {
                str = channelTimeFormat.format(date);
                if (str.contains(CURRENT_TIME)) {
                    str = str.substring(str.indexOf(" ") + 1, str.length());
                } else {
                    str = str.substring(0, str.indexOf(" "));
                }
            }

        } catch (Exception e) {
            return str;
        }

        return str;
    }




    public static String getTimeForSubChannel(String time) {
        String str = null;
        try {
            Date date = parseFormat.parse(time);
            long timeSpan = now().getTime() - date.getTime();
            if (timeSpan < ONE_MINUTE * 2) {
                str = "刚刚";
            } else if (timeSpan >= ONE_MINUTE * 2
                    && timeSpan <= ONE_MINUTE * 15) {
                str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
            } else {
                str = channelTimeFormat.format(date);
                if (str.contains(CURRENT_TIME)) {
                    str = str.substring(str.indexOf(" ") + 1, str.length());
                } else {
                    str = str.substring(0, str.indexOf(" "));
                }
            }

        } catch (Exception e) {
            return str;
        }

        return str;
    }


    public static String getTimeForVideoChannel(String time) {
        String str = "";
        try {
            Date date = mSearchTimeFormat.parse(time);
            long timeSpan = now().getTime() - date.getTime();
            if (timeSpan < ONE_MINUTE * 2) {
                str = "刚刚";
            } else if (timeSpan >= ONE_MINUTE * 2
                    && timeSpan <= ONE_MINUTE * 15) {
                str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
            } else {
                str = channelTimeFormat.format(date);
                if (str.contains(CURRENT_TIME)) {
                    str = str.substring(str.indexOf(" ") + 1, str.length());
                } else {
                    str = str.substring(0, str.indexOf(" "));
                }
            }

        } catch (Exception e) {
            return str;
        }

        return str;
    }

    public static String getTimeForSearchChannel(String time) {
        String str = null;
        try {
            Date date = parseFormat2.parse(time);
            long timeSpan = now().getTime() - date.getTime();
            if (timeSpan < ONE_MINUTE * 2) {
                str = "刚刚";
            } else if (timeSpan >= ONE_MINUTE * 2
                    && timeSpan <= ONE_MINUTE * 15) {
                str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
            } else {
                str = mSearchTimeFormat.format(date);
            }
        } catch (Exception e) {
            return str;
        }
        return str;
    }

    public static String getTimeForDetailRelated(String time) {
        String str = "";
        try {
            Date date = parseFormat.parse(time);
            long timeSpan = now().getTime() - date.getTime();
            if (timeSpan < ONE_MINUTE * 2) {
                str = "刚刚";
            } else if (timeSpan >= ONE_MINUTE * 2
                    && timeSpan <= ONE_MINUTE * 15) {
                str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
            } else {
                str = monthDayFormat.format(date);
            }
        } catch (Exception e) {
            str = "";
        }
        return str;
    }


    /**
     * 通过日期来判断 通透部分的 消息日期。
     *
     * */
    public static String initTimeByDate(Date mDate) {
        return "刚刚";
    }

    /**
     * 将以s为单位的时间翻译成时分秒的格式；
     * 例如,输入： 30，输出： 00：30；
     * <p>
     * 输入：-1，输出：00：00；
     * <p>
     * 输入：90, 输出：01:30;
     * <p>
     * 输入：3600 + 20*60 + 25，输出： 1:20:25.
     */
    public static String parseTimeSecsToHMS(int mTotalTimeSecs){

        mTotalTimeSecs = Math.max(0, mTotalTimeSecs);
        int hours = mTotalTimeSecs / 3600;
        int mins = (mTotalTimeSecs % 3600) / 60;
        int secs = (mTotalTimeSecs % 3600) % 60;
        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(String.format("%02d", hours)).append(":");
        }
        if (mins >= 0) {
            sb.append(String.format("%02d", mins)).append(":");
        }
        if (secs >= 0) {
            sb.append(String.format("%02d", secs));
        }
        return sb.toString();
    }
    public static String parseForArith(String time){
        String str = null;
        try {
            Date date = parseFormat2.parse(time);
            if(isTodayTime(date.getTime())){
                long timeSpan = now().getTime() - date.getTime();
                if (timeSpan < ONE_HOUR) {
                    str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
                } else if (timeSpan >= ONE_HOUR
                        && timeSpan <= ONE_HOUR * 12) {
                    str = String.valueOf(timeSpan / ONE_HOUR)+"小时前";
                }else{
                    str = "12小时前";
                }
            }else{
                long timeSpan = now().getTime() - date.getTime();
                if (timeSpan < ONE_HOUR) {
                    str = String.valueOf(timeSpan / ONE_MINUTE)+"分钟前";
                } else if (timeSpan >= ONE_HOUR
                        && timeSpan <= ONE_HOUR * 6) {
                    str = String.valueOf(timeSpan / ONE_HOUR)+"小时前";
                }else{
                    str = channelTimeFormat.format(date);
                    if (str.contains(CURRENT_TIME)) {
                        str = str.substring(str.indexOf(" ") + 1, str.length());
                    } else {
                        str = str.substring(0, str.indexOf(" "));
                    }
                }
            }
        }catch (Exception e) {
            str = "";
        }
        return str;
    }

    /**
     * 我的订阅列表日期处理函数
     *
     * @param time
     * @return
     */
    public static String getMySubTime(String time) {
        try {
            long dropVaule = System.currentTimeMillis()
                    - Long.parseLong(time)*1000;
            String articleTime="";
            if (dropVaule >= ONE_DAY) {
                Date date = new Date(Long.parseLong(time)*1000);
                articleTime = parseFormat3.format(date);
            } else if (dropVaule >= 0) {
                int hours = (int) (dropVaule / ONE_HOUR);
                if(hours>=1){
                    articleTime = hours + "小时前";
                }else{
                    articleTime = dropVaule/ONE_MINUTE + "分钟前";
                }

            }
            return articleTime;
        } catch (Exception e) {
            Log.d("hht","==格式化错误");
        }
        return "";
    }

    /**
     * mUpdateTime格式为： "2015-02-20 12:50:23"
     *
     * @return 24小时热文url中date参数的格式："2015-02-20"
     */
    public static String initImportantNewsDateBy(String mUpdateTime) {
        if (null != mUpdateTime) {
            try {
                Date mDate = parseFormat2.parse(mUpdateTime);
                return mImportantNewsTimeFormat.format(mDate);
            } catch (Exception e) {

            }
        }
        return "";
    }

    public static String getTimelineTagFrom(String mUpdateTime) {
        if (!TextUtils.isEmpty(mUpdateTime)) {
            try {
                Date mDate = parseFormat2.parse(mUpdateTime);
                return new SimpleDateFormat("HH").format(mDate) + ":00";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static String parseRefreshDate(String mStrDate) {
        try{
            Date mDate = mImportantNewsTimeFormat.parse(mStrDate);
            Calendar calendar = Calendar.getInstance();
            if (mDate != null) {
                calendar.setTime(mDate);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            return new StringBuilder().append(year)
                    .append("-").append(month)
                    .append("-").append(day)
                    .append(" 周").append(WEEK_NUMBER[week])
                    .toString();
        }catch(Exception e){

        }
        return "";
    }

    public static String parseImportantNewsDeshDate(String mStrDate) {
        try{
            Date mDate = mImportantNewsTimeFormat.parse(mStrDate);
            Calendar calendar = Calendar.getInstance();
            if (mDate != null) {
                calendar.setTime(mDate);
            }
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            StringBuilder sb = new StringBuilder();
            if(isTodayTime(mDate.getTime())){
                sb.append("今天");
            }else{
                sb.append(month)
                        .append("月").append(day).append("日");
            }
            sb.append(" 周").append(WEEK_NUMBER[week]);
            return sb.toString();
        }catch(Exception e){

        }
        return "";
    }

    /**
     * mDateTime格式为： "2015-02-20"
     *
     * @return 24小时热文日期选择页面的日期格式："2/20"
     */
    public static String parseImportantShowingName(String mDateTime){
        try{
            Date mDate = mImportantNewsTimeFormat.parse(mDateTime);
            Calendar calendar = Calendar.getInstance();
            if (mDate != null) {
                calendar.setTime(mDate);
            }
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new StringBuilder().append(month).append("/").append(day).toString();
        }catch(Exception e){

        }
        return "";
    }

    /**
     * mDateTime格式为： "2015-02-20"
     *
     * @return 24小时热文日期选择页面的日期格式："2/20"
     */
    public static String parseWeekName(String mDateTime){
        try{
            if (isTodayTime(mDateTime)) {
                return "今天";
            } else {
                Date mDate = mImportantNewsTimeFormat.parse(mDateTime);
                Calendar calendar = Calendar.getInstance();
                if (mDate != null) {
                    calendar.setTime(mDate);
                }
                int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                if(week >=0 && week < WEEK_NUMBER.length){
                    return"周"+WEEK_NUMBER[week];
                }
            }
        }catch(Exception e){

        }
        return "";
    }

    public static String getChannelTimeFormat(String time){
        String str = null;
        try {
            Date date = parseFormat2.parse(time);
            str = channelTimeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 用于比较存储的时间是否和当前时间为同一天
     * @param key sp中存储的key值
     * @return true 代表同一天
     */
    public static boolean isTheSameDay(Context context, String key){
        long lastTime= SPUtils.getLongByName(context, key, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String lastDate = sdf.format(new Date(lastTime));
        String currDate = sdf.format(new Date(System.currentTimeMillis()));
        Log.d("isTheSameDay"," key : "+key+" lastDate : "+lastDate+" - "+currDate);
        if(lastDate.equals(currDate)) {
            return true;
        }
        return false;
    }

    /**
     * 将毫秒数转换为 xxhxx'xx" 格式
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 xxhxx'xx"样式
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        StringBuffer sb = new StringBuffer();
        if (days > 0) {
            if (days < 10 ) {
                sb.append("0");
            }
            sb.append(days + "d");
        }
        if (hours > 0) {
            if (hours < 10 ) {
                sb.append("0");
            }
            sb.append(hours + "h");
        }
        if (minutes > 0) {
            if (minutes < 10 ) {
                sb.append("0");
            }
            sb.append(minutes + "'");
        } else {
            if (hours > 0 && minutes <= 0) {
                sb.append("00'");
            }
        }
        if (seconds > 0) {
            if (seconds < 10 ) {
                sb.append("0");
            }
            sb.append(seconds + "\"");
        } else {
            if (minutes > 0 && seconds <= 0) {
                sb.append("00\"");
            }
        }
        return  sb.toString();
    }
    public static String gethhmmTime(String time) {
        String str = null;

        try {
            Date date = parseFormat.parse(time);
            str = hourMinFormat.format(date);
        } catch (Exception e) {
            return str;
        }

        return str;
    }
    public static long getTimeStamp(String time){
        long ll = 0;

        try {
            Date date = parseFormat.parse(time);
            ll = date.getTime();
        } catch (Exception e) {
            return ll;
        }

        return ll;
    }

    public static String formatTimeByMinSec(int timeSecs) {

        StringBuilder resultBuilder = new StringBuilder();
        long nHours = timeSecs / (60 * 60);
        long nMinutes = (timeSecs % (60 * 60)) / (60);
        long nSeconds = timeSecs % 60;

        if (nHours > 0) {
            if (nHours < 10) {
                resultBuilder.append("0");
            }
            resultBuilder.append(nHours).append(":");
        }

        if (nMinutes < 10) {
            resultBuilder.append("0");
        }
        resultBuilder.append(nMinutes).append(":");

        if (nSeconds < 10) {
            resultBuilder.append("0");
        }
        resultBuilder.append(nSeconds);
        return resultBuilder.toString();
    }
}
