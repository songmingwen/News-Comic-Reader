package com.hook.hookdemo;

/**
 * @author wsx @ Zhihu Inc.
 * @since 2019-12-24
 */
public class Test1 {
    private PrintInerface mSuperClass = new SuperClass();
    public void println() {
        mSuperClass.println();
    }
}
