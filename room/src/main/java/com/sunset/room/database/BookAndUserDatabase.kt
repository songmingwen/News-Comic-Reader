package com.sunset.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sunset.room.dao.BookAndUserDao
import com.sunset.room.entity.Book
import com.sunset.room.entity.User

/**
 * Desc:    user database 类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/7 10:08
 */
@Database(entities = [User::class, Book::class], version = 1, exportSchema = false)
abstract class BookAndUserDatabase : RoomDatabase() {

    abstract fun getDao(): BookAndUserDao

    companion object {

        @Volatile
        private var INSTANCE: BookAndUserDatabase? = null

        fun getInstance(context: Context): BookAndUserDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                BookAndUserDatabase::class.java,
                                "sunset_book_and_user")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE as BookAndUserDatabase
        }
    }
}