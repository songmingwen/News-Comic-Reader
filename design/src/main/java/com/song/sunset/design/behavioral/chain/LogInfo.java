package com.song.sunset.design.behavioral.chain;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 15:32
 */
public class LogInfo extends Logger {

    public LogInfo() {
        level = INFO;
    }

    @Override
    void log(String message) {
        System.out.println("Info：" + message);
    }
}
