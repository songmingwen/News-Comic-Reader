package com.song.sunset.design.structural.adapter;

import static com.song.sunset.design.structural.adapter.AviPlayer.AVI;
import static com.song.sunset.design.structural.adapter.Mp4Player.MP4;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:11
 */
public class AudioPlayer implements MediaPlayer {

    public static final String MP3 = "mp3";

    @Override
    public void play(String type, String file) {
        if (type == null) {
            return;
        }
        switch (type) {
            case MP3:
                System.out.println("play mp3 = " + file);
                break;
            case MP4:
            case AVI:
                MediaAdapter mediaAdapter = new MediaAdapter();
                mediaAdapter.play(type, file);
                break;
            default:
                System.out.println("不支持此类型文件 = " + type);
                break;
        }
    }
}
