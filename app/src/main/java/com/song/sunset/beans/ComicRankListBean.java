package com.song.sunset.beans;

import java.util.List;

/**
 * Created by Song on 2016/9/28 0028.
 * Email:z53520@qq.com
 */

public class ComicRankListBean {

    private List<RankinglistBean> rankinglist;

    public List<RankinglistBean> getRankinglist() {
        return rankinglist;
    }

    public void setRankinglist(List<RankinglistBean> rankinglist) {
        this.rankinglist = rankinglist;
    }

    public static class RankinglistBean {
        private String title;
        private String subTitle;
        private String cover;
        private String argName;
        private String argValue;
        private String rankingType;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getArgName() {
            return argName;
        }

        public void setArgName(String argName) {
            this.argName = argName;
        }

        public String getArgValue() {
            return argValue;
        }

        public void setArgValue(String argValue) {
            this.argValue = argValue;
        }

        public String getRankingType() {
            return rankingType;
        }

        public void setRankingType(String rankingType) {
            this.rankingType = rankingType;
        }
    }
}
