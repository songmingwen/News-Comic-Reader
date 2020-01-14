package com.hook.hookdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wsx @ Zhihu Inc.
 * @since 2019/5/28
 */

public class LoggerProxy implements InvocationHandler {
    private Object target;//目标对象的引用，这里设计成Object类型，更具通用性

    public LoggerProxy(Object target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] arg) throws Throwable {

        Object result = method.invoke(target, arg);//调用目标对象的方法

        System.out.println("Before return");
        return result;
    }
}
