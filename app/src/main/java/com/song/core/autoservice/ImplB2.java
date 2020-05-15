package com.song.core.autoservice;

import com.google.auto.service.AutoService;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/13
 */
@AutoService(InterfaceB.class)
public class ImplB2 implements InterfaceB {
    @Override
    public String getString() {
        return "ImplB2";
    }
}
