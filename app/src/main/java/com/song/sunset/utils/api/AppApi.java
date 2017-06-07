package com.song.sunset.utils.api;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class AppApi {

    public static final String COMIC_BASE_URL = "http://app.u17.com/v3/appV3/android/phone/";

    public static final String COMIC_LIST_URL = COMIC_BASE_URL + "list/commonComicList?";

    public static final String COMIC_DETAIL_URL = COMIC_BASE_URL + "comic/detail_static_new?comicid=";

    public static final String COMIC_READ_URL = COMIC_BASE_URL + "comic/chapterlist?comicid=";

    public static final String COMIC_CLASSIFY_URL = COMIC_BASE_URL + "sort/mobileCateList?version=2";

    public static String getComicDetailUrl(int comicId) {
        return COMIC_DETAIL_URL + comicId;
    }

    public static String getComicReadUrl(String comicId) {
        return COMIC_READ_URL + comicId;
    }

    public static String getComicListUrl(int page, int argValue, String argName) {
        return COMIC_LIST_URL + "argValue=" + argValue + "&" + "argName=" + argName + "&" + "&page=" + page;
    }

}
