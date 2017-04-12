package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by z5352 on 2017/4/12 0012.
 */

public class ComicsBean implements Parcelable {
    private String conTag;
    private String cover;
    private String name;
    private int comicId;
    private String description;
    private int flag;
    private String author;
    private List<String> tags;

    public String getConTag() {
        return conTag;
    }

    public void setConTag(String conTag) {
        this.conTag = conTag;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.conTag);
        dest.writeString(this.cover);
        dest.writeString(this.name);
        dest.writeInt(this.comicId);
        dest.writeString(this.description);
        dest.writeInt(this.flag);
        dest.writeString(this.author);
        dest.writeStringList(this.tags);
    }

    public ComicsBean() {
    }

    protected ComicsBean(Parcel in) {
        this.conTag = in.readString();
        this.cover = in.readString();
        this.name = in.readString();
        this.comicId = in.readInt();
        this.description = in.readString();
        this.flag = in.readInt();
        this.author = in.readString();
        this.tags = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ComicsBean> CREATOR = new Parcelable.Creator<ComicsBean>() {
        @Override
        public ComicsBean createFromParcel(Parcel source) {
            return new ComicsBean(source);
        }

        @Override
        public ComicsBean[] newArray(int size) {
            return new ComicsBean[size];
        }
    };
}
