package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/6/7 0007.
 * E-mail: z53520@qq.com
 */

public class MusicInfo implements Parcelable {

    private long id;
    private String title;
    private String album;
    private int duration;
    private long size;
    private String artist;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.album);
        dest.writeInt(this.duration);
        dest.writeLong(this.size);
        dest.writeString(this.artist);
        dest.writeString(this.url);
    }

    public MusicInfo() {
    }

    protected MusicInfo(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.album = in.readString();
        this.duration = in.readInt();
        this.size = in.readLong();
        this.artist = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MusicInfo> CREATOR = new Parcelable.Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel source) {
            return new MusicInfo(source);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override
    public String toString() {
        return "MusicInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
