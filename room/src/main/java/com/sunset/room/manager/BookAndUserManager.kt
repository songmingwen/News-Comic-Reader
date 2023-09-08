package com.sunset.room.manager

import com.song.sunset.base.AppConfig
import com.sunset.room.dao.BookAndUserDao
import com.sunset.room.database.BookAndUserDatabase
import com.sunset.room.entity.Book
import com.sunset.room.entity.User

/**
 * Desc:    bookanduser 数据库操作管理类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/8 9:45
 */
object BookAndUserManager {

    private val bookAndUserDao: BookAndUserDao
        get() {
            return BookAndUserDatabase.getInstance(AppConfig.getApp()).getDao()
        }

    fun insertOrUpdateUser(user: User) {
        val queryUser = bookAndUserDao.queryUserById(user.id)
        if (queryUser == null) {
            bookAndUserDao.insertUser(user)
        } else {
            bookAndUserDao.updateUser(user)
        }
    }

    fun insertOrUpdateBook(book: Book) {
        val queryBook = bookAndUserDao.queryBookById(book.id)
        if (queryBook == null) {
            bookAndUserDao.insertBook(book)
        } else {
            bookAndUserDao.updateBook(book)
        }
    }

    fun queryAllUsers(): List<User>? {
        return bookAndUserDao.queryAllUsers()
    }

    fun queryAllBooks(): List<Book>? {
        return bookAndUserDao.queryAllBooks()
    }

    fun deleteUserById(id: Long) {
        val user = bookAndUserDao.queryUserById(id)
        if (user != null) {
            bookAndUserDao.deleteUser(user)
        }
    }

    fun deleteBookById(id: Long) {
        val book = bookAndUserDao.queryBookById(id)
        if (book != null) {
            bookAndUserDao.deleteBook(book)
        }
    }

    fun clearAllUsers() {
        bookAndUserDao.clearUser()
    }

    fun clearAllBooks() {
        bookAndUserDao.clearBook()
    }
}