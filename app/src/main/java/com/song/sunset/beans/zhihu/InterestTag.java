package com.song.sunset.beans.zhihu;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/16
 */
@Keep
public class InterestTag implements Parcelable {

    public static final String TYPE_OF_HOT = "0";
    public static final String TYPE_OF_INTEREST = "1";
    public static final String TYPE_OF_PORTRAIT = "2";

    /**
     * 文字颜色
     */
    public String color = "#3acbe0";

    /**
     * tag 选中时候的背景色
     */
    public String bgWhenSelected = "#f98807";

    /**
     * tag 未选中时候的背景色
     */
    public String bgNormal = "#19f98807";

    public String id;

    /**
     * 标签的名称
     */
    public String tag;

    /**
     * 标签选中状态
     */
    public String name;

    public boolean selected;

    /**
     * 标签类型：0 热词标签，1 兴趣标签，2 形象标签
     */
    @SerializedName("tag_type")
    public String type;

    /**
     * 热词描述
     */
    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTag() {
        if (!TextUtils.isEmpty(name)) {
            return name;
        } else {
            return tag;
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeString(this.bgWhenSelected);
        dest.writeString(this.bgNormal);
        dest.writeString(this.id);
        dest.writeString(this.tag);
        dest.writeString(this.name);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
        dest.writeString(this.description);
    }

    public InterestTag() {
    }

    protected InterestTag(Parcel in) {
        this.color = in.readString();
        this.bgWhenSelected = in.readString();
        this.bgNormal = in.readString();
        this.id = in.readString();
        this.tag = in.readString();
        this.name = in.readString();
        this.selected = in.readByte() != 0;
        this.type = in.readString();
        this.description = in.readString();
    }

    public static final Creator<InterestTag> CREATOR = new Creator<InterestTag>() {
        @Override
        public InterestTag createFromParcel(Parcel source) {
            return new InterestTag(source);
        }

        @Override
        public InterestTag[] newArray(int size) {
            return new InterestTag[size];
        }
    };
}
