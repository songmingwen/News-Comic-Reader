package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Song on 2018/2/7 0007.
 * E-mail: z53520@qq.com
 */

public class VideoDetailBean implements Parcelable {
    private String documentId;
    private String title;
    private String image;
    private String thumbnail;
    private String guid;
    private String type;
    private int commentsall;
    private int duration;
    private String shareUrl;
    private String commentsUrl;
    private String video_url;
    private int video_size;
    private String praise;
    private String tread;
    private String playTime;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCommentsall() {
        return commentsall;
    }

    public void setCommentsall(int commentsall) {
        this.commentsall = commentsall;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getVideo_size() {
        return video_size;
    }

    public void setVideo_size(int video_size) {
        this.video_size = video_size;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getTread() {
        return tread;
    }

    public void setTread(String tread) {
        this.tread = tread;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.documentId);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.thumbnail);
        dest.writeString(this.guid);
        dest.writeString(this.type);
        dest.writeInt(this.commentsall);
        dest.writeInt(this.duration);
        dest.writeString(this.shareUrl);
        dest.writeString(this.commentsUrl);
        dest.writeString(this.video_url);
        dest.writeInt(this.video_size);
        dest.writeString(this.praise);
        dest.writeString(this.tread);
        dest.writeString(this.playTime);
    }

    public VideoDetailBean() {
    }

    protected VideoDetailBean(Parcel in) {
        this.documentId = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.thumbnail = in.readString();
        this.guid = in.readString();
        this.type = in.readString();
        this.commentsall = in.readInt();
        this.duration = in.readInt();
        this.shareUrl = in.readString();
        this.commentsUrl = in.readString();
        this.video_url = in.readString();
        this.video_size = in.readInt();
        this.praise = in.readString();
        this.tread = in.readString();
        this.playTime = in.readString();
    }

    public static final Parcelable.Creator<VideoDetailBean> CREATOR = new Parcelable.Creator<VideoDetailBean>() {
        @Override
        public VideoDetailBean createFromParcel(Parcel source) {
            return new VideoDetailBean(source);
        }

        @Override
        public VideoDetailBean[] newArray(int size) {
            return new VideoDetailBean[size];
        }
    };
}
