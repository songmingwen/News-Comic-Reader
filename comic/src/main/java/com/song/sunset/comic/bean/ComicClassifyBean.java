package com.song.sunset.comic.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * Created by Song on 2016/9/21 0021.
 * Email:z53520@qq.com
 */
@Keep
public class ComicClassifyBean {
    private String recommendSearch;

    private List<RankingListBean> rankingList;

    private List<TopListBean> topList;

    public String getRecommendSearch() {
        return recommendSearch;
    }

    public void setRecommendSearch(String recommendSearch) {
        this.recommendSearch = recommendSearch;
    }

    public List<RankingListBean> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<RankingListBean> rankingList) {
        this.rankingList = rankingList;
    }

    public List<TopListBean> getTopList() {
        return topList;
    }

    public void setTopList(List<TopListBean> topList) {
        this.topList = topList;
    }

    public static class RankingListBean {
        private String sortName;
        private String cover;
        private String argName;
        private int argValue;
        private int argCon;

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
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

        public int getArgValue() {
            return argValue;
        }

        public void setArgValue(int argValue) {
            this.argValue = argValue;
        }

        public int getArgCon() {
            return argCon;
        }

        public void setArgCon(int argCon) {
            this.argCon = argCon;
        }
    }

    public static class TopListBean {
        private String sortId;
        private String sortName;
        private String cover;

        private ExtraBean extra;

        public String getSortId() {
            return sortId;
        }

        public void setSortId(String sortId) {
            this.sortId = sortId;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public static class ExtraBean {
            private String title;

            private List<TabListBean> tabList;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<TabListBean> getTabList() {
                return tabList;
            }

            public void setTabList(List<TabListBean> tabList) {
                this.tabList = tabList;
            }

            public static class TabListBean {
                private String argName;
                private int argValue;
                private int argCon;
                private String tabTitle;

                public String getArgName() {
                    return argName;
                }

                public void setArgName(String argName) {
                    this.argName = argName;
                }

                public int getArgValue() {
                    return argValue;
                }

                public void setArgValue(int argValue) {
                    this.argValue = argValue;
                }

                public int getArgCon() {
                    return argCon;
                }

                public void setArgCon(int argCon) {
                    this.argCon = argCon;
                }

                public String getTabTitle() {
                    return tabTitle;
                }

                public void setTabTitle(String tabTitle) {
                    this.tabTitle = tabTitle;
                }
            }
        }
    }
}
