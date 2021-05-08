package com.song.sunset.design.creational.single;

public class SingleTon {

    private static volatile SingleTon singleton;

    private SingleTon() {
    }

    public static SingleTon getInstance() {
        if (singleton == null) {
            synchronized (SingleTon.class) {
                if (singleton == null) {
                    singleton = new SingleTon();
                }
            }
        }
        return singleton;
    }
}