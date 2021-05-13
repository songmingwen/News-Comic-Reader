package com.song.sunset.design.behavioral.command.audioplayer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:44
 */
public class Keypad {

    private final ControlCommand play;
    private final ControlCommand rewind;
    private final ControlCommand stop;

    public Keypad(ControlCommand play, ControlCommand rewind, ControlCommand stop) {
        this.play = play;
        this.rewind = rewind;
        this.stop = stop;
    }

    public void play() {
        if (play != null) {
            play.execute();
        }
    }

    public void rewind() {
        if (rewind != null) {
            rewind.execute();
        }
    }

    public void stop() {
        if (stop != null) {
            stop.execute();
        }
    }
}
