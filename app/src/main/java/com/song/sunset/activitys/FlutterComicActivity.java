package com.song.sunset.activitys;

import android.os.Bundle;
import android.util.Log;

import com.flutter.SimpleEventPlugin;
import com.flutter.net.NetworkPlugin;
import com.flutter.router.ARouterPlugin;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/6
 */
public class FlutterComicActivity extends FlutterActivity {

    private FlutterEngine flutterEngine;

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        this.flutterEngine = flutterEngine;
        NetworkPlugin.Companion.registerWith(flutterEngine.getDartExecutor());
        ARouterPlugin.Companion.registerWith(flutterEngine.getDartExecutor());
        SimpleEventPlugin.Companion.registerWith(flutterEngine.getDartExecutor());
        Log.i("FlutterComicActivity", "configureFlutterEngine");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("FlutterComicActivity", "onDestroy");
        cleanUpFlutterEngine(flutterEngine);
    }
}
