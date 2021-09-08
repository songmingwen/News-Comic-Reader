package com.song.sunset.comic;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.song.sunset.base.activity.BaseActivity;
import com.song.sunset.comic.fragment.CollectionKotlinFragment;
import com.song.sunset.comic.widget.SunsetWidget;

/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

@Route(path = "/comic/collection/activity")
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

}
