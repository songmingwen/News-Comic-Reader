package com.song.core.autoservice;

import com.google.auto.service.AutoService;

/**
 * @author songmingwen
 * @description
 * @since 2020/5/13
 */
@AutoService(InterfaceA.class)
public class ImplA implements InterfaceA {
    @Override
    public String getString(String a) {
        return "ImplA + " + a;
    }
}
