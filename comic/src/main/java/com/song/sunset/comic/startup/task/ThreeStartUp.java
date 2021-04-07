package com.song.sunset.comic.startup.task;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class ThreeStartUp {
    private static ThreeStartUp instance;

    private ThreeStartUp() {
    }

    public static ThreeStartUp getInstance() {
        if (instance == null) {
            instance = new ThreeStartUp();
        }
        return instance;
    }

    public void init(@NotNull Context context) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("start_up_task", "3");
    }
}