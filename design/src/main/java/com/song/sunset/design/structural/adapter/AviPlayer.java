package com.song.sunset.design.structural.adapter;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:03
 */
public class AviPlayer implements AdvancedMediaPlayer {

    public static final String AVI = "avi";

    @Override
    public void playMp4(String file) {

    }

    @Override
    public void playAvi(String file) {
        System.out.println("play Avi = " + file);
    }
}
