package com.song.sunset.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.song.sunset.R;
import com.song.sunset.fragments.ComicClassifyFragment;
import com.song.sunset.fragments.ComicListFragment;
import com.song.sunset.fragments.ComicRankFragment;
import com.song.sunset.fragments.TVListFragment;
import com.song.sunset.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TimePickerView pvTime;
    private long lastBackPressedTime;
    private OptionsPickerView pvOptions;
    private ArrayList<String> options1Items = new ArrayList<>();
    private FloatingActionButton fab;
    private NavigationView navigationView;
//    private UserDao mUserDao;
//    private CoderDao mCoderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);

//        mUserDao = SunsetApplication.getSunsetApplication().getUserDaoSession().getUserDao();
//        mCoderDao = SunsetApplication.getSunsetApplication().getUserDaoSession().getCoderDao();

        initView();
        initDrawer();
        initTimePicker();
        initOptionsPicker();

        switchFragment(ComicListFragment.class.getName(), R.id.activity_framelayout_main);
        toolbar.setTitle("更新漫画");

        setUpListener();
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpListener() {
        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                pvTime.show();
//                pvOptions.show();
//                MainActivity.this.startActivity(new Intent(MainActivity.this, SubScaleViewActivity.class));
            }
        });
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            switchFragmentDelay(ComicListFragment.class.getName(), "更新漫画");
        } else if (id == R.id.classify_comic) {
            switchFragmentDelay(ComicClassifyFragment.class.getName(), "分类漫画");
        } else if (id == R.id.nav_slideshow) {
            switchFragmentDelay(TVListFragment.class.getName(), "电视频道");
        } else if (id == R.id.nav_manage) {
//            ComicRankFragment.start(MainActivity.this);
            switchFragmentDelay(ComicRankFragment.class.getName(), "排行漫画");

//            List<User> users = mUserDao.loadAll();
//            User load = mUserDao.load(1993l);
//            if (load != null)
//                Log.d("-------------:", load.toString());
//            Log.d("-------------:", users.toString());
//
//            List<Coder> users0 = mCoderDao.loadAll();
//            Coder load0 = mCoderDao.load(1994l);
//            if (load0 != null)
//                Log.i("-------------:", load0.toString());
//            Log.i("-------------:", users0.toString());

        } else if (id == R.id.nav_share) {
            getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LocationMarkerActivity.start(MainActivity.this);
                }
            }, 300);
//            mUserDao.deleteByKey(1993l);
//            mCoderDao.deleteByKey(19940l);

        } else if (id == R.id.nav_send) {
            getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    BasicMapActivity.start(MainActivity.this);
//                    MeadiaPlayerActivity.start(MainActivity.this);
                    VideoViewActivity.start(MainActivity.this);
                }
            }, 300);

//            if (mUserDao.load(1993l) == null)
//                mUserDao.insert(new User(1993l, "taozi"));
//            mUserDao.update(new User(1993l, "Song"));
//
//            if (mCoderDao.load(1994l) == null)
//                mCoderDao.insert(new Coder(1994l, "taozi"));
//            mCoderDao.update(new Coder(1994l, "Song"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
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

    private void initTimePicker() {
        pvTime = new TimePickerView(MainActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setRange(1900, 2016);
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setTime(new Date());
        pvTime.setTitle("请选择日期");
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
//                Toast.makeText(MainActivity.this, c.get(Calendar.YEAR) + "年" + (1 + c.get(Calendar.MONTH)) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, DateTimeUtils.getConstellation(1 + c.get(Calendar.MONTH), Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, DateTimeUtils.getAge(date) + "岁", Toast.LENGTH_SHORT).show();

//                User user = new User();
//                user.setId((long) c.get(Calendar.YEAR));
//                user.setName(DateTimeUtils.getAge(date) + "岁");
//                mUserDao.insert(user);

//                Coder coder = new Coder();
//                coder.setId((long) c.get(Calendar.YEAR));
//                coder.setName(DateTimeUtils.getAge(date) + "岁");
//                mCoderDao.insert(coder);
            }
        });
    }

    private void initOptionsPicker() {
        pvOptions = new OptionsPickerView(this);
        options1Items.add("男");
        options1Items.add("女");
        pvOptions.setPicker(options1Items);
        pvOptions.setTitle("选择性别");
        pvOptions.setCyclic(true);
        pvOptions.setLabels("");
        pvOptions.setSelectOptions(1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                Toast.makeText(MainActivity.this, options1 + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchFragmentDelay(final String className, final String title) {
        getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchFragment(className, R.id.activity_framelayout_main);
                toolbar.setTitle(title);
            }
        }, 300);
    }
}
