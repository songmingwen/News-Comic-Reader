package com.bubblepicker

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bubblepicker.adapter.BubblePickerAdapter
import com.bubblepicker.model.PickerItem
import com.song.sunset.R
import com.song.sunset.beans.User
import kotlinx.android.synthetic.main.activity_bubble.*

class BubbleActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, BubbleActivity::class.java))
        }
    }

    val list = arrayOf(
            User("songmingwen", "湖北", "110"),
            User("liuwei", "山东", "110"),
            User("duke", "湖北", "110"),
            User("lipengfei", "河南", "110"),
            User("huge", "上海", "110"),
            User("pengyuyan", "台湾", "110"),
            User("wuweibo", "山东", "110"),
            User("wangsongxiang", "浙江", "110"),
            User("luyezhang", "湖北", "110"),
            User("tanxiao", "四川", "110"),
            User("yangyufei", "山东", "110"),
            User("duweigang", "陕西", "110"),
            User("zhuyongqing", "青海", "110"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            subtitleTextView.letterSpacing = 0.06f
            hintTextView.letterSpacing = 0.05f
        }

        picker.adapter = object : BubblePickerAdapter {

            override val totalCount: Int
                get() = list.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = list[position].userName
                    textColor = ContextCompat.getColor(this@BubbleActivity, android.R.color.white)
                    color = this@BubbleActivity.resources.getColor(R.color.colorAccent)
                    customData = list[position]
                }
            }
        }


        picker.bubbleSize = 20
        picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                val user = item.customData as User
                user.selected = true
                toast("${user.userName} selected")
            }

            override fun onBubbleDeselected(item: PickerItem) {
                val user = item.customData as User
                user.selected = false
                toast("${user.userName} selected")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}