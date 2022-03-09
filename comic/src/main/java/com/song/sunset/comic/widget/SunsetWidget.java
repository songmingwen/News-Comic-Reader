package com.song.sunset.comic.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.song.sunset.comic.ComicCollectionActivity;
import com.song.sunset.comic.R;

import java.util.Arrays;

/**
 * Implementation of App Widget functionality.
 */
public class SunsetWidget extends AppWidgetProvider {

    public static final String FROM_WIDGET = "com.song.sunset.from_widget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sunset_widget);

        Intent intent = new Intent();
        intent.setClassName(context, "com.song.sunset.activitys.MainActivity");

        PendingIntent newestComic = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_newest_comic, newestComic);

        Intent collectedComicIntent = new Intent(context, ComicCollectionActivity.class);
        collectedComicIntent.putExtra(FROM_WIDGET, true);
        PendingIntent collectedComic = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                collectedComicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_collected_comic, collectedComic);

        PendingIntent news = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_news, news);

        Intent newIntent = new Intent();
        newIntent.setClassName(context, "com.song.sunset.activitys.VideoListActivity");
        newIntent.putExtra(FROM_WIDGET, true);
        PendingIntent newsVideo = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_news_video, newsVideo);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.i("SunsetWidget", Arrays.toString(appWidgetIds));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

