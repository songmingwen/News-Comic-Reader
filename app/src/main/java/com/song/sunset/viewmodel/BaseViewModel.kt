package com.song.sunset.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Copyright(C),2006-2021,快乐阳光互动娱乐传媒有限公司
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/4/28 16:38
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val compositeDisposable: CompositeDisposable by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}