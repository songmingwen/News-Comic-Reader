package com.song.sunset.phoenix.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class PhoenixLinkBean implements Parcelable {

    private String type;
    private String url;
    private String weburl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.weburl);
    }

    public PhoenixLinkBean() {
    }

    protected PhoenixLinkBean(Parcel in) {
        this.type = in.readString();
        this.url = in.readString();
        this.weburl = in.readString();
    }

    public static final Parcelable.Creator<PhoenixLinkBean> CREATOR = new Parcelable.Creator<PhoenixLinkBean>() {
        @Override
        public PhoenixLinkBean createFromParcel(Parcel source) {
            return new PhoenixLinkBean(source);
        }

        @Override
        public PhoenixLinkBean[] newArray(int size) {
            return new PhoenixLinkBean[size];
        }
    };
}
