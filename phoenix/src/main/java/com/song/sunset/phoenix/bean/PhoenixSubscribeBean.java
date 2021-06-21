package com.song.sunset.phoenix.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
@Keep
public class PhoenixSubscribeBean implements Parcelable {

    private String cateid;
    private String type;
    private String catename;
    private String logo;
    private String description;

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cateid);
        dest.writeString(this.type);
        dest.writeString(this.catename);
        dest.writeString(this.logo);
        dest.writeString(this.description);
    }

    public PhoenixSubscribeBean() {
    }

    protected PhoenixSubscribeBean(Parcel in) {
        this.cateid = in.readString();
        this.type = in.readString();
        this.catename = in.readString();
        this.logo = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<PhoenixSubscribeBean> CREATOR = new Parcelable.Creator<PhoenixSubscribeBean>() {
        @Override
        public PhoenixSubscribeBean createFromParcel(Parcel source) {
            return new PhoenixSubscribeBean(source);
        }

        @Override
        public PhoenixSubscribeBean[] newArray(int size) {
            return new PhoenixSubscribeBean[size];
        }
    };
}
