package com.song.sunset.hook.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLog {
    public static final int LEVEL_V = 0;
    public static final int LEVEL_D = 1;
    public static final int LEVEL_I = 2;
    public static final int LEVEL_W = 3;
    public static final int LEVEL_E = 4;
    private static final String TAG_PREFIX = "xxxx_";
    private static final String TAG = "LOG";
    public static final String TAG_NET = "NET";//网络相关日志
    private static final boolean DEBUG = true;


    private static final String SDCARD_LOG_PATH = "/mnt/sdcard/com.qm.core/log";// 日志文件在sdcard中的路径

    private static final String LOG_FILE_NAME = "Log.txt";// 本类输出的日志文件名称

    private static final SimpleDateFormat mLogTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式

    private static final SimpleDateFormat mLogFileNameFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void logs(String format, Object... args) {
        log(String.format(format, args));
    }

    public static void log(String msg) {
        log(1, msg);
    }

    public static void log(int level, String msg) {
        log(TAG, level, msg);
    }

    public static void log(String tag, String msg) {
        log(tag, 1, msg);
    }

    public static void log(String tag, int level, String msg) {
        log(tag, level, msg, null);
    }

    public static void log(String tag, int level, String msg, Throwable e) {
        if (DEBUG) {
            tag = TAG_PREFIX + tag;
            switch (level) {
                case LEVEL_V:
                    Log.v(tag, msg == null ? "null" : msg, e);
                    break;
                case LEVEL_D:
                    Log.d(tag, msg == null ? "null" : msg, e);
                    break;
                case LEVEL_I:
                    Log.i(tag, msg == null ? "null" : msg, e);
                    break;
                case LEVEL_W:
                    Log.w(tag, msg == null ? "null" : msg, e);
                    break;
                case LEVEL_E:
                default:
                    Log.e(tag, msg == null ? "null" : msg, e);
                    break;
            }
        }
    }


    public static void log(String tag, Throwable e) {
        if (DEBUG) {
            log(tag, LEVEL_W, "", e == null ? new RuntimeException("null") : e);
        }
    }

    public static void log(Throwable e) {
        if (DEBUG) {
            log(TAG, LEVEL_W, "", e == null ? new RuntimeException("null") : e);
        }
    }

    // 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
    // 所以这里使用自己分节的方式来输出足够长度的message
    public static void longLog(String tag, int level, String str) {
        int index = 0;
        int maxLength = 2000;
        while (str.length() > index * maxLength) {
            // java的字符不允许指定超过总的长度end
            if (str.length() < (index + 1) * maxLength) {
                log(level, str.substring(index * maxLength));
            } else {
                log(level, str.substring(index * maxLength, (index + 1) * maxLength));
            }
            index++;
        }
    }

    // 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
    // 所以这里使用自己分节的方式来输出足够长度的message
    public static void longLog(String str) {
        longLog(TAG, 1, str);
    }

    public static void longLog(int level, String str) {
        longLog(TAG, level, str);
    }

    public static void longLog(String tag, String str) {
        longLog(tag, 1, str);
    }

    public static void writeLog2File(String logLevel, String tag, String text) {// 新建或打开日志文件
        Date nowTime = new Date();
        String needWriteFile = mLogFileNameFormat.format(nowTime);
        String needWriteMessage = mLogTimeFormat.format(nowTime) + "    " + logLevel + "    " + tag + "    " + text;

        File dirsFile = new File(SDCARD_LOG_PATH);
        if (!dirsFile.exists()) {
            dirsFile.mkdirs();
        }


        File file = new File(dirsFile.toString(), needWriteFile + LOG_FILE_NAME);
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {

            }
        }

        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}