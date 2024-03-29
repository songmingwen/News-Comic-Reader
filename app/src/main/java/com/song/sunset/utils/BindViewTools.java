package com.song.sunset.utils;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author songmingwen
 * @description
 * @since 2020/3/17
 */
public class BindViewTools {

    public static void bind(Activity activity) {

        Class clazz = activity.getClass();
        try {
            Class bindViewClass = Class.forName(clazz.getName() + "_ViewBinding");
            Method method = bindViewClass.getMethod("bind", activity.getClass());
            method.invoke(bindViewClass.newInstance(), activity);
        } catch (ClassNotFoundException | IllegalAccessException |
                InstantiationException | NoSuchMethodException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
