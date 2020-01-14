package com.hook.hookdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class MyClass {
    public static void main(String[] agrus) {
        Test1 test1 = new Test1();
        dynamicHook(test1);
        test1.println();
    }



    private static void staticHook(Object hookObject) {
        try {
            Class<?> testClass = Class.forName("com.hook.zhihu.hookdemo.Test1");

            Field field = testClass.getDeclaredField("mSuperClass");
            field.setAccessible(true);
            PrintInerface proxy = new StaticHookClass();
            field.set(hookObject, proxy);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private static void dynamicHook(Object hookObject) {
        try {
            Class<?> testClass = Class.forName("com.hook.zhihu.hookdemo.Test1");

            Field field = testClass.getDeclaredField("mSuperClass");
            field.setAccessible(true);
            PrintInerface proxy = (PrintInerface) Proxy.newProxyInstance(
                    Test1.class.getClassLoader(), new Class[]{PrintInerface.class}
                    , new LoggerProxy(new SuperClass()));
            field.set(hookObject, proxy);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
