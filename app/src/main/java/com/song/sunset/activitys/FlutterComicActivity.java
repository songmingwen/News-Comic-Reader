package com.song.sunset.activitys;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        NetworkPlugin.Companion.registerWith(flutterEngine.getDartExecutor());
        ARouterPlugin.Companion.registerWith(flutterEngine.getDartExecutor());
        Log.i("NetworkPlugin", "configureFlutterEngine");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
