package com.song.sunset.activitys.temp

import android.Manifest
import android.content.pm.VersionedPackage
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

@Route(path = "/song/xposed")
class XposedActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xposed)

        val classLoader: ClassLoader = this.classLoader
        val tele: Class<*> = classLoader.loadClass("android.telephony.TelephonyManager")
        XposedHelpers.findAndHookMethod(tele, "getImei", object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                Log.i("ssoonngg,找到了 getImei", param.toString())

            }
        })
        XposedHelpers.findAndHookMethod(tele, "getMeid", object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                Log.i("ssoonngg,找到了getMeid", param.toString())

            }
        })
        XposedHelpers.findAndHookMethod(tele, "getDeviceId", object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                Log.i("ssoonng getDeviceId", param.method.name)

            }
        })
        XposedHelpers.findAndHookMethod(tele, "getSubscriberId", object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {

                Log.i("ssoonng getSubscriberId", param.method.name)

                Log.i("ssoo", getThreadStacktrace(Thread.currentThread()))
            }
        })

        val packageManager: Class<*> = classLoader.loadClass("android.app.ApplicationPackageManager")
        XposedHelpers.findAndHookMethod(packageManager, "getInstalledPackages", Int::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {

                Log.i("ssoonng", "getInstalledPackages" + param.method.name)

                Log.i("ssoo", getThreadStacktrace(Thread.currentThread()))
            }
        })
        XposedHelpers.findAndHookMethod(packageManager, "getPackageInfo", String::class.java, Int::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {

                Log.i("ssoonng", "getPackageInfo" + param.method.name)

                Log.i("ssoo", getThreadStacktrace(Thread.currentThread()))
            }
        })
        XposedHelpers.findAndHookMethod(packageManager, "getPackageInfo", VersionedPackage::class.java, Int::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {

                Log.i("ssoonng", "getPackageInfo" + param.method.name)

                Log.i("ssoo", getThreadStacktrace(Thread.currentThread()))
            }
        })
    }

    fun getThreadStacktrace(thd: Thread?): String {
        if (thd == null) {
            return ""
        }
        val sb = StringBuilder()
        try {
            val stacktrace = thd.stackTrace
            sb.append("\n\nThread StackTrace:\n")
            sb.append("pid: ").append(Process.myPid()).append(", tid: ").append(thd.id).append(", name: ")
                    .append(thd.name).append("  >>> ").append("processNamexxx").append(" <<<\n")
            sb.append("\n")
            sb.append("java stacktrace:\n")
            for (element in stacktrace) {
                sb.append("    at ").append(element.toString()).append("\n")
            }
            sb.append("\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun start(view: View) {
        test()
    }

    private fun test() {
        val telephonyManager = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        val disposable = RxPermissions(this)
                .request(Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted: Boolean ->
                    if (granted) {
                        tele(telephonyManager)
                        pm()
                    }
                }
    }

    private fun pm() {
        val packageManager = this.packageManager
        try {
            Log.i("ssoonng", "开始测试 getInstalledPackages")
            packageManager.getInstalledPackages(0)
        } catch (e: java.lang.Exception) {

        }
        try {
            Log.i("ssoonng", "开始测试 getPackageInfo(string,int)")
            packageManager.getPackageInfo("", 0)
        } catch (e: java.lang.Exception) {

        }

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Log.i("ssoonng", "开始测试 getPackageInfo(VersionedPackage,int)")
                packageManager.getPackageInfo((VersionedPackage("", 1)), 0)
            }
        } catch (e: java.lang.Exception) {

        }

    }

    private fun tele(telephonyManager: TelephonyManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Log.i("ssoonngg,imei", "开始测试")
                telephonyManager.imei
            } catch (e: java.lang.Exception) {

            }
            try {
                Log.i("ssoonngg,meid", "开始测试")
                telephonyManager.meid
            } catch (e: java.lang.Exception) {

            }
            try {
                Log.i("ssoonngg,deviceId", "开始测试")
                telephonyManager.deviceId
            } catch (e: java.lang.Exception) {

            }
            try {
                Log.i("ssoonngg,subscriberId", "开始测试")
                telephonyManager.subscriberId
            } catch (e: java.lang.Exception) {

            }
        }
    }
}