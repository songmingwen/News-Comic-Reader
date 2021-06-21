package com.song.sunset.phoenix.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by Song on 2016/12/21.
 */
@Keep
public class VideoListsBean implements Parcelable {

    private int totalPage;
    private String currentPage;
    private String type;
    private List<VideoListsTypeBean> types;
    private List<VideoDetailBean> item;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<VideoListsTypeBean> getTypes() {
        return types;
    }

    public void setTypes(List<VideoListsTypeBean> types) {
        this.types = types;
    }

    public List<VideoDetailBean> getItem() {
        return item;
    }

    public void setItem(List<VideoDetailBean> item) {
        this.item = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPage);
        dest.writeString(this.currentPage);
        dest.writeString(this.type);
        dest.writeTypedList(this.types);
        dest.writeTypedList(this.item);
    }

    public VideoListsBean() {
    }

    protected VideoListsBean(Parcel in) {
        this.totalPage = in.readInt();
        this.currentPage = in.readString();
        this.type = in.readString();
        this.types = in.createTypedArrayList(VideoListsTypeBean.CREATOR);
        this.item = in.createTypedArrayList(VideoDetailBean.CREATOR);
    }

    public static final Parcelable.Creator<VideoListsBean> CREATOR = new Parcelable.Creator<VideoListsBean>() {
        @Override
        public VideoListsBean createFromParcel(Parcel source) {
            return new VideoListsBean(source);
        }

        @Override
        public VideoListsBean[] newArray(int size) {
            return new VideoListsBean[size];
        }
    };
}
