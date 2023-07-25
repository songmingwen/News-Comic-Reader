package com.easyar.samples.arvideo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.easyar.samples.arvideo.bean.ArListData;
import com.imgo.easyar.R;
import com.song.sunset.base.utils.AssetsUtils;
import com.song.sunset.utils.JsonUtil;

import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.VideoPlayer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/7/24 11:03
 */
public class EasyArFragment extends Fragment {

    /*
     * Steps to create the key for this sample:
     *  1. login www.easyar.com
     *  2. create app with
     *      Name: HelloARVideo
     *      Package Name: cn.easyar.samples.helloarvideo
     *  3. find the created item in the list and show key
     *  4. set key string bellow
     */

//    private static String key = "15Mv1dOAN8nL5iqf+7osx9sr5xpGgyA/YLwHruehGf7TsR/j57wIrqjwTbmi40y+oOdMuaCSTb+r/B/j//BQrv+zD/j3oDfp65sYrqjjUK7+ux/p/KEZ/7DoJ/ewsAni9r4ZxfahXrbJj1Cu5LMO5fO8CP+w6Ceu8b0R4ee8Ffjr8CGgsKIQ7ea0E/7/oV62yfAL5fy2E/vh8FCu/7Mfrs/+XuH9tgng96FetsnwD+n8oRmi278d6/eGDu3xuRXi9fBQruG3Ev/3/D/g/acY3vexE+v8uwjl/bxeoLChGeLht1Le97ET/va7Euuw/l7/97wP6bydHub3sQjY4LMf5/u8G66+8A/p/KEZosGnDurzsRnY4LMf5/u8G66+8A/p/KEZosGiHf7hty/886YV7f6fHfyw/l7/97wP6byfE/j7vRLY4LMf5/u8G66+8A/p/KEZota3Ev/3gQzt5rsd4N+zDK6+8A/p/KEZotGTONjgsx/n+7wbrs/+XunqohX+94YV4feBCO3/ol62/KcQ4L7wFf/evR/t/vBG6vO+D+nv/geu8KcS6P63Nejh8EbXsLET4by6CeLzvAj6vLsR6/38He/muwrl5qte0b7wCu3gux3i5qFetsnwH+P/vwni+6YFrs/+Xvz+swjq/aAR/7DoJ67zvBj+/bsYrs/+XuH9tgng96FetsnwD+n8oRmi278d6/eGDu3xuRXi9fBQruG3Ev/3/D/g/acY3vexE+v8uwjl/bxeoLChGeLht1Le97ET/va7Euuw/l7/97wP6bydHub3sQjY4LMf5/u8G66+8A/p/KEZosGnDurzsRnY4LMf5/u8G66+8A/p/KEZosGiHf7hty/886YV7f6fHfyw/l7/97wP6byfE/j7vRLY4LMf5/u8G66+8A/p/KEZota3Ev/3gQzt5rsd4N+zDK6+8A/p/KEZotGTONjgsx/n+7wbrs/+XunqohX+94YV4feBCO3/ol62/KcQ4L7wFf/evR/t/vBG6vO+D+nv/geu8KcS6P63Nejh8EbXsPAhoLCkHf77sxL44fBG17CxE+H/pxLl5qte0b7wDODzphrj4L8PrqiJXuX9oV7RvvAR4/anEOnh8EbXsKEZ4uG3UsX/sxvpxqAd7/m7Euuw/l7/97wP6byREOPnti7p8b0b4vumFeP88FCu4bcS//f8LunxvQ7o+7wbrr7wD+n8oRmi3bAW6fGmKP7zsRfl/LVeoLChGeLht1Lf56Aa7fG3KP7zsRfl/LVeoLChGeLht1Lf4rMO//eBDO3mux3g37MMrr7wD+n8oRmi370I5f28KP7zsRfl/LVeoLChGeLht1LI97wP6cGiHfj7sxDB86JeoLChGeLht1LP05Yo/vOxF+X8tV7RvvAZ9OK7DunGuxHpwaYd4eLwRuLnvhCgsLsPwP2xHeCw6Brt/qEZ8c+v1v8TVlyacpYOQvLRDTBoUP6hbCy5abrxpbXfBMGmgBxQz2FDREiPRqKhdwIEMbEfH0f6lUmrPAaAdz37ujLaRjpFn5ZWW/uWY/WU1tE7iXmRyLLefJOeYM+0rSOskj5trd3XNqClVYalDuMtIwb53gza3/0F41TmW5dikMn5map4Y1ys3FAtmOuzRHG2JZ0g/Mz3smKQFkUeM4EqhVw75R02PCCUjvTgQqMBgZnv8XduPEhKI7rBRqfiELjb57LVNIsSwm9ilV8RjUbawb1iOsndk1UUZy9Lfxc9Tfv3BQ/6SfGdBrno959N7CJ8u+e4BBoFhMV4rNxkpQbdktJ8jA==";

