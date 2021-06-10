package com.song.sunset.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.base.fragment.BaseFragment

/**
 * @author songmingwen
 * @description
 * @since 2020/4/30
 */
@Route(path = "/song/flutter/comic/newest")
class FlutterComicFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}