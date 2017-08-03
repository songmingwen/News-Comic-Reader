package com.song.sunset.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadImageListBean implements Parcelable {

    private String location;
    private String image_id;
    private int width;
    private int height;
    private String total_tucao;
    private String webp;
    private String type;
    private String img05;
    private String img50;
    private List<ComicImagesBean> images;
    private List<String> imHeightArr;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTotal_tucao() {
        return total_tucao;
    }

    public void setTotal_tucao(String total_tucao) {
        this.total_tucao = total_tucao;
    }

    public String getWebp() {
        return webp;
    }

    public void setWebp(String webp) {
        this.webp = webp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg05() {
        return img05;
    }

    public void setImg05(String img05) {
        this.img05 = img05;
    }

    public String getImg50() {
        return img50;
    }

    public void setImg50(String img50) {
        this.img50 = img50;
    }

    public List<ComicImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ComicImagesBean> images) {
        this.images = images;
    }

    public List<String> getImHeightArr() {
        return imHeightArr;
    }

    public void setImHeightArr(List<String> imHeightArr) {
        this.imHeightArr = imHeightArr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.location);
        dest.writeString(this.image_id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.total_tucao);
        dest.writeString(this.webp);
        dest.writeString(this.type);
        dest.writeString(this.img05);
        dest.writeString(this.img50);
        dest.writeTypedList(this.images);
        dest.writeStringList(this.imHeightArr);
    }

    public ComicReadImageListBean() {
    }

    protected ComicReadImageListBean(Parcel in) {
        this.location = in.readString();
        this.image_id = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.total_tucao = in.readString();
        this.webp = in.readString();
        this.type = in.readString();
        this.img05 = in.readString();
        this.img50 = in.readString();
        this.images = in.createTypedArrayList(ComicImagesBean.CREATOR);
        this.imHeightArr = in.createStringArrayList();
    }

    public static final Creator<ComicReadImageListBean> CREATOR = new Creator<ComicReadImageListBean>() {
        @Override
        public ComicReadImageListBean createFromParcel(Parcel source) {
            return new ComicReadImageListBean(source);
        }

        @Override
        public ComicReadImageListBean[] newArray(int size) {
            return new ComicReadImageListBean[size];
        }
    };
}
