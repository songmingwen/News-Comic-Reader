package com.song.sunset.utils;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
public class DefaultPreinstallHandler implements PreinstallHandler {
    @Override
    public String getPreinstallInfo() {
        return "undefined";
    }

    @Override
    public void setNextHandler(PreinstallHandler preinstallHandler) {
    }
}
