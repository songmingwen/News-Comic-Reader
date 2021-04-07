package com.song.sunset.comic.startup.task;

import android.content.Context;
import android.util.Log;

public class OneStartUp {

    private static OneStartUp instance;

    private OneStartUp() {
    }

    public static OneStartUp getInstance() {
        if (instance == null) {
            instance = new OneStartUp();
        }
        return instance;
    }

    public void init(Context context) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("start_up_task", "1");
    }
}