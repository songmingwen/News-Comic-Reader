package com.song.sunset.design.behavioral.command.audioplayer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:43
 */
public class StopCommand implements ControlCommand {

    private final AudioPlayer audioPlayer;

    public StopCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
    }
}
