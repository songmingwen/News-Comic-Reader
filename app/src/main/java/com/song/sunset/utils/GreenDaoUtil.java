package com.song.sunset.utils;

import android.database.sqlite.SQLiteDatabase;

import com.song.sunset.base.AppConfig;
import com.sunset.greendao.gen.DaoMaster;
import com.sunset.greendao.gen.DaoSession;
import com.sunset.greendao.gen.MySQLiteOpenHelper;


/**
 * Created by Song on 2017/2/28.
 * E-mail:z53520@qq.com
 */

public class GreenDaoUtil {

    //    private static DaoMaster.DevOpenHelper mDBHelper;
    private static SQLiteDatabase db;
    private static DaoSession mDaoSession;

    /**
     * 设置greenDao
     */
    public static void initGreenDao() {
//        mDBHelper = new DaoMaster.DevOpenHelper(AppConfig.getApp(), "notes-dbUser", null);
//        db = mDBHelper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(AppConfig.getApp(), "notes-dbUser", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
