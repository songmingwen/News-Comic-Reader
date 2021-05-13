package com.song.sunset.design.behavioral.command.audioplayer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:39
 */
public class AudioPlayer {

    private final String file;

    public AudioPlayer(String file) {
        this.file = file;
    }

    public void play() {
        System.out.println("play:" + file);
    }

    public void rewind() {
        System.out.println("rewind:" + file);
    }

    public void stop() {
        System.out.println("stop:" + file);
    }
}
