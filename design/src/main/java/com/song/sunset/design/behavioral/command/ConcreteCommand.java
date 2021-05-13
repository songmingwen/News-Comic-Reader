package com.song.sunset.design.behavioral.command;

/**
 * Desc:    具体命令角色类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:26
 */
public class ConcreteCommand implements Command {

    /**
     * 持有相应的接收者对象
     */
    private Receiver receiver = null;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        if (receiver != null) {
            receiver.action();
        }
    }
}
