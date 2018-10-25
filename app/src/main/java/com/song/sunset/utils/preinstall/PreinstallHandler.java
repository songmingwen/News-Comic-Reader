package com.song.sunset.utils.preinstall;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
public interface PreinstallHandler {

    String getPreinstallInfo();

    void setNextHandler(PreinstallHandler preinstallHandler);
}
