package com.song.sunset.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


/**
 * @author songmingwen
 * @description
 * @since 2020/3/18
 */
class SimpleWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object {
        const val TAG: String = "SimpleWorker"
    }

    override fun doWork(): Result {
        Log.i(TAG, "后台任务干了一些事")

        val params1 = inputData.getInt("params1", 0)
        val params2 = inputData.getString("params2")
        Log.d(TAG, "获得参数:$params1,$params2")

        val resultData: Data = Data.Builder()
                .putString("result", "success").build()

        return Result.success(resultData)
    }

    override fun onStopped() {
        Log.i(TAG, "onStopped")
        super.onStopped()
    }
}