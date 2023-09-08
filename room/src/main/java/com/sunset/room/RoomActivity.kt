package com.sunset.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.song.sunset.addButton
import com.sunset.room.dao.BookAndUserDao
import com.sunset.room.database.BookAndUserDatabase
import com.sunset.room.entity.Book
import com.sunset.room.entity.User
import com.sunset.room.manager.BookAndUserManager
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Desc:    room 数据库测试
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/7 8:53
 */
class RoomActivity : AppCompatActivity() {

    companion object {

        const val TAG = "RoomActivity"

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RoomActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        button_container.apply {
            addButton("添加或更新数据") { GlobalScope.launch(Dispatchers.IO) { insert() } }
            addButton("删除数据") { GlobalScope.launch(Dispatchers.IO) { delete() } }
            addButton("查询所有用户") { GlobalScope.launch(Dispatchers.IO) { queryUser() } }
            addButton("查询所有书籍") { GlobalScope.launch(Dispatchers.IO) { queryBook() } }
            addButton("查询所有用户对应的书籍") { GlobalScope.launch(Dispatchers.IO) { queryUserBook() } }
            addButton("清除所有数据") { GlobalScope.launch(Dispatchers.IO) { clear() } }
        }
    }

    private fun insert() {
        val user1 = User(10000, "曹雪芹")
        val user2 = User(10001, "罗贯中")
        val user3 = User(10002, "施耐庵")
        val user4 = User(10003, "吴承恩")
        BookAndUserManager.insertOrUpdateUser(user1)
        BookAndUserManager.insertOrUpdateUser(user2)
        BookAndUserManager.insertOrUpdateUser(user3)
        BookAndUserManager.insertOrUpdateUser(user4)

        val book1 = Book(10000000, "三国演义").apply { authorName = "罗贯中1" }
        val book2 = Book(10000001, "水浒传").apply { authorName = "施耐庵1" }
        val book3 = Book(10000002, "红楼梦").apply { authorName = "曹雪芹1" }
        val book4 = Book(10000003, "西游记").apply { authorName = "罗贯中" }
        val book5 = Book(10000004, "废艺斋集稿").apply { authorName = "曹雪芹" }
        val book6 = Book(10000005, "画石").apply { authorName = "曹雪芹" }
        val book7 = Book(10000006, "三遂平妖传").apply { authorName = "罗贯中" }
        BookAndUserManager.insertOrUpdateBook(book1)
        BookAndUserManager.insertOrUpdateBook(book2)
        BookAndUserManager.insertOrUpdateBook(book3)
        BookAndUserManager.insertOrUpdateBook(book4)
        BookAndUserManager.insertOrUpdateBook(book5)
        BookAndUserManager.insertOrUpdateBook(book6)
        BookAndUserManager.insertOrUpdateBook(book7)
    }

    private fun delete() {
        BookAndUserManager.deleteUserById(10000)
        BookAndUserManager.deleteBookById(10000000)
    }

    private fun queryUser() {
        val list = BookAndUserManager.queryAllUsers()
        if (list != null) {
            for (user in list) {
                Log.i(TAG, "user:$user")
            }
        }
    }

    private fun queryBook() {
        val list = BookAndUserManager.queryAllBooks()
        if (list != null) {
            for (book in list) {
                Log.i(TAG, "book:$book")
            }
        }
    }

    private fun queryUserBook() {
//        val map = bookAndUserDao.getUserAllBook()
//        if (map != null) {
//            for (ma in map) {
//                val list = ma.value
//                val user = ma.key
//                Log.i(TAG, "user:$user")
//                for (book in list) {
//                    Log.i(TAG, "book:$book")
//                }
//            }
//        }
    }

    private fun clear() {
        BookAndUserManager.clearAllUsers()
        BookAndUserManager.clearAllBooks()
    }
}