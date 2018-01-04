package com.song.sunset.utils;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.song.sunset.beans.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2017/6/7 0007.
 * E-mail: z53520@qq.com
 */

public class MusicLoader {

    private static final String TAG = "com.example.nature.MusicLoader";

    private static MusicLoader musicLoader;

    //Uri，指向external的database
    private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            Media._ID,
            Media.DISPLAY_NAME,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 ";
    private String sortOrder = Media.DATA;

    public static MusicLoader instance() {
        if (musicLoader == null) {
            musicLoader = new MusicLoader();
        }
        return musicLoader;
    }

    //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
    private MusicLoader() {
    }

    private List<MusicInfo> loadMusic() {
        List<MusicInfo> musicList = new ArrayList<>();
        Cursor cursor = AppConfig.getApp().getContentResolver().query(contentUri, null, null, null, Media.DISPLAY_NAME);
        if (cursor == null) {
            Log.e(TAG, "MusicLoader: is null");
            return null;
        } else if (!cursor.moveToFirst()) {
            Log.e(TAG, "MusicLoader: is not first");
            return null;
        } else {
            try {
                int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
                int albumCol = cursor.getColumnIndex(Media.ALBUM);
                int idCol = cursor.getColumnIndex(Media._ID);
                int durationCol = cursor.getColumnIndex(Media.DURATION);
                int sizeCol = cursor.getColumnIndex(Media.SIZE);
                int artistCol = cursor.getColumnIndex(Media.ARTIST);
                int urlCol = cursor.getColumnIndex(Media.DATA);
                do {
                    String title = cursor.getString(displayNameCol);
                    String album = cursor.getString(albumCol);
                    long id = cursor.getLong(idCol);
                    int duration = cursor.getInt(durationCol);
                    long size = cursor.getLong(sizeCol);
                    String artist = cursor.getString(artistCol);
                    String url = cursor.getString(urlCol);

                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setId(id);
                    musicInfo.setTitle(title);
                    musicInfo.setAlbum(album);
                    musicInfo.setDuration(duration);
                    musicInfo.setSize(size);
                    musicInfo.setArtist(artist);
                    musicInfo.setUrl(url);
                    musicList.add(musicInfo);

                } while (cursor.moveToNext());
                return musicList;
            } catch (Exception e) {
                return null;
            } finally {
                cursor.close();
            }
        }
    }

    public List<MusicInfo> getMusicList() {
        return loadMusic();
    }

    public Uri getMusicUriById(long id) {
        return ContentUris.withAppendedId(contentUri, id);
    }
}
