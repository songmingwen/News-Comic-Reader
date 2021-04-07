package com.song.sunset.comic.startup.task;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class FiveStartUp {
    private static FiveStartUp instance;

    private FiveStartUp() {
    }

    public static FiveStartUp getInstance() {
        if (instance == null) {
            instance = new FiveStartUp();
        }
        return instance;
    }

    public void init(@NotNull Context context) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("start_up_task", "5");
        }).start();
    }
}