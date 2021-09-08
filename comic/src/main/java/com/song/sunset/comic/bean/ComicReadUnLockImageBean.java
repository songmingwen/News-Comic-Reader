package com.song.sunset.comic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */
@Keep
public class ComicReadUnLockImageBean implements Parcelable {
    /**
     * image_id : 4826245
     * blur_image_url : http://img4.u17i.com/17/08/99874/wp/10449554_1501636878_HgEEyRgv2nnV.cedff_sealwp.jpg
     * price : 100
     * total_tucao : 5
     * state : 0
     * total_praise : 309
     * total_buy_count : 356
     * author_comments : 女神凝儿的另一面…
     * size : 212742
     * total_score : 0.0
     * opt_praise : 0
     */

    private String image_id;
    private String blur_image_url;
    private String price;
    private String total_tucao;
    private int state;
    private int total_praise;
    private int total_buy_count;
    private String author_comments;
    private String size;
    private String total_score;
    private int opt_praise;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getBlur_image_url() {
        return blur_image_url;
    }

    public void setBlur_image_url(String blur_image_url) {
        this.blur_image_url = blur_image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_tucao() {
        return total_tucao;
    }

    public void setTotal_tucao(String total_tucao) {
        this.total_tucao = total_tucao;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTotal_praise() {
        return total_praise;
    }

    public void setTotal_praise(int total_praise) {
        this.total_praise = total_praise;
    }

    public int getTotal_buy_count() {
        return total_buy_count;
    }

    public void setTotal_buy_count(int total_buy_count) {
        this.total_buy_count = total_buy_count;
    }

    public String getAuthor_comments() {
        return author_comments;
    }

    public void setAuthor_comments(String author_comments) {
        this.author_comments = author_comments;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public int getOpt_praise() {
        return opt_praise;
    }

    public void setOpt_praise(int opt_praise) {
        this.opt_praise = opt_praise;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image_id);
        dest.writeString(this.blur_image_url);
        dest.writeString(this.price);
        dest.writeString(this.total_tucao);
        dest.writeInt(this.state);
        dest.writeInt(this.total_praise);
        dest.writeInt(this.total_buy_count);
        dest.writeString(this.author_comments);
        dest.writeString(this.size);
        dest.writeString(this.total_score);
        dest.writeInt(this.opt_praise);
    }

    public ComicReadUnLockImageBean() {
    }

    protected ComicReadUnLockImageBean(Parcel in) {
        this.image_id = in.readString();
        this.blur_image_url = in.readString();
        this.price = in.readString();
        this.total_tucao = in.readString();
        this.state = in.readInt();
        this.total_praise = in.readInt();
        this.total_buy_count = in.readInt();
        this.author_comments = in.readString();
        this.size = in.readString();
        this.total_score = in.readString();
        this.opt_praise = in.readInt();
    }

    public static final Parcelable.Creator<ComicReadUnLockImageBean> CREATOR = new Parcelable.Creator<ComicReadUnLockImageBean>() {
        @Override
        public ComicReadUnLockImageBean createFromParcel(Parcel source) {
            return new ComicReadUnLockImageBean(source);
        }

        @Override
        public ComicReadUnLockImageBean[] newArray(int size) {
            return new ComicReadUnLockImageBean[size];
        }
    };
}
