package org.artoolkit.ar.base.log;

import android.util.Log;

/**
 * Desc:    日志包装类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/31 15:56
 */
public class ArLog {

    private static boolean debug;

    public static void setDebug(boolean debug) {
        ArLog.debug = debug;
    }

    public static int d(String tag, String msg) {
        if (debug) {
            return Log.d(tag, msg);
        }
        return -1;
    }

    public static int i(String tag, String msg) {
        if (debug) {
            return Log.i(tag, msg);
        }
        return -1;
    }

    public static int w(String tag, String msg) {
        if (debug) {
            return Log.w(tag, msg);
        }
        return -1;
    }

    public static int e(String tag, String msg) {
        if (debug) {
            return Log.e(tag, msg);
        }
        return -1;
    }

    public static int v(String tag, String msg) {
        if (debug) {
            return Log.v(tag, msg);
        }
        return -1;
    }
}
