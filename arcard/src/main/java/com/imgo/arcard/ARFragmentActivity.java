package com.imgo.arcard;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.artoolkit.ar.base.log.ArLog;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/15 10:49
 */
public class ARFragmentActivity extends AppCompatActivity {

    private static final String TAG = "ARFragmentActivity";
    private ARFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        ArLog.setDebug(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new ARFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ArLog.i(TAG, "onConfigurationChanged");
        if (fragment != null) {
            fragment.onPause();
            fragment.onResume();
        }
    }
}
