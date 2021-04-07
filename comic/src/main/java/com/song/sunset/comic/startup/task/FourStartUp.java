package com.song.sunset.comic.startup.task;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class FourStartUp {
    private static FourStartUp instance;

    private FourStartUp() {
    }

    public static FourStartUp getInstance() {
        if (instance == null) {
            instance = new FourStartUp();
        }
        return instance;
    }

    public void init(@NotNull Context context) {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("start_up_task", "4");
    }
}