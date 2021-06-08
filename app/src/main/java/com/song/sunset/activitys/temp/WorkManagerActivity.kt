package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.work.*
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.work.SimpleWorker
import kotlinx.android.synthetic.main.activity_workmanager.*
import java.util.concurrent.TimeUnit
import com.song.sunset.R

class WorkManagerActivity : BaseActivity() {
    companion object {
        const val TAG = "WorkManagerActivity"

        fun start(context: Context) {
            context.startActivity(Intent(context, WorkManagerActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workmanager)

        //约束
        val constraints: Constraints = Constraints.Builder()
                .setRequiresDeviceIdle(true) //指定{@link WorkRequest}运行时设备是否为空闲
                .setRequiresCharging(true) //指定要运行的{@link WorkRequest}是否应该插入设备
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true) //指定设备电池是否不应低于临界阈值
                .setRequiresCharging(true) //网络状态
                .setRequiresStorageNotLow(true) //指定设备可用存储是否不应低于临界阈值
                .build()

        val data: Data = Data.Builder().putInt("params1", 1).putString("params2", "hello").build()

        val once = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()

        val periodic = PeriodicWorkRequest.Builder(SimpleWorker::class.java, 15, TimeUnit.MINUTES)
                .setInputData(data)
//                .setConstraints(constraints)//添加约束
                .build()

        val list = ArrayList<WorkRequest>()
        list.add(once)
        list.add(periodic)

        addListener(once, periodic)

        start_once.setOnClickListener { WorkManager.getInstance().enqueue(once) }
        start_periodic.setOnClickListener { WorkManager.getInstance().enqueue(periodic) }
        cancel_periodic.setOnClickListener { WorkManager.getInstance().cancelWorkById(periodic.id) }

    }

    private fun addListener(once: OneTimeWorkRequest, periodic: PeriodicWorkRequest) {
        WorkManager.getInstance().getWorkInfoByIdLiveData(once.id).observe(this, Observer {
            when (it.state) {
                WorkInfo.State.RUNNING -> {
                    Log.i(TAG, "RUNNING")
                }
                WorkInfo.State.ENQUEUED -> {
                    Log.i(TAG, "ENQUEUED")
                }
                WorkInfo.State.CANCELLED -> {
                    Log.i(TAG, "CANCELLED")
                }
                WorkInfo.State.SUCCEEDED -> {
                    Log.i(TAG, "SUCCEEDED")
                    Log.i(TAG, "SUCCEEDED：" + it.outputData.getString("result"))
                }
                WorkInfo.State.FAILED -> {
                    Log.i(TAG, "FAILED")
                }
                WorkInfo.State.BLOCKED -> {
                    Log.i(TAG, "BLOCKED")
                }
                else -> {
                    Log.i(TAG, "else")
                }
            }
        })

        WorkManager.getInstance().getWorkInfoByIdLiveData(periodic.id).observe(this, Observer {
            when (it.state) {
                WorkInfo.State.RUNNING -> {
                    Log.i(TAG, "RUNNING")
                }
                WorkInfo.State.ENQUEUED -> {
                    Log.i(TAG, "ENQUEUED")
                }
                WorkInfo.State.CANCELLED -> {
                    Log.i(TAG, "CANCELLED")
                }
                WorkInfo.State.SUCCEEDED -> {
                    Log.i(TAG, "SUCCEEDED")
                    Log.i(TAG, "SUCCEEDED：" + it.outputData.getString("result"))
                }
                WorkInfo.State.FAILED -> {
                    Log.i(TAG, "FAILED")
                }
                WorkInfo.State.BLOCKED -> {
                    Log.i(TAG, "BLOCKED")
                }
                else -> {
                    Log.i(TAG, "else")
                }
            }
        })
    }

}
