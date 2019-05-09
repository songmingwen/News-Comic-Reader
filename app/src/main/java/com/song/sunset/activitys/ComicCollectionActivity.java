package com.song.sunset.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.fragments.CollectionKotlinFragment;
import com.song.sunset.receivers.SunsetWidget;

/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

@Route(path = "/song/comic/collection")
public class ComicCollectionActivity extends BaseActivity {

    private boolean fromWidget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            fromWidget = getIntent().getBooleanExtra(SunsetWidget.FROM, false);
        }
        setContentView(R.layout.activity_comic_collection);
        switchFragment(CollectionKotlinFragment.class.getName(), R.id.id_comic_collection_content);
    }

    @Override
    public void onBackPressedSupport() {
        if (fromWidget) {
            this.startActivity(new Intent(this, MainActivity.class));
        }
        super.onBackPressedSupport();
    }
}
