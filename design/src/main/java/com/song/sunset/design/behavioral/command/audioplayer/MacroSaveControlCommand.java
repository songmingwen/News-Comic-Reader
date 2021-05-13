package com.song.sunset.design.behavioral.command.audioplayer;

import java.util.ArrayList;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:57
 */
public class MacroSaveControlCommand implements MacroControlCommand {

    private ArrayList<ControlCommand> list = new ArrayList<>();

    @Override
    public void add(ControlCommand controlCommand) {
        list.add(controlCommand);
    }

    @Override
    public void remove(ControlCommand controlCommand) {
        list.remove(controlCommand);
    }

    @Override
    public void execute() {
        if (!list.isEmpty()) {
            for (ControlCommand command : list) {
                command.execute();
            }
        }
    }
}
