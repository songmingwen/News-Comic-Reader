package com.song.sunset.phoenix.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.song.sunset.base.bean.PageEntity;

import java.util.List;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */

public class PhoenixNewsListBean implements Parcelable, PageEntity<PhoenixChannelBean> {

    private String listId;
    private String type;
    private int expiredTime;
    private int currentPage;
    private int totalPage;
    private int topsize;
    private List<PhoenixChannelBean> item;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTopsize() {
        return topsize;
    }

    public void setTopsize(int topsize) {
        this.topsize = topsize;
    }

    public List<PhoenixChannelBean> getItem() {
        return item;
    }

    public void setItem(List<PhoenixChannelBean> item) {
        this.item = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.listId);
        dest.writeString(this.type);
        dest.writeInt(this.expiredTime);
        dest.writeInt(this.currentPage);
        dest.writeInt(this.totalPage);
        dest.writeInt(this.topsize);
        dest.writeTypedList(this.item);
    }

    public PhoenixNewsListBean() {
    }

    protected PhoenixNewsListBean(Parcel in) {
        this.listId = in.readString();
        this.type = in.readString();
        this.expiredTime = in.readInt();
        this.currentPage = in.readInt();
        this.totalPage = in.readInt();
        this.topsize = in.readInt();
        this.item = in.createTypedArrayList(PhoenixChannelBean.CREATOR);
    }

    public static final Parcelable.Creator<PhoenixNewsListBean> CREATOR = new Parcelable.Creator<PhoenixNewsListBean>() {
        @Override
        public PhoenixNewsListBean createFromParcel(Parcel source) {
            return new PhoenixNewsListBean(source);
        }

        @Override
        public PhoenixNewsListBean[] newArray(int size) {
            return new PhoenixNewsListBean[size];
        }
    };

    @Override
    public int getOnePageCount() {
        return totalPage;
    }

    @Override
    public boolean isHasMore() {
        return true;
    }

    @Override
    public List<PhoenixChannelBean> getData() {
        return getItem();
    }
}
