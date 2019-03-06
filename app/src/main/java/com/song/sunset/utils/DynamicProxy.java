package com.song.sunset.utils;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author songmingwen
 * @description
 * @since 2019/3/5
 */
public class DynamicProxy implements InvocationHandler {

    private Object mTarget = null;

    public Object bind(Object target) {
        this.mTarget = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.e(this.getClass().toString(), "before");
        Object result = method.invoke(mTarget, objects);
        Log.e(this.getClass().toString(), "after");
        return result;
    }
}
