package com.song.sunset.design.behavioral.chain;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/12 15:36
 */
public class LogChan {

    public static Logger getLogChain() {
        LogError error = new LogError();
        LogDebug debug = new LogDebug();
        LogInfo info = new LogInfo();
        error.setNextLogger(debug);
        debug.setNextLogger(info);
        return error;
    }
}
