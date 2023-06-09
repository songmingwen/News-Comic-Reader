package com.imgo.arcard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.imgo.arcard.Config.ConfigHolder;
import com.imgo.arcard.Util.CanvasTransfer;
import com.imgo.arcard.bean.CanvasListData;
import com.song.sunset.base.activity.BaseActivity;
import com.song.sunset.base.utils.AssetsUtils;
import com.song.sunset.utils.JsonUtil;

import org.artoolkit.ar.base.log.ArLog;

import java.io.File;

/**
 * 想快速测试
 * 请将 mockData 中的文件，按照【如何配置测试数据.png】图片的描述，复制到 device file explorer 中。
 */
public class Presentation extends BaseActivity {

    final static String TAG = "Presentation";
    private final static int REQUEST_WRITE = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, Presentation.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        Button bv = findViewById(R.id.btLaunch);
        bv.setOnClickListener(v -> {
            ArLog.d(TAG, "Click");
            Intent myIntent = new Intent(this, ARFragmentActivity.class);
            this.startActivity(myIntent);
        });
        checkPermission();
        initData();
    }

    private void initData() {
        File folder = new File(this.getExternalFilesDir(null) + "/empty");
        if (!folder.exists()) {
            folder.mkdir();
        }

        //加载 json 数据
        String json = AssetsUtils.getJson("mock.json", this);
        // 将 MockData 中对应的文件夹 test1、test2、test3（上面一行中 mock.json 配置的数据）
        // 拷贝到指定目录中（mock.json 中 localFeaturePath 路径）
        // 如果不懂，详见 MockData 中 【mock方法.jpg】图片的描述
        CanvasListData canvasListData = JsonUtil.gsonToBean(json, CanvasListData.class);
        ArLog.i(TAG, "canvasListData = " + json);
        ConfigHolder.getInstance().load(CanvasTransfer.createCanvasList(this, canvasListData));
    }

    private void checkPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (this.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Will drop in here if user denied permissions access camera before.
                        // Or no uses-permission CAMERA element is in the
                        // manifest file. Must explain to the end user why the app wants
                        // permissions to the camera devices.
                        Toast.makeText(this.getApplicationContext(),
                                "App requires access to write external storage to be granted",
                                Toast.LENGTH_SHORT).show();
                    }
                    // Request permission from the user to access the camera.
                    ArLog.i(TAG, "Presentation(): must ask user for write external storage access permission");
                    this.requestPermissions(new String[]
                                    {
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    },
                            REQUEST_WRITE);
                    return;
                }
            }
        } catch (Exception ex) {
            ArLog.e(TAG, "Presentation(): exception caught, " + ex.getMessage());
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        ArLog.i(TAG, "onRequestPermissionsResult(): called");
        if (requestCode == REQUEST_WRITE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        "Application will not run with folder access denied",
                        Toast.LENGTH_LONG).show();
            } else if (1 <= permissions.length) {
                Toast.makeText(getApplicationContext(),
                        String.format("Reading file access permission \"%s\" allowed", permissions[0]),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
