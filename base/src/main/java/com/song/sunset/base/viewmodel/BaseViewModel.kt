package com.song.sunset.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

/**
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