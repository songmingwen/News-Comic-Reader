package com.song.sunset.design.behavioral.command.audioplayer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:42
 */
public class RewindCommand implements ControlCommand {

    private final AudioPlayer audioPlayer;

    public RewindCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        if (audioPlayer != null) {
            audioPlayer.rewind();
        }
    }
}
