package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/4/18 0018.
 * E-mail: z53520@qq.com
 */

public class DanmakuBean implements Parcelable {
    private long time;                      // 0:时间(弹幕出现时间)
    private float textSize;                // 2:字号
    private int type;                        // 1:类型(1从右至左滚动弹幕|6从左至右滚动弹幕|5顶端固定弹幕|4底端固定弹幕|7高级弹幕|8脚本弹幕)
    private int color;                       // 3:颜色
    private int textShadowColor;      // 3:阴影颜色
    private String id;                       // 5:弹幕池id
    private String userHash;             // 6:用户hash
    private String danmakuId;          // 7:弹幕id
    private String content;
    private boolean isLive;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTextShadowColor() {
        return textShadowColor;
    }

    public void setTextShadowColor(int textShadowColor) {
        this.textShadowColor = textShadowColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public String getDanmakuId() {
        return danmakuId;
    }

    public void setDanmakuId(String danmakuId) {
        this.danmakuId = danmakuId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.time);
        dest.writeFloat(this.textSize);
        dest.writeInt(this.type);
        dest.writeInt(this.color);
        dest.writeInt(this.textShadowColor);
        dest.writeString(this.id);
        dest.writeString(this.userHash);
        dest.writeString(this.danmakuId);
        dest.writeString(this.content);
        dest.writeByte(this.isLive ? (byte) 1 : (byte) 0);
    }

    public DanmakuBean() {
    }

    protected DanmakuBean(Parcel in) {
        this.time = in.readLong();
        this.textSize = in.readFloat();
        this.type = in.readInt();
        this.color = in.readInt();
        this.textShadowColor = in.readInt();
        this.id = in.readString();
        this.userHash = in.readString();
        this.danmakuId = in.readString();
        this.content = in.readString();
        this.isLive = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DanmakuBean> CREATOR = new Parcelable.Creator<DanmakuBean>() {
        @Override
        public DanmakuBean createFromParcel(Parcel source) {
            return new DanmakuBean(source);
        }

        @Override
        public DanmakuBean[] newArray(int size) {
            return new DanmakuBean[size];
        }
    };
}
