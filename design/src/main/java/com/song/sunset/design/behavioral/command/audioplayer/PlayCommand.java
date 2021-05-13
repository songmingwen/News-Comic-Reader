package com.song.sunset.design.behavioral.command.audioplayer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:38
 */
public class PlayCommand implements ControlCommand {

    private final AudioPlayer audioPlayer;

    public PlayCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        if (audioPlayer != null) {
            audioPlayer.play();
        }
    }
}
