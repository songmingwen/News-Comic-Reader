package com.song.sunset.beans;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 */
@Keep
@Entity
public class ComicLocalCollection {

    private String cover;
    private String name;
    @Id
    private long comicId;
    private String description;
    private String author;
    private String chapterNum;
    @Generated(hash = 1145280907)
    public ComicLocalCollection(String cover, String name, long comicId,
            String description, String author, String chapterNum) {
        this.cover = cover;
        this.name = name;
        this.comicId = comicId;
        this.description = description;
        this.author = author;
        this.chapterNum = chapterNum;
    }
    @Generated(hash = 1790223035)
    public ComicLocalCollection() {
    }
    public String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getComicId() {
        return this.comicId;
    }
    public void setComicId(long comicId) {
        this.comicId = comicId;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getChapterNum() {
        return this.chapterNum;
    }
    public void setChapterNum(String chapterNum) {
        this.chapterNum = chapterNum;
    }
}
