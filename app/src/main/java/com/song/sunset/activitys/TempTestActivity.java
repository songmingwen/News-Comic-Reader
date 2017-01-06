package com.song.sunset.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.song.sunset.R;
import com.song.sunset.utils.Reflect;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.views.CustomScrollView;
import com.song.sunset.views.TextSwitchView;
import com.stfalcon.frescoimageviewer.ImageViewer;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TempTestActivity extends BaseActivity {

    private FloatingActionButton button;
    private TextSwitchView textSwitchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        button = (FloatingActionButton) findViewById(R.id.button);
        textSwitchView = (TextSwitchView) findViewById(R.id.textswitch);
    }

    public void onTempTestFlowButtonClick(View view) {
        textSwitchView.setTextStillTime(3000);
    }
}
