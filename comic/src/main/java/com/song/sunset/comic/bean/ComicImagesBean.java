package com.song.sunset.comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */
@Keep
public class ComicImagesBean implements Parcelable {
    /**
     * id : 4450239
     * sort : 0
     * width : 800
     * height : 1132
     * img05 : http://img4.u17i.com/17/08/99874/wp/10449554_1501636791_NeoFVQNuN1F4.65b4a_05.jpg
     * img50 : http://img4.u17i.com/17/08/99874/wp/10449554_1501636791_NeoFVQNuN1F4.65b4a_50.jpg
     */

    private String id;
    private String sort;
    private String width;
    private String height;
    private String img05;
    private String img50;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImg05() {
        return img05;
    }

    public void setImg05(String img05) {
        this.img05 = img05;
    }

    public String getImg50() {
        return img50;
    }

    public void setImg50(String img50) {
        this.img50 = img50;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sort);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeString(this.img05);
        dest.writeString(this.img50);
    }

    public ComicImagesBean() {
    }

    protected ComicImagesBean(Parcel in) {
        this.id = in.readString();
        this.sort = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.img05 = in.readString();
        this.img50 = in.readString();
    }

    public static final Parcelable.Creator<ComicImagesBean> CREATOR = new Parcelable.Creator<ComicImagesBean>() {
        @Override
        public ComicImagesBean createFromParcel(Parcel source) {
            return new ComicImagesBean(source);
        }

        @Override
        public ComicImagesBean[] newArray(int size) {
            return new ComicImagesBean[size];
        }
    };
}
