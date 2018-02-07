package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2018/2/7 0007.
 * E-mail: z53520@qq.com
 */

public class VideoListsTypeBean implements Parcelable {
    private String id;
    private String name;
    private String chType;
    private String position;
    private String type;
    private String api;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChType() {
        return chType;
    }

    public void setChType(String chType) {
        this.chType = chType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.chType);
        dest.writeString(this.position);
        dest.writeString(this.type);
        dest.writeString(this.api);
    }

    public VideoListsTypeBean() {
    }

    protected VideoListsTypeBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.chType = in.readString();
        this.position = in.readString();
        this.type = in.readString();
        this.api = in.readString();
    }

    public static final Parcelable.Creator<VideoListsTypeBean> CREATOR = new Parcelable.Creator<VideoListsTypeBean>() {
        @Override
        public VideoListsTypeBean createFromParcel(Parcel source) {
            return new VideoListsTypeBean(source);
        }

        @Override
        public VideoListsTypeBean[] newArray(int size) {
            return new VideoListsTypeBean[size];
        }
    };
}
