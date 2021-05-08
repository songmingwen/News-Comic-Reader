package com.song.sunset.design.structural.adapter;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:02
 */
public class Mp4Player implements AdvancedMediaPlayer {

    public static final String MP4 = "mp4";

    @Override
    public void playMp4(String file) {
        System.out.println("play Mp4 = " + file);
    }

    @Override
    public void playAvi(String file) {

    }
}
