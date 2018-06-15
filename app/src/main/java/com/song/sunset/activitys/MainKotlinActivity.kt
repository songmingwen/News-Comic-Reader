package com.song.sunset.activitys

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.NotificationCompat
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.song.core.statusbar.StatusBarUtil
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.beans.CollectionOnlineListBean
import com.song.sunset.beans.MusicInfo
import com.song.sunset.fragments.*
import com.song.sunset.mvp.models.ComicCollectionModel
import com.song.sunset.mvp.presenters.ComicCollectionPresenter
import com.song.sunset.mvp.views.ComicCollectionView
import com.song.sunset.services.impl.BinderPoolImpl
import com.song.sunset.services.impl.MusicCallBackListenerImpl
import com.song.sunset.services.impl.MusicGetterImpl
import com.song.sunset.services.managers.BinderPool
import com.song.sunset.services.managers.MessengerManager
import com.song.sunset.services.managers.PushManager
import com.song.sunset.utils.GreenDaoUtil
import com.song.sunset.utils.SPUtils
import com.song.sunset.utils.process.AndroidProcesses
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList

class MainKotlinActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, ComicCollectionView {
    val TAG = MainKotlinActivity::class.java.name!!

    private var lastBackPressedTime: Long = 0
    private val MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1001
    private var mPresenter: ComicCollectionPresenter? = null
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        //夜间模式一定要包含日间模式的配置文件：如color，style......
        setDayNightMode(isNightMode)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyStoragePermissions()
        initView()
        initDrawer()
        setUpListener()

        switchFragmentDelay(PhoenixListFragment::class.java.name, resources.getString(R.string.phoenix_news), 0)
        mPresenter = ComicCollectionPresenter()
        mPresenter!!.attachVM(this, ComicCollectionModel())
        mPresenter!!.getNewestCollectedComic()
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            if (!hasPermission()) {
        //                startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
        //                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
        //            }
        //        }
    }


    private fun verifyStoragePermissions() {
        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
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

    override fun swipeBackPriority(): Boolean {
        return false
    }

    private fun initView() {
        toolbar!!.setLogo(R.mipmap.logo)
    }

    private fun initDrawer() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        StatusBarUtil.setColorForDrawerLayout(this, drawer, resources.getColor(R.color.transparent))
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navView!!.setCheckedItem(R.id.nav_news)
        navView!!.itemIconTintList = null
        setDrawerLeftEdgeSize(this, drawer, 0.35f)
    }

    private fun setUpListener() {
        navView!!.setNavigationItemSelectedListener(this)

        fab!!.setOnClickListener {
            //                MainActivity.this.startActivity(new Intent(MainActivity.this, SubScaleViewActivity.class));
            //                MainActivity.this.startActivity(new Intent(MainActivity.this, TouchEventTestActivity.class));
            this@MainKotlinActivity.startActivity(Intent(this@MainKotlinActivity, TempTestActivity::class.java))
            //                MainActivity.this.startActivity(new Intent(MainActivity.this, TransTestActivity.class));
            //                ScrollingActivity.start(MainActivity.this);
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
            //                PushManager.getInstance().sendMusicInfo(MusicLoader.instance().getMusicList().get(0));
            //                MessengerManager.getInstance().sendMessage();

            //                MusicGetterManager.getInstance().setMusicCallBackListener(new MusicGetterManager.MusicCallBackListener() {
            //                    @Override
            //                    public void success(List<MusicInfo> list) {
            //                        Log.i(TAG + "callback", list.toString());
            //                    }
            //
            //                    @Override
            //                    public void failure() {
            //                        Log.i(TAG, "false");
            //                    }
            //                });
            //                MusicGetterManager.getInstance().getMusicLists();

//            useBinderPool()

            //                Log.i(TAG, "Weeks.SUNDAY.getDate() = " + Weeks.SUNDAY.getDate());

            //                Log.i("music_list: ", MusicLoader.instance(MainActivity.this.getContentResolver()).getMusicList().toString());

            //                switchDayNightMode();

            //                PrintProcess();
            //                getTopApp();
            //                Log.i("recent_song", getTaskList());
        }
    }

