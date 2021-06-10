package com.song.sunset.phoenix.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class PhoenixChannelBean implements Parcelable {

    private String thumbnail;
    private String online;
    private String title;
    private String showType;
    private String source;
    private PhoenixSubscribeBean subscribe;
    private String updateTime;
    private String id;
    private String documentId;
    private String staticId;
    private String type;
    private PhoenixStyleBean style;
    private boolean hasVideo;
    private String commentsUrl;
    private String comments;
    private String commentsall;
    private PhoenixLinkBean link;
    private String reftype;
    private String simId;
    private boolean hasSlide;
    private String recomToken;
    private String startTimeStr;
    private PhoenixLiveExt liveExt;
    private PhoenixSportLiveExt sportsLiveExt;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public PhoenixSubscribeBean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(PhoenixSubscribeBean subscribe) {
        this.subscribe = subscribe;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getStaticId() {
        return staticId;
    }

    public void setStaticId(String staticId) {
        this.staticId = staticId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PhoenixStyleBean getStyle() {
        return style;
    }

    public void setStyle(PhoenixStyleBean style) {
        this.style = style;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsall() {
        return commentsall;
    }

    public void setCommentsall(String commentsall) {
        this.commentsall = commentsall;
    }

    public PhoenixLinkBean getLink() {
        return link;
    }

    public void setLink(PhoenixLinkBean link) {
        this.link = link;
    }

    public String getReftype() {
        return reftype;
    }

    public void setReftype(String reftype) {
        this.reftype = reftype;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public boolean isHasSlide() {
        return hasSlide;
    }

    public void setHasSlide(boolean hasSlide) {
        this.hasSlide = hasSlide;
    }

    public String getRecomToken() {
        return recomToken;
    }

    public void setRecomToken(String recomToken) {
        this.recomToken = recomToken;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public PhoenixLiveExt getLiveExt() {
        return liveExt;
    }

    public void setLiveExt(PhoenixLiveExt liveExt) {
        this.liveExt = liveExt;
    }

    public PhoenixSportLiveExt getSportsLiveExt() {
        return sportsLiveExt;
    }

    public void setSportsLiveExt(PhoenixSportLiveExt sportsLiveExt) {
        this.sportsLiveExt = sportsLiveExt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnail);
        dest.writeString(this.online);
        dest.writeString(this.title);
        dest.writeString(this.showType);
        dest.writeString(this.source);
        dest.writeParcelable(this.subscribe, flags);
        dest.writeString(this.updateTime);
        dest.writeString(this.id);
        dest.writeString(this.documentId);
        dest.writeString(this.staticId);
        dest.writeString(this.type);
        dest.writeParcelable(this.style, flags);
        dest.writeByte(this.hasVideo ? (byte) 1 : (byte) 0);
        dest.writeString(this.commentsUrl);
        dest.writeString(this.comments);
        dest.writeString(this.commentsall);
        dest.writeParcelable(this.link, flags);
        dest.writeString(this.reftype);
        dest.writeString(this.simId);
        dest.writeByte(this.hasSlide ? (byte) 1 : (byte) 0);
        dest.writeString(this.recomToken);
        dest.writeString(this.startTimeStr);
        dest.writeParcelable(this.liveExt, flags);
        dest.writeParcelable(this.sportsLiveExt, flags);
    }

    public PhoenixChannelBean() {
    }

    protected PhoenixChannelBean(Parcel in) {
        this.thumbnail = in.readString();
        this.online = in.readString();
        this.title = in.readString();
        this.showType = in.readString();
        this.source = in.readString();
        this.subscribe = in.readParcelable(PhoenixSubscribeBean.class.getClassLoader());
        this.updateTime = in.readString();
        this.id = in.readString();
        this.documentId = in.readString();
        this.staticId = in.readString();
        this.type = in.readString();
        this.style = in.readParcelable(PhoenixStyleBean.class.getClassLoader());
        this.hasVideo = in.readByte() != 0;
        this.commentsUrl = in.readString();
        this.comments = in.readString();
        this.commentsall = in.readString();
        this.link = in.readParcelable(PhoenixLinkBean.class.getClassLoader());
        this.reftype = in.readString();
        this.simId = in.readString();
        this.hasSlide = in.readByte() != 0;
        this.recomToken = in.readString();
        this.startTimeStr = in.readString();
        this.liveExt = in.readParcelable(PhoenixLiveExt.class.getClassLoader());
        this.sportsLiveExt = in.readParcelable(PhoenixSportLiveExt.class.getClassLoader());
    }

    public static final Creator<PhoenixChannelBean> CREATOR = new Creator<PhoenixChannelBean>() {
        @Override
        public PhoenixChannelBean createFromParcel(Parcel source) {
            return new PhoenixChannelBean(source);
        }

        @Override
        public PhoenixChannelBean[] newArray(int size) {
            return new PhoenixChannelBean[size];
        }
    };
}
