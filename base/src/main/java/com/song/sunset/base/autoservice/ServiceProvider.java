package com.song.sunset.base.autoservice;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java8.util.Optional;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/13
 */
public class ServiceProvider {

    private static final String TAG = "ServiceProvider";

    public static <T> Optional<T> optional(Class<T> ofClass) {
        return Optional.ofNullable(get(ofClass));
    }

    public static <T> T get(Class<T> clazz) {
        List<T> services = loadService(clazz);
        if (services.size() == 1) {
            return services.get(0);
        }
        return null;
    }

    public static <T> List<T> loadService(Class<T> clazz) {
        long start = System.currentTimeMillis();
        List<T> list = new ArrayList<>();
        try {
            ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
            for (T t : serviceLoader) {
                list.add(t);
            }
        } catch (ServiceConfigurationError e) {
            Log.e(TAG, "loadService: ", e);
        }
        long end = System.currentTimeMillis();
        Log.i(TAG, "load time = " + (end - start));
        return Collections.unmodifiableList(list);
    }
}
