//================================================================================================================================
//
// Copyright (c) 2015-2022 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.easyar.samples.arvideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.imgo.easyar.R;

public class EasyArActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, EasyArActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_ar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EasyArFragment fragment = new EasyArFragment();
        transaction.replace(R.id.preview, fragment);
        transaction.commitAllowingStateLoss();

    }

}
