package com.song.sunset.design.behavioral.command.audioplayer;

/**
 * Desc:    宏命令:包含多个命令的命令，是一个命令的组合。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:54
 */
public interface MacroControlCommand extends ControlCommand {
    void add(ControlCommand controlCommand);

    void remove(ControlCommand controlCommand);
}
