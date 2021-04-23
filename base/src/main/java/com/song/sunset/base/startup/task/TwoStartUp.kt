package com.song.sunset.base.startup.task;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class TwoStartUp {
    private static TwoStartUp instance;

    private TwoStartUp() {
    }

    public static TwoStartUp getInstance() {
        if (instance == null) {
            instance = new TwoStartUp();
        }
        return instance;
    }

    public void init(@NotNull Context context) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("start_up_task", "2");
    }
}