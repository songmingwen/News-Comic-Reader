package com.song.sunset.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ViewDragHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.fragments.CollectionFragment;
import com.song.sunset.fragments.ComicClassifyFragment;
import com.song.sunset.fragments.ComicRankFragment;
import com.song.sunset.fragments.PhoenixListFragment;
import com.song.sunset.fragments.MVPComicListFragment;
import com.song.sunset.utils.DateTimeUtils;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Song on 2016/12/2.
 * E-mail:z53520@qq.com
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private long lastBackPressedTime;
    private FloatingActionButton fab;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getDelegate().setLocalNightMode(isNightMode() ?
//                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        initView();
        initDrawer();
        setUpListener();
        switchFragmentDelay(PhoenixListFragment.class.getName(), "凤凰新闻");
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.logo);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_news);
        navigationView.setItemIconTintList(null);
        setDrawerLeftEdgeSize(this, drawer, 0.35f);
    }

    private void setUpListener() {
        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, SubScaleViewActivity.class));
//                MainActivity.this.startActivity(new Intent(MainActivity.this, TouchEventTestActivity.class));
                MainActivity.this.startActivity(new Intent(MainActivity.this, TempTestActivity.class));
//                new ImageViewer.Builder(MainActivity.this, new String[]{"http://img2.niutuku.com/1312/0831/0831-niutuku.com-28071.jpg",
//                        "http://img2.niutuku.com/desk/130220/52/52-niutuku.com-984.jpg",
//                        "http://img01.sogoucdn.com/app/a/100540002/490110.jpg",
//                        "http://att.x2.hiapk.com/forum/201409/10/173524pydcdt4ccz928j8d.jpg",
//                        "http://cdn.duitang.com/uploads/item/201409/07/20140907233240_VYNvH.jpeg"})
//                        .setStartPosition(0)
//                        .hideStatusBar(false)
//                        .show();

//                RecursiveTest();
//                PushManager.getInstance().connect();
//                PushManager.getInstance().sendMusicInfo(MusicLoader.instance(MainActivity.this.getContentResolver()).getMusicList().get(0));
//                Log.i("music_list: ", MusicLoader.instance(MainActivity.this.getContentResolver()).getMusicList().toString());

//                switchDayNightMode();

            }
        });
    }

    private void RecursiveTest() {
        long start2 = System.currentTimeMillis();
        long result2 = getPlus(1000L);
        long end2 = System.currentTimeMillis();
        Log.i("结果对比", "result2=" + result2 + "; time2 = " + (end2 - start2) + "millis");

        final long start1 = System.currentTimeMillis();

        Observable.just(1000L)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return getOrderPlus(aLong);
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        long result1 = aLong;
                        long end1 = System.currentTimeMillis();
                        Log.i("结果对比", "result1=" + result1 + "; time1 = " + (end1 - start1) + "millis");
                    }
                });
    }

    private long getFactorial(long endNum) {
        if (endNum <= 1) {
            return 1;
        } else {
            return getFactorial(endNum - 1) * endNum;
        }
    }

    /**
     * Java没有实现编译器尾递归的优化
     *
     * @param endNum
     * @return
     */
    private long getOrderPlus(long endNum) {
        return endNum == 1 ? 1 : getOrderPlus(endNum, 1);
    }

    private long getOrderPlus(long endNum, long sum) {
        return endNum == 1 ? sum : getOrderPlus(endNum - 1, sum + endNum);
    }

    private long getPlus(long endNum) {
        int sum = 0;
        for (long i = 0; i < endNum + 1; i++) {
            sum += i;
        }
        return sum;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            switchFragmentDelay(MVPComicListFragment.class.getName(), getResources().getString(R.string.newest_comic));
        } else if (id == R.id.nav_classify_comic) {
            switchFragmentDelay(ComicClassifyFragment.class.getName(), getResources().getString(R.string.classify_comic));
        } else if (id == R.id.nav_video) {
            VideoListActivity.start(this);
        } else if (id == R.id.nav_rank_comic) {
            switchFragmentDelay(ComicRankFragment.class.getName(), getResources().getString(R.string.rank_comic));
        } else if (id == R.id.nav_news) {
            switchFragmentDelay(PhoenixListFragment.class.getName(), getResources().getString(R.string.phoenix_news));
        } else if (id == R.id.nav_collection) {
            switchFragmentDelay(CollectionFragment.class.getName(), getResources().getString(R.string.collection_comic));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
                moveTaskToBack(true);
            } else {
                lastBackPressedTime = System.currentTimeMillis();
            }
        }
    }

    private void switchFragmentDelay(final String className, final String title) {
        fab.setVisibility(TextUtils.equals(className, PhoenixListFragment.class.getName()) ? View.VISIBLE : View.GONE);
        getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchFragment(className, R.id.activity_framelayout_main);
                toolbar.setTitle(title);
            }
        }, 300);
    }
}
