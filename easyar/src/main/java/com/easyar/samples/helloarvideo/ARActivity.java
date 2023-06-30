//================================================================================================================================
//
// Copyright (c) 2015-2022 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.easyar.samples.helloarvideo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.easyar.samples.helloarvideo.bean.ArListData;
import com.imgo.easyar.R;
import com.song.sunset.base.utils.AssetsUtils;
import com.song.sunset.utils.JsonUtil;

import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.VideoPlayer;

public class ARActivity extends Activity {
    /*
     * Steps to create the key for this sample:
     *  1. login www.easyar.com
     *  2. create app with
     *      Name: HelloARVideo
     *      Package Name: cn.easyar.samples.helloarvideo
     *  3. find the created item in the list and show key
     *  4. set key string bellow
     */
    private static String key = "fP+OL3jsljNgivNdoLd3JHcNbgafvRLAp2mmVEzNuAR43b4ZTNCpVAOcp0MKi+9Gec+sWFrRsFQVnLAXSsq4BHLbpD9dnOdHFZyxH1rbswVczf9MYsX/FEzQuRpc97kFG4SGKxWcqxdL17wYTc3/TGKcvhlU06gYUMqkVGSS/wZV36kQVsywBRuEhlRO17MSVsmuVBWcsBdanIBaG9OyEkzSuAUbhIZUStuzBVyQlBtY2bgiS9++HVDQulQVnK4TV824WHrSsgNd7LgVVtmzH03Xshgbkv8FXNCuExfsuBVWzLkfV9n/WhvNuBhK2/M5W9S4FU3qrxda1bQYXpzxVErbswVckI4DS9i8FVzqrxda1bQYXpzxVErbswVckI4GWMyuE2rOvAJQ37E7WM7/WhvNuBhK2/M7Vsq0GVfqrxda1bQYXpzxVErbswVckJkTV824JUnfqR9Y0pAXSZzxVErbswVckJ43feqvF1rVtBhenIBaG9ulBlDMuCJQ07glTd+wBhuEswNV0vFUUM2RGVrfsVQD2LwaStugWkKcvwNX2rETcNquVAPl/xVW0/MFVtC6WErLswVcyv8rFZyrF0vXvBhNzf9MYpy+GVTTqBhQyqRUZJL/BlXfqRBWzLAFG4SGVFjQuQRW17lUZJL/G1baqBpczf9MYpyuE1fNuFhw07wRXOqvF1rVtBhenPFUStuzBVyQnhpWy7kkXN2yEVfXqR9W0P9aG824GErb8yRc3bIEXdezERuS/wVc0K4TF/G/HFzdqSJL374dUNC6VBWcrhNXzbhYasuvEFjduCJL374dUNC6VBWcrhNXzbhYas68BErbjgZYyrQXVfO8BhuS/wVc0K4TF/OyAlDRsyJL374dUNC6VBWcrhNXzbhYfduzBVztrRdN17wadN+tVBWcrhNXzbhYev+ZIkvfvh1Q0LpUZJL/E0HOtARc6rQbXO2pF1TO/0xXy7EaFZy0BXXRvhdVnOcQWNKuE0SSplRby7MSVduUEkqc5y0bnIBaG8i8BFDfswJKnOctG92yG1TLsx9Nx/8rFZytGljKuxlL065UA+X/H1bN/ysVnLAZXcuxE0qc5y0bzbgYStvzP1TfuhNtzLwVUtezERuS/wVc0K4TF/2xGUzajxNa0boYUMq0GVec8VRK27MFXJCPE1rRrxJQ0LpUFZyuE1fNuFh23LcTWsqJBFjdth9X2f9aG824GErb8yVMzLsXWtuJBFjdth9X2f9aG824GErb8yVJ368FXO2tF03XvBp0361UFZyuE1fNuFh00akfVtCJBFjdth9X2f9aG824GErb8zJc0K4Tas68AlDfsTtYzv9aG824GErb8zV4+okEWN22H1fZ/ysVnLgOSdevE23XsBNqyrwbSZznGEzSsVob1646Vt28GhuEuxdVzbgLZMO7FwdRi+AaxPemQr5O+AbsE31oeWo6lc8hHgyRqlTUsx9/N7jfk4G8gAqlqDN2AWBmc4KSxqLfo7VFLO4PJNKLUCPOGmhC9u77QqWGRAULFPmDvhaOMQ5NfaznkHbH0EmVDhQRq03jo+iTP3tLvx7oPfwTCW2Gko9FdTo3RtttHbq5SoS5MWCimI34lKM/O+9ZtE3dq1jk29rqSnJtFoS7fZTCnytV0suzVuHs1oVHQ2aefSzi/37en+0xcBqxB9PzvaY7eGSNoAgyUdJcFctqPpSZ9ZFZ5WNossjQh63b9O/vK8rmWVYj1h7ZILKvyNNqDQ3RMkhvbG86/oA5vt12";
    private GLView glView;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ARActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_ar);

        initData();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (!Engine.initialize(this, key)) {
            Log.e("HelloAR", "Initialization Failed.");
            Toast.makeText(ARActivity.this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        if (!CameraDevice.isAvailable()) {
            Toast.makeText(ARActivity.this, "CameraDevice not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ImageTracker.isAvailable()) {
            Toast.makeText(ARActivity.this, "ImageTracker not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!VideoPlayer.isAvailable()) {
            Toast.makeText(ARActivity.this, "VideoPlayer not available.", Toast.LENGTH_LONG).show();
            return;
        }

        glView = new GLView(this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private void initData() {
        String json = AssetsUtils.getJson("arlist.json", this);
        ArListData arListData = JsonUtil.gsonToBean(json, ArListData.class);
        if (arListData != null && arListData.list != null) {
            ArDataManager.getInstance().setList(arListData.list);
        }
    }

    private interface PermissionCallback {
        void onSuccess();

        void onFailure();
    }

    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;

    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionCallbacks.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallbacks.get(requestCode);
            permissionCallbacks.remove(requestCode);
            boolean executed = false;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    executed = true;
                    callback.onFailure();
                }
            }
            if (!executed) {
                callback.onSuccess();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glView != null) {
            glView.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (glView != null) {
            glView.onPause();
        }
        super.onPause();
    }
}
