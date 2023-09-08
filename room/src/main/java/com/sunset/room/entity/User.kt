package com.sunset.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Desc:    人
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/7 9:35
 */
@Entity(tableName = "user")
data class User(
        @PrimaryKey(autoGenerate = false) var id: Long,
        @ColumnInfo(name = "user_name") var name: String) {

    companion object {
        const val UNKNOWN_INT: Int = -10086
        const val UNKNOWN_STR: String = "unknown_value"
    }

    @ColumnInfo(name = "user_age")
    var age: Int = UNKNOWN_INT

    /*** 0：女，1：男，2：无性别，3：女变男，4：男变女 ，5：直升机性别，6：加特林性别*/
    @ColumnInfo(name = "user_gender")
    var gender: Int = UNKNOWN_INT

    @ColumnInfo(name = "user_nationality")
    var nationality: String = UNKNOWN_STR

    override fun toString(): String {
        return "User(id='$id', name='$name', age=$age, gender=$gender, nationality='$nationality')"
    }

}
