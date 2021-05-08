package com.song.sunset.design.creational.builder;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/7 16:27
 */
public class ComputerClient {

    String keyboard;
    String display;
    String os;

    public String getKeyboard() {
        return keyboard;
    }

    public String getDisplay() {
        return display;
    }

    public String getOs() {
        return os;
    }

    public ComputerClient() {
        this(new Builder());
    }

    ComputerClient(Builder builder) {
        this.keyboard = builder.keyboard;
        this.display = builder.display;
        this.os = builder.os;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public void printComputer() {
        System.out.println("keyboard = " + keyboard);
        System.out.println("display = " + display);
        System.out.println("os = " + os);
    }

    public static final class Builder {
        String keyboard;
        String display;
        String os;

        public Builder() {
            this.keyboard = "无";
            this.display = "无";
            this.os = "i5 处理器";
        }

        Builder(ComputerClient computerClient) {
            this.keyboard = computerClient.keyboard;
            this.display = computerClient.display;
            this.os = computerClient.os;
        }

        public Builder keyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder display(String display) {
            this.display = display;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public ComputerClient build() {
            return new ComputerClient(this);
        }
    }
}
