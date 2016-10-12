package com.song.sunset.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Song on 2016/9/20 0020.
 * Email:z53520@qq.com
 */
public class DateTimeUtils {

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
}
