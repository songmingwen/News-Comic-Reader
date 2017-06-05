package com.sunset.greendao.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Song on 2017/2/28.
 * E-mail:z53520@qq.com
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, ComicLocalCollectionDao.class);
    }
}
