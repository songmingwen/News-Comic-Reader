package com.hook.zhihu.demo;

/**
 * @author wsx @ Zhihu Inc.
 * @since 2019-12-25
 */
public class Hook extends SuperClass{
    public void aa(int a) {
        System.out.println("Debug-F tt println");
    }

    public void println() {
        System.out.println("Debug-F Hook println");
    }
}
