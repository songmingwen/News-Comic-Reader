package com.song.sunset.hook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.sunset.base.activity.BaseActivity;
import com.song.sunset.hook.R;
import com.song.sunset.hook.hookdangerapi.HookDangerApiClient;
import com.song.sunset.hook.hookdangerapi.HookGapDangerApiClient;
import com.song.sunset.hook.hooknet.HookNetClient;
import com.song.sunset.hook.ui.dialog.HookResultDialog;

/**
 * Desc:    结果列表页面
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/27 15:25
 */
public class HookResultActivity extends BaseActivity {

    private static final String TAG = "song-HookResultActivity";

    private Button dangerApi, netCall, dangerGapApi;

    public static void startActivity(Context context) {
        Log.i(TAG, "startActivity");
        try {
            Intent intent = new Intent(context, HookResultActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_result);

        dangerApi = findViewById(R.id.dangerapi);
        netCall = findViewById(R.id.netCall);
        dangerGapApi = findViewById(R.id.dangergapapi);

        renderText();
    }

    private void renderText() {
        if (HookDangerApiClient.getInstance().showResult()) {
            dangerApi.setText(String.format(getString(R.string.string_danger_api), HookDangerApiClient.getInstance().getRecord().size()));
        } else {
            dangerApi.setText(R.string.string_no_danger_issue);
        }

        if (HookNetClient.getInstance().showResult()) {
            netCall.setText(String.format(getString(R.string.string_net_call), HookNetClient.getInstance().getRecord().size()));
        } else {
            netCall.setText(R.string.string_no_net_issue);
        }

        if (HookGapDangerApiClient.getInstance().showResult()) {
            dangerGapApi.setText(String.format(getString(R.string.string_danger_gap_api), HookGapDangerApiClient.getInstance().getRecord().size()));
        } else {
            dangerGapApi.setText(R.string.string_no_gap_danger_issue);
        }
    }

    /**
     * 显示所有敏感 api 调用情况
     */
    public void dangerApi(View view) {
        if (HookDangerApiClient.getInstance().showResult()) {
            HookResultDialog.showHookResultDialog(this, HookDangerApiClient.getInstance().getRecord());
        } else {
            Toast.makeText(this, R.string.string_no_danger_issue, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示网络请求情况
     */
    public void netCall(View view) {
        if (HookNetClient.getInstance().showResult()) {
            HookResultDialog.showHookResultDialog(this, HookNetClient.getInstance().getRecord());
        } else {
            Toast.makeText(this, R.string.string_no_net_issue, Toast.LENGTH_SHORT).show();
        }
    }

    public void dangerGapApi(View view) {
        if (HookGapDangerApiClient.getInstance().showResult()) {
            HookResultDialog.showHookResultDialog(this, HookGapDangerApiClient.getInstance().getRecord());
        } else {
            Toast.makeText(this, R.string.string_no_gap_danger_issue, Toast.LENGTH_SHORT).show();
        }
    }
}