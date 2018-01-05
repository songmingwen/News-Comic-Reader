package com.song.sunset.receivers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicCollectionActivity;
import com.song.sunset.activitys.MainActivity;
import com.song.sunset.activitys.VideoListActivity;
import com.squareup.haha.perflib.Main;

import java.util.Arrays;

/**
 * Implementation of App Widget functionality.
 */
public class SunsetWidget extends AppWidgetProvider {

    public static final String FROM = "com.song.sunset.from";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sunset_widget);

        PendingIntent newestComic = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_newest_comic, newestComic);

        Intent collectedComicIntent = new Intent(context, ComicCollectionActivity.class);
        collectedComicIntent.putExtra(FROM, true);
        PendingIntent collectedComic = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                collectedComicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_collected_comic, collectedComic);

        PendingIntent news = PendingIntent.getActivity(context, (int) (Math.random() * 100000),
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_news, news);

        Intent newIntent = new Intent(context, VideoListActivity.class);
        newIntent.putExtra(FROM, true);
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

