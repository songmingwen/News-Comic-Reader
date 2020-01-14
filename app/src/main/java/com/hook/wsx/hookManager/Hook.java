package com.hook.wsx.hookManager;
import java.lang.reflect.Method;

/**
 * @author wsx @ Zhihu Inc.
 * @since 2019/12/25
 */
public class Hook {
    public static void  hook(Method srcMethod,Method targetMethod) throws NoSuchMethodException {
        OrangeHook methodHook = new OrangeHook(srcMethod,targetMethod);
        methodHook.hook();
    }
}
