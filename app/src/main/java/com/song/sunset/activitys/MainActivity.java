package com.song.sunset.activitys;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.song.core.statusbar.StatusBarUtil;
import com.song.sunset.BindView;
import com.song.sunset.BuildConfig;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.activitys.temp.FunctionListActivity;
import com.song.sunset.beans.CollectionOnlineListBean;
import com.song.sunset.beans.ComicCollectionBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.fragments.PhoenixListFragment;
import com.song.sunset.mvp.models.ComicCollectionModel;
import com.song.sunset.mvp.presenters.ComicCollectionPresenter;
import com.song.sunset.mvp.views.ComicCollectionView;
import com.song.sunset.services.managers.MessengerManager;
import com.song.sunset.services.managers.PushManager;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.BindViewTools;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.preinstall.*;
import com.song.video.VideoManager;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import io.flutter.embedding.android.FlutterActivity;

/**
 * Created by Song on 2016/12/2.
 * E-mail:z53520@qq.com
 */
@Route(path = "/song/main")
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ComicCollectionView {

    public static final String TAG = MainActivity.class.getName();

    private Toolbar toolbar;
    private long lastBackPressedTime;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1001;
    private ComicCollectionPresenter mPresenter;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private FrameLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //夜间模式一定要包含日间模式的配置文件：如color，style......
        setDayNightMode(isNightMode());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions();
        initView();
        initDrawer();
        setUpListener();

//        switchFragmentDelay(PhoenixListFragment.class.getName(), getResources().getString(R.string.phoenix_news), 0);
        swithFragmentByRouter("/song/phoenix/list", getResources().getString(R.string.phoenix_news));
        mPresenter = new ComicCollectionPresenter();
        mPresenter.attachVM(this, new ComicCollectionModel());
        mPresenter.getNewestCollectedComic();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (!hasPermission()) {
//                startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
//                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
//            }
//        }
        //获取渠道
//        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(this.getApplicationContext());
        if (channelInfo != null) {
            String channel = channelInfo.getChannel();
            Map<String, String> extraInfo = channelInfo.getExtraInfo();
            Log.d(TAG, "onCreate: " + channel + ";extra:" + extraInfo.get("installerId"));
            Log.d(TAG, "onCreate: " + channel + ";extra:" + extraInfo.get("extraId"));
        } else {
            Log.d(TAG, "onCreate: " + "null------");
        }

        Log.d(TAG, "flavors:APPLICATION_ID  " + BuildConfig.APPLICATION_ID);
        Log.d(TAG, "flavors:BUILD_TYPE  " + BuildConfig.BUILD_TYPE);
        Log.d(TAG, "flavors:FLAVOR  " + BuildConfig.FLAVOR);
        Log.d(TAG, "flavors:VERSION_NAME  " + BuildConfig.VERSION_NAME);
        Log.d(TAG, "flavors:DEBUG  " + BuildConfig.DEBUG);
        Log.d(TAG, "flavors:VERSION_CODE  " + BuildConfig.VERSION_CODE);
        Log.d(TAG, "flavors:preinstall  " + new DefaultPreinstallHandler().getPreinstallInfo());
    }


    public void verifyStoragePermissions() {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //检测用户是否对本app开启了“Apps with usage access”权限
//    private boolean hasPermission() {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) return false;
//        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
//                android.os.Process.myUid(), getPackageName());
//        return mode == AppOpsManager.MODE_ALLOWED;
//    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private void initView() {
        BindViewTools.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.logo_black);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mContent = findViewById(R.id.activity_framelayout_main);
    }

    private void initDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        StatusBarUtil.setColorForDrawerLayout(this, drawer, getResources().getColor(R.color.transparent));
        navigationView = (NavigationView) findViewById(R.id.navView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_news);
        navigationView.setItemIconTintList(null);
//        setDrawerLeftEdgeSize(this, drawer, 0.35f);
    }

    private void setUpListener() {
        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(v -> FunctionListActivity.Companion.start(MainActivity.this));
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

        switch (id) {
            case R.id.nav_gallery:
                swithFragmentByRouter("/song/comic/newest", getResources().getString(R.string.newest_comic));
                break;
            case R.id.nav_classify_comic:
                swithFragmentByRouter("/song/comic/classify", getResources().getString(R.string.classify_comic));
                break;
            case R.id.nav_video:
                swithFragmentByRouter("/song/video/tv","TV");
                break;
            case R.id.nav_rank_comic:
                swithFragmentByRouter("/song/comic/rank", getResources().getString(R.string.rank_comic));
                break;
            case R.id.nav_news:
                swithFragmentByRouter("/song/phoenix/list", getResources().getString(R.string.phoenix_news));
                break;
            case R.id.nav_collection:
                swithFragmentByRouter("/song/comic/collection", getResources().getString(R.string.collection_comic));
                break;
            case R.id.nav_flutter:
                startActivity(new Intent(this, FlutterComicActivity.class));
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (VideoManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        MessengerManager.getInstance().destroy(AppConfig.getApp());
        PushManager.getInstance().destroy(AppConfig.getApp());
        super.onDestroy();
    }

    private void switchFragmentDelay(final String className, final String title) {
        switchFragmentDelay(className, title, 300L);
    }

    private void switchFragmentDelay(final String className, final String title, long delayTime) {
        fab.setVisibility(TextUtils.equals(className, PhoenixListFragment.class.getName()) ? View.VISIBLE : View.GONE);
        getmHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchFragment(className, R.id.activity_framelayout_main);
                toolbar.setTitle(title);
            }
        }, delayTime);
    }

    private void swithFragmentByRouter(String routerUrl, String title) {
        fab.setVisibility(TextUtils.equals(routerUrl, "/song/phoenix/list") ? View.VISIBLE : View.GONE);

        getmHandler().postDelayed(() -> {
            toolbar.setTitle(title);
            Fragment fragment = (Fragment) ARouter.getInstance().build(routerUrl).navigation();
            switchFragment(fragment, R.id.activity_framelayout_main);
        }, 300);
    }

    @Override
    public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
        ComicLocalCollectionDao comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        List<ComicLocalCollection> list = comicLocalCollectionDao.loadAll();
        if (list == null || list.isEmpty() || collectionOnlineListBean == null || collectionOnlineListBean.getFavList().isEmpty())
            return;
        ArrayList<String> newList = new ArrayList<>();
        for (ComicLocalCollection bean : list) {
            for (ComicCollectionBean onlineBean : collectionOnlineListBean.getFavList()) {
                if (TextUtils.equals(bean.getComicId() + "", onlineBean.getComic_id())
                        && !TextUtils.equals(bean.getChapterNum(), onlineBean.getPass_chapter_num() + "")) {
                    newList.add(onlineBean.getName());
                }
            }
        }
        if (newList.isEmpty()) {
            return;
        }
        StringBuilder content = new StringBuilder();
        for (String name : newList) {
            content.append(name).append("、");
        }
        content = new StringBuilder(content.substring(0, content.length() - 1));
        content.append("有更新");
        showNotification(content.toString());
    }

    private void showNotification(String content) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Android 8.0 以后必须设置 channel 否则不显示通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("growth", "growth", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, ComicCollectionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100000),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Android 8.0 以后必须设置 channel 否则不显示通知
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "growth");
        nBuilder.setSmallIcon(R.mipmap.logo_black)
                .setContentIntent(pendingIntent)
                .setContentTitle("漫画有更新")
                .setContentText(content)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(false);
        Notification notification = nBuilder.build();
        notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS
                | Notification.FLAG_ONGOING_EVENT;

        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {

    }

}
