package com.sunset.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Desc:    书
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/7 10:29
 */
@Entity(tableName = "book")
data class Book(@PrimaryKey(autoGenerate = false) var id: Long,
                @ColumnInfo(name = "book_name") var name: String) {

    companion object {
        const val UNKNOWN_STR: String = "unknown_value"
    }

    @ColumnInfo(name = "book_author")
    var authorName: String = UNKNOWN_STR

    override fun toString(): String {
        return "Book(name='$name', id=$id, authorName='$authorName')"
    }

}