package com.song.sunset.utils.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Config
import android.util.Log
import com.song.sunset.BuildConfig
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.lang.ref.WeakReference
import java.util.HashMap

/**
 * @author songmingwen
 * @description 生命周期监听管理类
 * @since 2020/3/10
 */

class LifecycleManager(application: Application) {

    companion object {
        val lifecyclePublisher: PublishSubject<BaseActivityLifecycle.LifecycleEvent> =
                PublishSubject.create<BaseActivityLifecycle.LifecycleEvent>()

        var sCurrentActivity: WeakReference<Activity>? = null
    }

    private val app = application

    private var createCount = 0
    private var startCount = 0

    private val lifecycleMap = object : HashMap<String, BaseActivityLifecycle>() {
        override fun put(key: String, value: BaseActivityLifecycle): BaseActivityLifecycle? {
            if (containsKey(key)) {
                throw RuntimeException("已有重名的 BaseActivityLifecycle: $key")
            }
            return super.put(key, value)
        }
    }

    /**
     * 添加生命周期监听类
     */
    fun addLifeCycle(name: String, lifecycle: BaseActivityLifecycle) {
        lifecycleMap[name] = lifecycle
    }

    /**
     * 绑定监听
     */
    fun init() {
        val lifeCycles = getList()
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity == null) {
                    return
                }
                sCurrentActivity = WeakReference(activity)
                if (createCount == 0) {
                    lifeCycles.forEach { doAsync { it.onFirstCreate(activity) } }
                    lifeCycles.forEach { doSync(activity, it.javaClass, "onFirstCreateSync") { it.onFirstCreateSync(activity) } }
                    lifecyclePublisher.onNext(BaseActivityLifecycle.LifecycleEvent.FIRST_CREATE)
                }
                createCount += 1
                lifeCycles.forEach { doSync(activity, savedInstanceState, it.javaClass, "onActivityCreated") { it.onActivityCreated(activity, savedInstanceState) } }
            }

            override fun onActivityStarted(activity: Activity) {
                if (activity == null) {
                    return
                }
                sCurrentActivity = WeakReference(activity)
                if (startCount == 0) {
                    lifeCycles.forEach { doAsync { it.onGlobalStart(activity) } }
                    lifeCycles.forEach { doSync(activity, it.javaClass, "onGlobalStartSync") { it.onGlobalStartSync(activity) } }
                    lifecyclePublisher.onNext(BaseActivityLifecycle.LifecycleEvent.GLOBAL_START)
                }
                startCount += 1
                lifeCycles.forEach { doSync(activity, it.javaClass, "onActivityStarted") { it.onActivityStarted(activity) } }
            }

            override fun onActivityResumed(activity: Activity) {
                if (startCount == 1) {
                    lifeCycles.forEach { doAsync { it.onGlobalResume(activity) } }
                    lifeCycles.forEach { doSync(activity, it.javaClass, "onGlobalResumeSync") { it.onGlobalResumeSync(activity) } }
                    lifecyclePublisher.onNext(BaseActivityLifecycle.LifecycleEvent.GLOBAL_RESUME)
                }
                lifeCycles.forEach { doSync(activity, it.javaClass, "onActivityResumed") { it.onActivityResumed(activity) } }
            }

            override fun onActivityPaused(activity: Activity) {
                lifeCycles.forEach { doSync(activity, it.javaClass, "onActivityPaused") { it.onActivityPaused(activity) } }
            }

            override fun onActivityStopped(activity: Activity) {
                startCount -= 1
                if (startCount == 0) {
                    lifeCycles.forEach { doAsync { it.onGlobalPause(activity) } }
                    lifeCycles.forEach { doSync(activity, it.javaClass, "onGlobalPauseSync") { it.onGlobalPauseSync(activity) } }
                    lifecyclePublisher.onNext(BaseActivityLifecycle.LifecycleEvent.GLOBAL_PAUSE)

                    lifeCycles.forEach { doAsync { it.onGlobalStop(activity) } }
                    lifeCycles.forEach { doSync(activity, it.javaClass, "onGlobalStopSync") { it.onGlobalStopSync(activity) } }
                    lifecyclePublisher.onNext(BaseActivityLifecycle.LifecycleEvent.GLOBAL_STOP)
                }
                lifeCycles.forEach { doSync(activity, it.javaClass, "onActivityStopped") { it.onActivityStopped(activity) } }
                if (sCurrentActivity?.get() != null) {
                    sCurrentActivity = null
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                createCount -= 1
                if (createCount == 0) {
                    lifeCycles.forEach { doAsync { it.onLastDestroy(activity) } }
                    lifecyclePublisher.onNext(BaseActivityLifecycle.LifecycleEvent.LAST_DESTROY)
                }
                lifeCycles.forEach { doSync(activity, it.javaClass, "onActivityDestroyed") { it.onActivityDestroyed(activity) } }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                lifeCycles.forEach { it.onActivitySaveInstanceState(activity, outState) }
            }

        })
    }

    private fun getList(): List<BaseActivityLifecycle> {
        val lifeCycles: ArrayList<BaseActivityLifecycle> = ArrayList()

        if (lifecycleMap.isEmpty()) {
            return lifeCycles
        }

        for ((key, value) in lifecycleMap) {
            Log.i("LifecycleManager 名称: ", key)
            lifeCycles.add(value)
        }
        return lifeCycles
    }

    private fun doAsync(invokable: () -> Unit) {
        Observable.just(invokable)
                .subscribeOn(Schedulers.single())
                .map { invokable.invoke() }
                .subscribe()
    }

    private fun doSync(activity: Activity?, className: Class<Any>, method: String, invokable: () -> Unit) {
        if (BuildConfig.DEBUG) {
            Log.i("LifecycleManager", "lifecycle:$className; activity:${activity?.localClassName}; method:$method")
        }
        invokable.invoke()
    }

    private fun doSync(activity: Activity?, savedInstanceState: Bundle?, className: Class<Any>, method: String, invokable: () -> Unit) {
        if (BuildConfig.DEBUG) {
            Log.i("LifecycleManager", " lifecycle:$className; activity:${activity?.localClassName}; method:$method")
        }
        invokable.invoke()
    }

}