    fun getTaskList(): String {
        var apps = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return apps
        }
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pm = packageManager
        return try {
            val list = am.getRecentTasks(64, 0)
            list.asSequence()
                    .map { it.baseIntent }
                    .mapNotNull { pm.resolveActivity(it, 0) }
                    .forEach { apps = if (apps == "") it.loadLabel(pm).toString() + "" else apps + "," + it.loadLabel(pm) }
            apps
        } catch (se: SecurityException) {
            se.printStackTrace()
            apps
        }

    }

    private fun PrintProcess() {
        val processes = AndroidProcesses.getRunningAppProcesses()

        for (process in processes) {
            Log.d("process_song", process.packageName)
        }
    }

    private fun getTopApp() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val m = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val now = System.currentTimeMillis()
            //获取600秒之内的应用数据
            val stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 600 * 1000, now)
            Log.i("song", "Running app number in last 600 seconds : " + stats.size)

            //取得最近运行的一个app，即当前运行的app
            if (!stats.isEmpty()) {
                for (i in stats.indices) {
                    Log.i("song", "top running app is : " + stats[i].packageName)
                }
            }
        }
    }

    /**
     * 使用binderPool过去对应的binder并且执行相应的方法（回调中获取结果，<异步>）
     */
    private fun useBinderPool() {
        val iBinder = BinderPool.getInstance().queryBinder(BinderPoolImpl.BINDER_GET_MUSIC)
        val iMusicGetter = MusicGetterImpl.asInterface(iBinder)
        try {
            iMusicGetter.getMusicList(mIMusicCallBackListener)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    private val mIMusicCallBackListener = object : MusicCallBackListenerImpl() {
        @Throws(RemoteException::class)
        override fun success(list: List<MusicInfo>) {
            super.success(list)
            Log.i(MusicCallBackListenerImpl.TAG + "MainActivity", list.toString())
        }

        @Throws(RemoteException::class)
        override fun failure() {
            Log.i(MusicCallBackListenerImpl.TAG + "MainActivity", "get music failure")
        }
    }

    private fun RecursiveTest() {
        val start2 = System.currentTimeMillis()
        val result2 = getPlus(1000L)
        val end2 = System.currentTimeMillis()
        Log.i("结果对比", "result2=" + result2 + "; time2 = " + (end2 - start2) + "millis")

        val start1 = System.currentTimeMillis()

        Observable.just(1000L)
                .map { aLong -> getOrderPlus(aLong!!) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aLong ->
                    val result1 = aLong!!
                    val end1 = System.currentTimeMillis()
                    Log.i("结果对比", "result1=" + result1 + "; time1 = " + (end1 - start1) + "millis")
                }
    }

    private fun getFactorial(endNum: Long): Long {
        return if (endNum <= 1) {
            1
        } else {
            getFactorial(endNum - 1) * endNum
        }
    }

    /**
     * Java没有实现编译器尾递归的优化
     *
     * @param endNum
     * @return
     */
    private fun getOrderPlus(endNum: Long): Long {
        return if (endNum == 1L) 1 else getOrderPlus(endNum, 1)
    }

    private fun getOrderPlus(endNum: Long, sum: Long): Long {
        return if (endNum == 1L) sum else getOrderPlus(endNum - 1, sum + endNum)
    }

    private fun getPlus(endNum: Long): Long {
        val sum = (0 until endNum + 1).sumBy { it.toInt() }
        return sum.toLong()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.nav_gallery -> switchFragmentDelay(ComicGenericListFragment::class.java.name, resources.getString(R.string.newest_comic))
            R.id.nav_classify_comic -> switchFragmentDelay(ComicClassifyFragment::class.java.name, resources.getString(R.string.classify_comic))
            R.id.nav_video -> VideoListActivity.start(this)
            R.id.nav_rank_comic -> switchFragmentDelay(ComicRankFragment::class.java.name, resources.getString(R.string.rank_comic))
            R.id.nav_news -> switchFragmentDelay(PhoenixListFragment::class.java.name, resources.getString(R.string.phoenix_news))
            R.id.nav_collection -> switchFragmentDelay(CollectionFragment::class.java.name, resources.getString(R.string.collection_comic))
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressedSupport() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
                //                moveTaskToBack(true);
                SPUtils.setBooleanByName(this, SPUtils.APP_FIRST_INSTALL, false)
                finish()
            } else {
                lastBackPressedTime = System.currentTimeMillis()
            }
        }
    }

    override fun onDestroy() {
        if (mPresenter != null) {
            mPresenter!!.detachVM()
        }
        MessengerManager.getInstance().destroy(this)
        PushManager.getInstance().destory(this)
        super.onDestroy()
    }

    private fun switchFragmentDelay(className: String, title: String) {
        switchFragmentDelay(className, title, 300L)
    }

    private fun switchFragmentDelay(className: String, title: String, delayTime: Long) {
        fab!!.visibility = if (TextUtils.equals(className, PhoenixListFragment::class.java.name)) View.VISIBLE else View.GONE
        getmHandler().postDelayed({
            switchFragment(className, R.id.activity_framelayout_main)
            toolbar!!.title = title
        }, delayTime)
    }

    override fun onSuccess(collectionOnlineListBean: CollectionOnlineListBean?) {
        val comicLocalCollectionDao = GreenDaoUtil.getDaoSession().comicLocalCollectionDao
        val list = comicLocalCollectionDao.loadAll()
        if (list == null || list.isEmpty() || collectionOnlineListBean == null || collectionOnlineListBean.favList.isEmpty())
            return
        val newList = ArrayList<String>()
        for (bean in list) {
            collectionOnlineListBean.favList
                    .filter {
                        TextUtils.equals(bean.comicId.toString(), it.comic_id)
                                && !TextUtils.equals(bean.chapterNum, it.pass_chapter_num.toString())
                    }
                    .mapTo(newList) {
                        it.name
                    }
        }
        if (newList.isEmpty()) {
            return
        }
        var content = StringBuilder()
        for (name in newList) {
            content.append(name).append("、")
        }
        content = StringBuilder(content.substring(0, content.length - 1))
        content.append("有更新")
        showNotification(content.toString())
    }

    override fun onFailure(errorCode: Int, errorMsg: String) {

    }

    private fun showNotification(content: String) {
        val intent = Intent(this, ComicCollectionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, (Math.random() * 100000).toInt(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val nBuilder = NotificationCompat.Builder(this)
        nBuilder.setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent)
                .setContentTitle("漫画有更新")
                .setContentText(content)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(false)
        val notification = nBuilder.build()
        notification.flags = (notification.flags or Notification.FLAG_AUTO_CANCEL
                or Notification.FLAG_SHOW_LIGHTS
                or Notification.FLAG_ONGOING_EVENT)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }

}
