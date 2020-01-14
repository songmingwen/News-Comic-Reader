package com.hook.hookdemo;

/**
 * @author wsx @ Zhihu Inc.
 * @since 2019-12-25
 */
public class StaticHookClass implements PrintInerface {
    @Override
    public void println() {
        System.out.println("Debug-F I'm static hook class");
    }
}
