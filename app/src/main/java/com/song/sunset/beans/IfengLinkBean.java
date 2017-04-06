package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class IfengLinkBean implements Parcelable {

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

    public IfengLinkBean() {
    }

    protected IfengLinkBean(Parcel in) {
        this.type = in.readString();
        this.url = in.readString();
        this.weburl = in.readString();
    }

    public static final Parcelable.Creator<IfengLinkBean> CREATOR = new Parcelable.Creator<IfengLinkBean>() {
        @Override
        public IfengLinkBean createFromParcel(Parcel source) {
            return new IfengLinkBean(source);
        }

        @Override
        public IfengLinkBean[] newArray(int size) {
            return new IfengLinkBean[size];
        }
    };
}
