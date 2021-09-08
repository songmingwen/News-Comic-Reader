package com.song.sunset.comic.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */
@Keep
public class ComicReadChapterBean {

    private String chapter_id;
    private String type;
    private String zip_file_high;
    private List<ComicReadImageListBean> image_list;
    private List<ComicReadUnLockImageBean> unlock_image;

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZip_file_high() {
        return zip_file_high;
    }

    public void setZip_file_high(String zip_file_high) {
        this.zip_file_high = zip_file_high;
    }

    public List<ComicReadImageListBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ComicReadImageListBean> image_list) {
        this.image_list = image_list;
    }

    public List<ComicReadUnLockImageBean> getUnlock_image() {
        return unlock_image;
    }

    public void setUnlock_image(List<ComicReadUnLockImageBean> unlock_image) {
        this.unlock_image = unlock_image;
    }
}
