package com.song.sunset.design.behavioral.chain;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 15:33
 */
public class LogDebug extends Logger {

    public LogDebug() {
        level = DEBUG;
    }

    @Override
    void log(String message) {
        System.out.println("Debug：" + message);
    }
}
