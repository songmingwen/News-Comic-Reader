//
// Created by wangsongxiang on 2019-06-20.
//


#include <jni.h>
#include <string.h>
#include<android/log.h>

#include <sys/time.h>

#ifdef __cplusplus

extern "C" {
#endif
#define LOG    "Debug-Forange_hook" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__)
static const char* kClassMethodHookChar = "com/wsx/hookManager/OrangeHook";

static struct {
    jmethodID m1;
    jmethodID m2;
    size_t methodSize;
} methodHookClassInfo;
JNIEXPORT void JNICALL
Java_com_wsx_hookManager_OrangeHook_hook_1native(JNIEnv *env, jclass type, jobject source_method,
                                                 jobject target_method) {
    void *srcMethod = reinterpret_cast<void *>(env->FromReflectedMethod(source_method));
    void *destMethod = reinterpret_cast<void *>(env->FromReflectedMethod(target_method));
    int *tempMethod = new int[methodHookClassInfo.methodSize];

    struct timeval start_time,end_time;
    memcpy(tempMethod, destMethod, methodHookClassInfo.methodSize);
    memcpy(destMethod, srcMethod, methodHookClassInfo.methodSize);
    memcpy(srcMethod, tempMethod, methodHookClassInfo.methodSize);
    delete tempMethod;
}

JNIEXPORT void JNICALL
Java_com_wsx_hookManager_OrangeHook_measure_1method_1size(JNIEnv *env, jclass type) {
    jclass classEvaluateUtil = env->FindClass(kClassMethodHookChar);
    methodHookClassInfo.m1 = env -> GetStaticMethodID(classEvaluateUtil, "m1", "()V");
    methodHookClassInfo.m2 = env -> GetStaticMethodID(classEvaluateUtil, "m2", "()V");
    methodHookClassInfo.methodSize = reinterpret_cast<size_t>(methodHookClassInfo.m2) - reinterpret_cast<size_t>(methodHookClassInfo.m1);
}

#ifdef __cplusplus
}
#endif

