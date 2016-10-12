package com.song.sunset.beans;

import java.util.List;

/**
 * Created by Song on 2016/9/22 0022.
 * Email:z53520@qq.com
 */
public class ComicListBean {

    private boolean hasMore;

    private int page;

    private List<ComicsBean> comics;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ComicsBean> getComics() {
        return comics;
    }

    public void setComics(List<ComicsBean> comics) {
        this.comics = comics;
    }

    public static class ComicsBean {
        private String conTag;
        private String cover;
        private String name;
        private int comicId;
        private String description;
        private int flag;
        private String author;
        private List<String> tags;

        public String getConTag() {
            return conTag;
        }

        public void setConTag(String conTag) {
            this.conTag = conTag;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getComicId() {
            return comicId;
        }

        public void setComicId(int comicId) {
            this.comicId = comicId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
