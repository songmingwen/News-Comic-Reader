package com.song.sunset.design.structural.adapter;

import static com.song.sunset.design.structural.adapter.AviPlayer.AVI;
import static com.song.sunset.design.structural.adapter.Mp4Player.MP4;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 10:04
 */
public class MediaAdapter implements MediaPlayer {

    @Override
    public void play(String type, String file) {
        if (MP4.equals(type)) {
            AdvancedMediaPlayer mediaPlayer = new Mp4Player();
            mediaPlayer.playMp4(file);
        } else if (AVI.equals(type)) {
            AdvancedMediaPlayer mediaPlayer = new AviPlayer();
            mediaPlayer.playAvi(file);
        }
    }
}
