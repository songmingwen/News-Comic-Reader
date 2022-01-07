package com.song.sunset.hook.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Desc:    堆栈工具类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/31 15:53
 */
public class StacktraceUtil {

    private static String mProcessName;

    public static String getThreadStacktrace(Context context) {
        Thread thread = Thread.currentThread();
        if (thread == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            StackTraceElement[] stacktrace = thread.getStackTrace();
            sb.append("\n\nThread StackTrace:\n")
                    .append("    >>>    processName: ").append(getProcessName(context)).append("    <<<    ")
                    .append("\n")
                    .append("    >>>    ").append("threadId: ").append(thread.getId()).append(", threadName: ").append(thread.getName()).append("    <<<    ")
                    .append("\n")
                    .append("java stacktrace:\n");
            for (StackTraceElement element : stacktrace) {
                sb.append("    at ").append(element.toString()).append("\n");
            }
            sb.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String getProcessName(Context ctx) {

        int pid = Process.myPid();

        if (TextUtils.isEmpty(mProcessName)) {
            mProcessName = "";

            //get from ActivityManager
            try {
                ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
                if (manager != null) {
                    List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
                    if (processInfoList != null) {
                        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
                            if (processInfo.pid == pid && !TextUtils.isEmpty(processInfo.processName)) {
                                mProcessName = processInfo.processName; //OK
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }

        if (TextUtils.isEmpty(mProcessName)) {
            //get from /proc/PID/cmdline
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
                String processName = br.readLine();
                if (!TextUtils.isEmpty(processName)) {
                    processName = processName.trim();
                    if (!TextUtils.isEmpty(processName)) {
                        mProcessName = processName; //OK
                    }
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception ignored) {
                }
            }
        }

        if (TextUtils.isEmpty(mProcessName)) {
            return "unknown processName";
        } else {
            return mProcessName;
        }

    }
}
