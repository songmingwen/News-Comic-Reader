package com.song.sunset.activitys.temp

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.base.api.WholeApi
import com.song.sunset.base.net.Net.createService
import com.song.sunset.phoenix.api.MGTVApi
import com.tencent.mmkv.MMKV

/**
 * Desc:    芒果发送私信页面
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/11/16 10:18
 */
@Route(path = "/song/sendmsg")
class SendMgtvMsgActivity : BaseActivity() {

    companion object {

        const val RECEIVE_UID = "receive_uid"

    }

    private val mmkv: MMKV by lazy { MMKV.defaultMMKV() }

    private var sendUid: EditText? = null
    private var content: EditText? = null
    private var receiveUid: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mgtv_msg)

        val receiveUidStr = mmkv.getString(RECEIVE_UID, "")

        sendUid = findViewById(R.id.edit_send_uid)
        content = findViewById(R.id.edit_content)
        receiveUid = findViewById(R.id.edit_receive_uid)

        if (!TextUtils.isEmpty(receiveUidStr)) {
            receiveUid?.setText(receiveUidStr)
        }
    }

    fun sendSingle(view: View?) {
        if (TextUtils.isEmpty(getSendUid())
                || TextUtils.isEmpty(getContent())
                || TextUtils.isEmpty(getReceiveUid())) {
            Toast.makeText(this, "请输入：发送者用户 ID 、私信内容、接收者用户 ID", Toast.LENGTH_SHORT).show()
            return
        }
        mmkv.encode(RECEIVE_UID, getReceiveUid())
        send(getSendUid(), getSendUid(), getReceiveUid(), getContent())
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show()
    }

    fun sendBatch(view: View?) {
        if (TextUtils.isEmpty(getReceiveUid())) {
            Toast.makeText(this, "请输入：接收者用户 ID", Toast.LENGTH_SHORT).show()
            return
        }
        for (i in 20000..20029) {
            send(i.toString() + "", i.toString() + "", getReceiveUid(), i.toString() + "")
        }
        mmkv.encode(RECEIVE_UID, getReceiveUid())
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show()
    }

    private fun send(sendUid: String, selfUid: String, targetDid: String, content: String) {
        createService(MGTVApi::class.java, WholeApi.MGTV_IM)
                .sendMsgSingle(sendUid, selfUid, targetDid, content)
                .compose(bindLifecycleAndScheduler()).subscribe()
    }

    private fun getContent(): String {
        return content?.text.toString().trim()
    }

    private fun getSendUid(): String {
        return sendUid?.text.toString().trim()
    }

    private fun getReceiveUid(): String {
        return receiveUid?.text.toString().trim()
    }
}