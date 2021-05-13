package com.song.sunset.design.behavioral.command;

/**
 * Desc:    请求者角色类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:28
 */
public class Invoker {
    /**
     * 持有命令对象
     */
    private Command command = null;

    public Invoker(Command command) {
        this.command = command;
    }

    public void action(){
        if (command != null) {
            command.execute();
        }
    }
}