    private static String key = "fP+OL3jsljNgivNdoLd3JHcNbgafvRLAp2mmVEzNuAR43b4ZTNCpVAOcp0MKi+9Gec+sWFrRsFQVnLAXSsq4BHLbpD9dnOdHFZyxH1rbswVczf9MYsX/FEzQuRpc97kFG4SGKxWcqxdL17wYTc3/TGKcvhlU06gYUMqkVGSS/wZV36kQVsywBRuEhlRO17MSVsmuVBWcsBdanIBaG9OyEkzSuAUbhIZUStuzBVyQlBtY2bgiS9++HVDQulQVnK4TV824WHrSsgNd7LgVVtmzH03Xshgbkv8FXNCuExfsuBVWzLkfV9n/WhvNuBhK2/M5W9S4FU3qrxda1bQYXpzxVErbswVckI4DS9i8FVzqrxda1bQYXpzxVErbswVckI4GWMyuE2rOvAJQ37E7WM7/WhvNuBhK2/M7Vsq0GVfqrxda1bQYXpzxVErbswVckJkTV824JUnfqR9Y0pAXSZzxVErbswVckJ43feqvF1rVtBhenIBaG9ulBlDMuCJQ07glTd+wBhuEswNV0vFUUM2RGVrfsVQD2LwaStugWkKcvwNX2rETcNquVAPl/xVW0/MFVtC6WErLswVcyv8rFZyrF0vXvBhNzf9MYpy+GVTTqBhQyqRUZJL/BlXfqRBWzLAFG4SGVFjQuQRW17lUZJL/G1baqBpczf9MYpyuE1fNuFhw07wRXOqvF1rVtBhenPFUStuzBVyQnhpWy7kkXN2yEVfXqR9W0P9aG824GErb8yRc3bIEXdezERuS/wVc0K4TF/G/HFzdqSJL374dUNC6VBWcrhNXzbhYasuvEFjduCJL374dUNC6VBWcrhNXzbhYas68BErbjgZYyrQXVfO8BhuS/wVc0K4TF/OyAlDRsyJL374dUNC6VBWcrhNXzbhYfduzBVztrRdN17wadN+tVBWcrhNXzbhYev+ZIkvfvh1Q0LpUZJL/E0HOtARc6rQbXO2pF1TO/0xXy7EaFZy0BXXRvhdVnOcQWNKuE0SSplRby7MSVduUEkqc5y0bnIBaG8i8BFDfswJKnOctG92yG1TLsx9Nx/8rFZytGljKuxlL065UA+X/H1bN/ysVnLAZXcuxE0qc5y0bzbgYStvzP1TfuhNtzLwVUtezERuS/wVc0K4TF/2xGUzajxNa0boYUMq0GVec8VRK27MFXJCPE1rRrxJQ0LpUFZyuE1fNuFh23LcTWsqJBFjdth9X2f9aG824GErb8yVMzLsXWtuJBFjdth9X2f9aG824GErb8yVJ368FXO2tF03XvBp0361UFZyuE1fNuFh00akfVtCJBFjdth9X2f9aG824GErb8zJc0K4Tas68AlDfsTtYzv9aG824GErb8zV4+okEWN22H1fZ/ysVnLgOSdevE23XsBNqyrwbSZznGEzSsVob1646Vt28GhuEuxdVzbgLZMO7FwdRi+AaxPemQr5O+AbsE31oeWo6lc8hHgyRqlTUsx9/N7jfk4G8gAqlqDN2AWBmc4KSxqLfo7VFLO4PJNKLUCPOGmhC9u77QqWGRAULFPmDvhaOMQ5NfaznkHbH0EmVDhQRq03jo+iTP3tLvx7oPfwTCW2Gko9FdTo3RtttHbq5SoS5MWCimI34lKM/O+9ZtE3dq1jk29rqSnJtFoS7fZTCnytV0suzVuHs1oVHQ2aefSzi/37en+0xcBqxB9PzvaY7eGSNoAgyUdJcFctqPpSZ9ZFZ5WNossjQh63b9O/vK8rmWVYj1h7ZILKvyNNqDQ3RMkhvbG86/oA5vt12";

    private EasyArGLView easyArGlView;

    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();

    private int permissionRequestCodeSerial = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_easy_ar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getActivity() == null) {
            return;
        }
        if (!Engine.initialize(getActivity(), key)) {
            Log.e("HelloAR", "Initialization Failed.");
            Toast.makeText(getActivity(), Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        if (!CameraDevice.isAvailable()) {
            Toast.makeText(getActivity(), "CameraDevice not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ImageTracker.isAvailable()) {
            Toast.makeText(getActivity(), "ImageTracker not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!VideoPlayer.isAvailable()) {
            Toast.makeText(getActivity(), "VideoPlayer not available.", Toast.LENGTH_LONG).show();
            return;
        }

        easyArGlView = new EasyArGLView(getActivity());

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ((ViewGroup) view.findViewById(R.id.preview))
                        .addView(easyArGlView, new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private void initData() {
        if (getActivity() == null) {
            return;
        }
        String json = AssetsUtils.getJson("arlist.json", getActivity());
        ArListData arListData = JsonUtil.gsonToBean(json, ArListData.class);
        if (arListData != null && arListData.list != null) {
            ArDataManager.getInstance().setList(arListData.list);
        }
    }

    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback) {
        if (getActivity() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
    public void onResume() {
        super.onResume();
        if (easyArGlView != null) {
            easyArGlView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (easyArGlView != null) {
            easyArGlView.onPause();
        }
        super.onPause();
    }

    private interface PermissionCallback {
        void onSuccess();

        void onFailure();
    }
}
