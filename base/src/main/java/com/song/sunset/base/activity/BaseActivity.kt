package com.song.sunset.base.activity

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.song.sunset.base.net.NetworkLifecycleTransformer
import com.song.sunset.base.net.SchedulerTransformer
import com.song.sunset.base.net.SimplifyRequestTransformer
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import java.util.ArrayList

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/3 9:57
 */
open class BaseActivity : RxAppCompatActivity() {
    private var currTag = ""
    protected var mHandler = Handler()

    val compositeDisposable: CompositeDisposable by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityStack.add(this)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
        activityStack.remove(this)
    }

    /**
     * 返回一个只添加了线程切换的 Transform
     */
    fun <T> bindScheduler(): SchedulerTransformer<T> {
        return SchedulerTransformer()
    }

    /**
     * 1. 执行线程切换
     * 2. 绑定生命周期
     */
    fun <T> bindLifecycleAndScheduler(): NetworkLifecycleTransformer<T> {
        return NetworkLifecycleTransformer(bindUntilEvent(ActivityEvent.DESTROY))
    }

    /**
     * 用于简化 RxJava 请求使用它会：
     * 1. 执行线程切换
     * 2. 绑定生命周期
     * 3. Response 剥离，将 Response[T] 中 T 的剥离
     */
    fun <T> simplifyRequest(): ObservableTransformer<Response<T>, T> {
        return SimplifyRequestTransformer(bindUntilEvent(ActivityEvent.DESTROY))
    }

    fun getmHandler(): Handler {
        return mHandler
    }

    protected fun switchFragment(className: String, layoutId: Int) {
        if (!TextUtils.isEmpty(currTag) && currTag == className) {
            return
        }
        val fragmentTransaction = supportFragmentManager!!.beginTransaction()
        var fragment: Fragment?
        if (!TextUtils.isEmpty(currTag)) {
            fragment = supportFragmentManager!!.findFragmentByTag(currTag)
            if (fragment != null && !fragment.isDetached) {
                fragmentTransaction.hide(fragment)
            }
        }
        fragment = supportFragmentManager!!.findFragmentByTag(className)
        if (fragment == null) {
//            fragment = Fragment.instantiate(this, className);
            fragment = supportFragmentManager!!.fragmentFactory.instantiate(this.classLoader, className)
            fragmentTransaction.add(layoutId, fragment, className)
        } else {
            fragmentTransaction.show(fragment)
        }
        currTag = className
        fragmentTransaction.commitAllowingStateLoss()
    }

    protected fun switchFragment(fragment: Fragment, layoutId: Int) {
        if (!TextUtils.isEmpty(currTag) && currTag == fragment.javaClass.toString()) {
            return
        }
        val fragmentTransaction = supportFragmentManager!!.beginTransaction()
        var innerFragment: Fragment?
        if (!TextUtils.isEmpty(currTag)) {
            innerFragment = supportFragmentManager!!.findFragmentByTag(currTag)
            if (innerFragment != null && !innerFragment.isDetached) {
                fragmentTransaction.hide(innerFragment)
            }
        }
        innerFragment = supportFragmentManager!!.findFragmentByTag(fragment.javaClass.toString())
        if (innerFragment == null) {
            innerFragment = fragment
            fragmentTransaction.add(layoutId, innerFragment, innerFragment.javaClass.toString())
        } else {
            fragmentTransaction.show(innerFragment)
        }
        currTag = fragment.javaClass.toString()
        fragmentTransaction.commitAllowingStateLoss()
    }

    //如果某个fragment每次打开都需要重新加载界面则isUseOld设置为false
    protected fun loadFragment(context: Context?, layoutId: Int, className: String?, bundle: Bundle? = null, isAddToBackStack: Boolean = false, isUseOld: Boolean = false): Fragment {
        val fragmentTransaction = supportFragmentManager!!.beginTransaction()
        var mFragment = supportFragmentManager!!.findFragmentByTag(className)
        if (mFragment == null) {
            mFragment = Fragment.instantiate(context!!, className!!, bundle)
            fragmentTransaction.add(layoutId, mFragment, className)
        } else {
            if (isUseOld) {
                //仅仅刷新界面
                fragmentTransaction.detach(mFragment)
                fragmentTransaction.attach(mFragment)
            }
            if (!mFragment.isAdded) {
                fragmentTransaction.add(layoutId, mFragment, className)
            } else {
                fragmentTransaction.show(mFragment)
            }
        }
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(className)
        }
        if (!isFinishing) {
            fragmentTransaction.commitAllowingStateLoss()
            supportFragmentManager!!.executePendingTransactions()
        }
        return mFragment
    }

    protected fun setDrawerLeftEdgeSize(activity: Activity?, drawerLayout: DrawerLayout?, displayWidthPercentage: Float) {
        if (activity == null || drawerLayout == null) return
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            val leftDraggerField = drawerLayout.javaClass.getDeclaredField("mLeftDragger") //Right
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField[drawerLayout] as ViewDragHelper

            // 找到 edgeSizeField 并设置 Accessible 为true
            val edgeSizeField = leftDragger.javaClass.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible = true
            val edgeSize = edgeSizeField.getInt(leftDragger)

            // 设置新的边缘大小
            val displaySize = Point()
            activity.windowManager.defaultDisplay.getSize(displaySize)
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (displaySize.x * displayWidthPercentage).toInt()))
        } catch (e: NoSuchFieldException) {
        } catch (e: IllegalArgumentException) {
        } catch (e: IllegalAccessException) {
        }
    }

    companion object {
        var activityStack = ArrayList<BaseActivity>()
            protected set
        val topActivity: BaseActivity?
            get() = if (activityStack.size > 0) {
                activityStack[activityStack.size - 1]
            } else null
    }
}