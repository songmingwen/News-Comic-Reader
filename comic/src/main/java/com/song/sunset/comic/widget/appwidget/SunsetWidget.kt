package com.song.sunset.comic.widget.appwidget

import android.appwidget.AppWidgetProvider
import android.content.Intent
import android.appwidget.AppWidgetManager
import android.widget.RemoteViews
import com.song.sunset.comic.R
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import com.song.sunset.comic.ComicCollectionActivity

/**
 * Implementation of App Widget functionality.
 */
class SunsetWidget : AppWidgetProvider() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        Log.i("SunsetWidget", appWidgetIds.contentToString())
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        const val FROM_WIDGET = "com.song.sunset.from_widget"
        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                            appWidgetId: Int) {

            //图标一
            val views = RemoteViews(context.packageName, R.layout.sunset_widget)
            val intent = Intent()
            intent.setClassName(context, "com.song.sunset.activitys.MainActivity")
            val newestComic = PendingIntent.getActivity(context, (Math.random() * 100000).toInt(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.appwidget_newest_comic, newestComic)

            //图标二
            val pendingIntent = Intent(context, ComicCollectionActivity::class.java)
                    .let {
                        PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
                    }
            views.setOnClickPendingIntent(R.id.appwidget_collected_comic, pendingIntent)

            //图标三
            val news = PendingIntent.getActivity(context, (Math.random() * 100000).toInt(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.appwidget_news, news)

            //图标四
            val videoPendingIntent = Intent().apply {
                setClassName(context, "com.song.sunset.activitys.VideoListActivity")
                putExtra(FROM_WIDGET, true)
            }.let {
                PendingIntent.getActivity(context, (Math.random() * 100000).toInt(),
                        it, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            views.setOnClickPendingIntent(R.id.appwidget_news_video, videoPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}