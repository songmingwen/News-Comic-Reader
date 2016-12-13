package com.song.sunset.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.song.sunset.R;
import com.song.sunset.Reflect;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.views.CustomScrollView;

/**
 * Created by songmw3 on 2016/12/2.
 */

public class TempTestActivity extends BaseActivity {

    private CustomScrollView scrollView;
    private ProgressBar progressBar;
    private TextView percent;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);
        scrollView = (CustomScrollView) findViewById(R.id.scrollView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        button = (Button) findViewById(R.id.button);
        percent = (TextView) findViewById(R.id.percent);
        scrollView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        Reflect.on(scrollView).set("mOverflingDistance", ViewUtil.getScreenHeigth() / 16);
    }

    public void onTempTestFlowButtonClick(View view) {
        scrollView.addViews();
    }
}
