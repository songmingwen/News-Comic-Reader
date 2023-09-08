package com.sunset.room.dao

import androidx.room.*
import com.sunset.room.entity.Book
import com.sunset.room.entity.User

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/7 11:02
 */
@Dao
interface BookAndUserDao {

    /*** -------------------------- user 相关 --------------------------*/
    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertUsers(userList: List<User>)

    @Update
    fun updateUser(user: User)

    @Delete()
    fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    fun queryAllUsers(): List<User>?

    @Query("SELECT * FROM user WHERE id = :id")
    fun queryUserById(id: Long): User?

    @Query("SELECT * FROM user WHERE user_name = :name")
    fun queryUserByName(name: String): List<User>?

    @Query("SELECT * FROM user WHERE user_age = :age")
    fun queryUserByAge(age: Int): List<User>?

    @Query("SELECT * FROM user WHERE user_gender = :gender")
    fun queryUserByGender(gender: Int): List<User>?

    @Query("SELECT * FROM user WHERE user_nationality = :nationality")
    fun queryUserByNationality(nationality: String): List<User>?

    /**
     * 获取每个人出版的所有书籍
     */
//    @Query("SELECT * FROM user Join book on user_name = book_author")
//    fun getUserAllBook(): Map<User, List<Book>>?

    @Query("DELETE FROM user")
    fun clearUser()

    /*** -------------------------- book 相关 --------------------------*/

    @Insert
    fun insertBook(book: Book)

    @Insert
    fun insertBooks(bookList: List<Book>)

    @Update
    fun updateBook(book: Book)

    @Query("SELECT * FROM book WHERE id = :id")
    fun queryBookById(id: Long): Book?

    @Query("SELECT * FROM book WHERE book_name = :bookName")
    fun queryBookByName(bookName: String): Book?

    @Query("SELECT * FROM book")
    fun queryAllBooks(): List<Book>?

    @Query("SELECT * FROM book WHERE book_author = :authorName")
    fun queryBookByAuthor(authorName: String): List<Book>?

    @Delete()
    fun deleteBook(book: Book)

    @Query("DELETE FROM book")
    fun clearBook()
}