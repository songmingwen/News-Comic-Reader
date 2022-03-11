package com.song.sunset.phoenix.widget.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RemoteViews
import com.song.sunset.phoenix.R
import com.song.sunset.phoenix.bean.WidgetItem
import com.song.sunset.phoenix.holder.PhoenixBottomViewHolder.Companion.PHOENIX_NEWS_URL
import com.song.sunset.phoenix.widget.appwidget.PhoenixNewsService.Companion.EXTRA
import com.song.sunset.phoenix.widget.appwidget.PhoenixNewsService.Companion.EXTRA_BUNDLE

/**
 * Implementation of App Widget functionality.
 */
class PhoenixNewsWidget : AppWidgetProvider() {

    companion object {
        const val TAG: String = "PhoenixNewsWidget"
        const val ACTION_REFRESH: String = "com.song.sunset.action.refresh"
        const val ACTION_CLICK: String = "com.song.sunset.action.click"
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { updateAppWidget(context, appWidgetManager, it) }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "onReceive action = " + intent?.action)
        if (ACTION_CLICK == intent?.action) {
            val bundle = intent.getBundleExtra(EXTRA_BUNDLE)
            val widgetItem = bundle?.getSerializable(EXTRA) as WidgetItem
            val className = widgetItem.className
            Log.i(TAG, "onReceive,className=$className")
            className?.let {
                context?.startActivity(Intent().apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    setClassName(context, it)
                    putExtra(PHOENIX_NEWS_URL, widgetItem.jumpUrl)
                })
            }
        }
        if (ACTION_REFRESH == intent?.action) {
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)
            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.listview)
        }
        super.onReceive(context, intent)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        Log.i(TAG, "更新控件")

        val intent = Intent(context, PhoenixNewsService::class.java)
                .apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                }

        val remoteViews = RemoteViews(context.packageName, R.layout.phoenix_news_widget)
                .apply {
                    setTextViewText(R.id.title, "凤凰新闻")
                    setRemoteAdapter(R.id.listview, intent)
                // setEmptyView(R.id.listview, R.id.empty_view)
                }

        //刷新按钮
        val refreshPendingIntent = Intent(context, PhoenixNewsWidget::class.java).apply {
            action = ACTION_REFRESH
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }.let {
            PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        remoteViews.setOnClickPendingIntent(R.id.refresh, refreshPendingIntent)

        //设置一个待定 Intent。集合中的各个项目无法设置它们自己的待定 Intent，
        // 而是整个集合设置一个待定 Intent 模板，并且各个项目设置填充 Intent 来逐项创建唯一的行为
        val pendingIntent = Intent(context, PhoenixNewsWidget::class.java)
                .run {
                    action = ACTION_CLICK
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))

                    PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
                }
        remoteViews.setPendingIntentTemplate(R.id.listview, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

        // 延时发送刷新数据广播，进行数据刷新并触发数据请求
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                sendRefreshBroadcast(context, appWidgetId)
            }, 3000)
        }
    }

    private fun sendRefreshBroadcast(context: Context, appWidgetId: Int) {
        Log.i(TAG, "sendRefreshBroadcast")
        val refreshIntent = Intent(context, PhoenixNewsWidget::class.java)
                .apply {
                    action = ACTION_REFRESH
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }
        context.sendBroadcast(refreshIntent)
    }

}