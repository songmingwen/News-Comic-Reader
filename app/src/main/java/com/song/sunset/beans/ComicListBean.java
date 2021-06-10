package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.song.sunset.base.bean.PageEntity;

import java.util.List;

/**
 * Created by Song on 2016/9/22 0022.
 * Email:z53520@qq.com
 */
public class ComicListBean implements PageEntity<ComicsBean>, Parcelable {

    private boolean hasMore;

    private int page;

    private List<ComicsBean> comics;

    @Override
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ComicsBean> getComics() {
        return comics;
    }

    public void setComics(List<ComicsBean> comics) {
        this.comics = comics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.hasMore ? (byte) 1 : (byte) 0);
        dest.writeInt(this.page);
        dest.writeTypedList(this.comics);
    }

    public ComicListBean() {
    }

    protected ComicListBean(Parcel in) {
        this.hasMore = in.readByte() != 0;
        this.page = in.readInt();
        this.comics = in.createTypedArrayList(ComicsBean.CREATOR);
    }

    public static final Parcelable.Creator<ComicListBean> CREATOR = new Parcelable.Creator<ComicListBean>() {
        @Override
        public ComicListBean createFromParcel(Parcel source) {
            return new ComicListBean(source);
        }

        @Override
        public ComicListBean[] newArray(int size) {
            return new ComicListBean[size];
        }
    };

    @Override
    public int getOnePageCount() {
        return 7;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public List<ComicsBean> getData() {
        return getComics();
    }
}
