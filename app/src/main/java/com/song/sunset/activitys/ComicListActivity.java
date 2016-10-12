package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.song.sunset.R;
import com.song.sunset.fragments.ComicListFragment;

/**
 * Created by Song on 2016/9/5 0005.
 * Email:z53520@qq.com
 */
public class ComicListActivity extends BaseActivity {

    public static final String ARG_NAME = "argName";
    public static final String ARG_VALUE = "argValue";
    public static final String SORT_NAME = "sortName";
    private Toolbar toolbar;

    private String argName = "";
    private String sortName = "";
    private int argValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        if (getIntent() != null) {
            argName = getIntent().getStringExtra(ARG_NAME);
            sortName = getIntent().getStringExtra(SORT_NAME);
            argValue = getIntent().getIntExtra(ARG_VALUE, -1);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle(sortName);

        Bundle bundle = new Bundle();
        bundle.putString(ARG_NAME, argName);
        bundle.putInt(ARG_VALUE, argValue);
        loadFragment(getBaseContext(), R.id.id_comic_list_framelayout, ComicListFragment.class.getName(), bundle, false);
    }

    public static void start(Context context, String argName, int argValue, String sortName) {
        Intent intent = new Intent(context, ComicListActivity.class);
        intent.putExtra(ARG_NAME, argName);
        intent.putExtra(ARG_VALUE, argValue);
        intent.putExtra(SORT_NAME, sortName);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
