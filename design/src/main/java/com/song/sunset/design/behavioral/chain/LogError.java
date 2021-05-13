package com.song.sunset.design.behavioral.chain;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 15:30
 */
public class LogError extends Logger {

    public LogError() {
        level = ERROR;
    }

    @Override
    void log(String message) {
        System.out.println("Error：" + message);
    }
}
