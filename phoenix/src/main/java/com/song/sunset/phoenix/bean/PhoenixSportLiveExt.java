package com.song.sunset.phoenix.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/4/11 0011.
 * E-mail: z53520@qq.com
 */
public class PhoenixSportLiveExt implements Parcelable {

    /**
     * matchid : 5342
     * title : 骑士vs热火
     * isOneTitle : 0
     * tag : NBA常规赛
     * typeName :
     * matchType : basketball
     * startTime : 1491867000
     * endTime : 1491877800
     * category : 1
     * leftName : 骑士
     * rightName : 热火
     * leftLogo : http://y2.ifengimg.com/ifengimcp/pic/20140523/e297ed9a42a344f79406_size11_w90_h62.png
     * rightLogo : http://y2.ifengimg.com/ifengimcp/pic/20140523/9c17d6b495001f3c9a6d_size6_w90_h62.png
     * showScore : 1
     * leftScore : 108
     * rightScore : 108
     * relateChannel1 : 1581
     * relateChannel2 :
     * time :
     * section : 4
     * sectionstr : 第四节
     * sync : true
     */

    private String matchid;
    private String title;
    private String isOneTitle;
    private String tag;
    private String typeName;
    private String matchType;
    private String startTime;
    private String endTime;
    private String category;
    private String leftName;
    private String rightName;
    private String leftLogo;
    private String rightLogo;
    private String showScore;
    private String leftScore;
    private String rightScore;
    private String relateChannel1;
    private String relateChannel2;
    private String time;
    private String section;
    private String sectionstr;
    private boolean sync;

    public String getMatchid() {
        return matchid;
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsOneTitle() {
        return isOneTitle;
    }

    public void setIsOneTitle(String isOneTitle) {
        this.isOneTitle = isOneTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getLeftLogo() {
        return leftLogo;
    }

    public void setLeftLogo(String leftLogo) {
        this.leftLogo = leftLogo;
    }

    public String getRightLogo() {
        return rightLogo;
    }

    public void setRightLogo(String rightLogo) {
        this.rightLogo = rightLogo;
    }

    public String getShowScore() {
        return showScore;
    }

    public void setShowScore(String showScore) {
        this.showScore = showScore;
    }

    public String getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(String leftScore) {
        this.leftScore = leftScore;
    }

    public String getRightScore() {
        return rightScore;
    }

    public void setRightScore(String rightScore) {
        this.rightScore = rightScore;
    }

    public String getRelateChannel1() {
        return relateChannel1;
    }

    public void setRelateChannel1(String relateChannel1) {
        this.relateChannel1 = relateChannel1;
    }

    public String getRelateChannel2() {
        return relateChannel2;
    }

    public void setRelateChannel2(String relateChannel2) {
        this.relateChannel2 = relateChannel2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionstr() {
        return sectionstr;
    }

    public void setSectionstr(String sectionstr) {
        this.sectionstr = sectionstr;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.matchid);
        dest.writeString(this.title);
        dest.writeString(this.isOneTitle);
        dest.writeString(this.tag);
        dest.writeString(this.typeName);
        dest.writeString(this.matchType);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.category);
        dest.writeString(this.leftName);
        dest.writeString(this.rightName);
        dest.writeString(this.leftLogo);
        dest.writeString(this.rightLogo);
        dest.writeString(this.showScore);
        dest.writeString(this.leftScore);
        dest.writeString(this.rightScore);
        dest.writeString(this.relateChannel1);
        dest.writeString(this.relateChannel2);
        dest.writeString(this.time);
        dest.writeString(this.section);
        dest.writeString(this.sectionstr);
        dest.writeByte(this.sync ? (byte) 1 : (byte) 0);
    }

    public PhoenixSportLiveExt() {
    }

    protected PhoenixSportLiveExt(Parcel in) {
        this.matchid = in.readString();
        this.title = in.readString();
        this.isOneTitle = in.readString();
        this.tag = in.readString();
        this.typeName = in.readString();
        this.matchType = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.category = in.readString();
        this.leftName = in.readString();
        this.rightName = in.readString();
        this.leftLogo = in.readString();
        this.rightLogo = in.readString();
        this.showScore = in.readString();
        this.leftScore = in.readString();
        this.rightScore = in.readString();
        this.relateChannel1 = in.readString();
        this.relateChannel2 = in.readString();
        this.time = in.readString();
        this.section = in.readString();
        this.sectionstr = in.readString();
        this.sync = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PhoenixSportLiveExt> CREATOR = new Parcelable.Creator<PhoenixSportLiveExt>() {
        @Override
        public PhoenixSportLiveExt createFromParcel(Parcel source) {
            return new PhoenixSportLiveExt(source);
        }

        @Override
        public PhoenixSportLiveExt[] newArray(int size) {
            return new PhoenixSportLiveExt[size];
        }
    };
}
