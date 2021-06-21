package com.song.sunset.phoenix.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.ArrayList;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
@Keep
public class PhoenixStyleBean implements Parcelable {

    //展示类型	recommend/bigimg/slides/normal
    private String type;
    //左上角标签	热，荐 等
    private String tag;
    //多联图	type为slides时存在，类型为数组,每个元素是一个图片地址
    private ArrayList<String> images;
    //大图模式
    private String bigimg;
    //图片数量
    private int slideCount;
    //显示类型
    private String view;
    //相关新闻 拉起视频app推广标签
    private String attribute;

    private ArrayList<String> backreason;

    private String sponsor;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getBigimg() {
        return bigimg;
    }

    public void setBigimg(String bigimg) {
        this.bigimg = bigimg;
    }

    public int getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ArrayList<String> getBackreason() {
        return backreason;
    }

    public void setBackreason(ArrayList<String> backreason) {
        this.backreason = backreason;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getSponsorLogo() {
        return sponsorLogo;
    }

    public void setSponsorLogo(String sponsorLogo) {
        this.sponsorLogo = sponsorLogo;
    }

    private String sponsorLogo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.tag);
        dest.writeStringList(this.images);
        dest.writeString(this.bigimg);
        dest.writeInt(this.slideCount);
        dest.writeString(this.view);
        dest.writeString(this.attribute);
        dest.writeStringList(this.backreason);
        dest.writeString(this.sponsor);
        dest.writeString(this.sponsorLogo);
    }

    public PhoenixStyleBean() {
    }

    protected PhoenixStyleBean(Parcel in) {
        this.type = in.readString();
        this.tag = in.readString();
        this.images = in.createStringArrayList();
        this.bigimg = in.readString();
        this.slideCount = in.readInt();
        this.view = in.readString();
        this.attribute = in.readString();
        this.backreason = in.createStringArrayList();
        this.sponsor = in.readString();
        this.sponsorLogo = in.readString();
    }

    public static final Parcelable.Creator<PhoenixStyleBean> CREATOR = new Parcelable.Creator<PhoenixStyleBean>() {
        @Override
        public PhoenixStyleBean createFromParcel(Parcel source) {
            return new PhoenixStyleBean(source);
        }

        @Override
        public PhoenixStyleBean[] newArray(int size) {
            return new PhoenixStyleBean[size];
        }
    };
}
