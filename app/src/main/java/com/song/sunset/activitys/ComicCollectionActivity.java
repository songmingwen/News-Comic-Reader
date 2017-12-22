package com.song.sunset.activitys;

import android.os.Bundle;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.fragments.CollectionFragment;

/**
 * Created by Song on 2017/12/22 0022.
 * E-mail: z53520@qq.com
 */

public class ComicCollectionActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_collection);
        switchFragment(CollectionFragment.class.getName(), R.id.id_comic_collection_content);
    }
}
