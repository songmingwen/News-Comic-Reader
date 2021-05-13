package com.song.sunset.design.behavioral.chain;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 15:24
 */
public abstract class Logger {

    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    /**
     * 日志等级
     */
    protected int level;

    private Logger next;

    public void setNextLogger(Logger logger) {
        this.next = logger;
    }

    /**
     * 根据 level 输出日志
     */
    public void log(int level, String message) {
        if (this.level <= level) {
            log(message);
        }
        if (next != null) {
            next.log(level, message);
        }
    }

    abstract void log(String message);
}
