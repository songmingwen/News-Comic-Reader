package com.hook.wsx.hookManager;

import java.lang.reflect.Method;

/**
 * @author wsx @ Zhihu Inc.
 * @since 2019/12/25
 */
public class OrangeHook {

    public static void m1() {
    }
    public static void m2() {
    }

    static {
        System.loadLibrary("orange_hook");
        measure_method_size();
    }
    private Method srcMethod;
    private Method hookMethod;


    public OrangeHook(Method src, Method dest) {
        srcMethod = src;
        hookMethod = dest;
    }

    public void hook() {
        System.out.println("Debug-F before");
        hook_native(srcMethod, hookMethod);
        System.out.println("Debug-F after");
    }

    private static native long measure_method_size();

    private static native long hook_native(Method src, Method dest);
}
