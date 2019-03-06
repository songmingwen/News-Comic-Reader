package com.song.sunset.utils.preinstall;

import com.song.kotlin.utils.preinstall.PreinstallHandler;

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
public class DefaultPreinstallHandler implements PreinstallHandler {
    @Override
    public String getPreinstallInfo() {
        return "alpha";
    }

    @Override
    public void setNextHandler(PreinstallHandler preinstallHandler) {
    }
}
