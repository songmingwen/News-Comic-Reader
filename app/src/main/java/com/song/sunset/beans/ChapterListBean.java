package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ChapterListBean implements Parcelable {
    private String name;
    private String image_total;
    private String chapter_id;
    private String type;
    private String price;
    private String size;
    private String release_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_total() {
        return image_total;
    }

    public void setImage_total(String image_total) {
        this.image_total = image_total;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image_total);
        dest.writeString(this.chapter_id);
        dest.writeString(this.type);
        dest.writeString(this.price);
        dest.writeString(this.size);
        dest.writeString(this.release_time);
    }

    public ChapterListBean() {
    }

    protected ChapterListBean(Parcel in) {
        this.name = in.readString();
        this.image_total = in.readString();
        this.chapter_id = in.readString();
        this.type = in.readString();
        this.price = in.readString();
        this.size = in.readString();
        this.release_time = in.readString();
    }

    public static final Creator<ChapterListBean> CREATOR = new Creator<ChapterListBean>() {
        @Override
        public ChapterListBean createFromParcel(Parcel source) {
            return new ChapterListBean(source);
        }

        @Override
        public ChapterListBean[] newArray(int size) {
            return new ChapterListBean[size];
        }
    };
}
