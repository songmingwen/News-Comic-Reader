package com.song.sunset.activitys;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.R;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.sunset.greendao.gen.DaoMaster;
import com.sunset.greendao.gen.DaoSession;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class SunsetApplication extends Application {

    private DaoMaster.DevOpenHelper mUserHelper;
    private SQLiteDatabase dbUser;
    private DaoMaster mUserDaoMaster;
    private DaoSession mUserDaoSession;

//    private DaoMaster.DevOpenHelper mCoderHelper;
//    private SQLiteDatabase dbCoder;
//    private DaoMaster mCoderDaoMaster;
//    private DaoSession mCoderDaoSession;

    private static SunsetApplication sunsetApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sunsetApplication = this;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setUserDatabase();
            }
        });
//        setCoderDatabase();
        AppConfig.setApp(this);
        initLoadingAndRetryLayout();
    }

    private void initLoadingAndRetryLayout() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry_view;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading_view;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty_view;
    }

    public static SunsetApplication getSunsetApplication() {
        return sunsetApplication;
    }

    /**
     * 设置greenDao
     */
    private void setUserDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mUserHelper = new DaoMaster.DevOpenHelper(this, "notes-dbUser", null);
        dbUser = mUserHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mUserDaoMaster = new DaoMaster(dbUser);
        mUserDaoSession = mUserDaoMaster.newSession();
    }
//
//    private void setCoderDatabase() {
//        mCoderHelper = new DaoMaster.DevOpenHelper(this,"notes-dbCoder",null);
//        dbCoder = mCoderHelper.getWritableDatabase();
//        mCoderDaoMaster = new DaoMaster(dbCoder);
//        mCoderDaoSession = mCoderDaoMaster.newSession();
//    }
//
    public DaoSession getUserDaoSession() {
        return mUserDaoSession;
    }
//
    public SQLiteDatabase getDbUser() {
        return dbUser;
    }
//
//    public DaoSession getCoderDaoSession(){
//        return mCoderDaoSession;
//    }
}
