package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.song.core.statusbar.StatusBarUtil;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.fragments.ComicGenericListFragment;

/**
 * Created by Song on 2016/9/5 0005.
 * Email:z53520@qq.com
 */
@Route(path = "/song/comic/list")
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
        toolbar.setLogo(R.mipmap.ic_launcher_round);
        toolbar.setTitle(sortName);

        Bundle bundle = new Bundle();
        bundle.putString(ARG_NAME, argName);
        bundle.putInt(ARG_VALUE, argValue);
        loadFragment(getBaseContext(), R.id.id_comic_list_framelayout, ComicGenericListFragment.class.getName(), bundle, false);
    }

    public static void start(Context context, String argName, int argValue, String sortName) {
        Intent intent = new Intent(context, ComicListActivity.class);
        intent.putExtra(ARG_NAME, argName);
        intent.putExtra(ARG_VALUE, argValue);
        intent.putExtra(SORT_NAME, sortName);
        context.startActivity(intent);
    }
}
