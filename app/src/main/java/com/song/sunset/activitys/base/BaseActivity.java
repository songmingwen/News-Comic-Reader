package com.song.sunset.activitys.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.WindowManager;

import com.song.sunset.utils.SPUtils;

import java.lang.reflect.Field;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
public class BaseActivity extends SwipeBackActivity {
    private String currTag = "";
    protected FragmentManager supportFragmentManager;
    protected Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        supportFragmentManager = getSupportFragmentManager();
    }

    public Handler getmHandler() {
        return mHandler;
    }

    protected void switchFragment(String className, int layoutId) {
        if (!TextUtils.isEmpty(currTag) && currTag.equals(className)) {
            return;
        }
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        // detech old fragment
        Fragment fragment = null;
        if (!TextUtils.isEmpty(currTag)) {
            fragment = supportFragmentManager.findFragmentByTag(currTag);
            if (fragment != null && !fragment.isDetached()) {
                fragmentTransaction.detach(fragment);
            }
        }
        fragment = supportFragmentManager.findFragmentByTag(className);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, className);
            fragmentTransaction.add(layoutId, fragment, className);
        } else {
            fragmentTransaction.attach(fragment);
        }
        currTag = className;
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected Fragment loadFragment(Context context, int layoutId, String className) {
        return this.loadFragment(context, layoutId, className, null);
    }

    protected Fragment loadFragment(Context context, int layoutId, String className, Bundle bundle) {
        return this.loadFragment(context, layoutId, className, bundle, false);
    }

    protected Fragment loadFragment(Context context, int layoutId, String className, Bundle bundle, boolean isAddToBackStack) {
        return this.loadFragment(context, layoutId, className, bundle, isAddToBackStack, false);
    }

    //如果某个fragment每次打开都需要重新加载界面则isUseOld设置为false
    protected Fragment loadFragment(Context context, int layoutId, String className, Bundle bundle, boolean isAddToBackStack, boolean isUseOld) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Fragment mFragment = supportFragmentManager.findFragmentByTag(className);
        if (mFragment == null) {
            mFragment = Fragment.instantiate(context, className, bundle);
            fragmentTransaction.add(layoutId, mFragment, className);
        } else {
            if (isUseOld) {
                //仅仅刷新界面
                fragmentTransaction.detach(mFragment);
                fragmentTransaction.attach(mFragment);
            }
            if (!mFragment.isAdded()) {
                fragmentTransaction.add(layoutId, mFragment, className);
            } else {
                fragmentTransaction.show(mFragment);
            }
        }
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(className);
        }
        if (!isFinishing()) {
            fragmentTransaction.commitAllowingStateLoss();
            supportFragmentManager.executePendingTransactions();
        }
        return mFragment;
    }

    protected void switchDayNightMode() {
        boolean nightMode = isNightMode();
        SPUtils.setBooleanByName(this, SPUtils.APP_NIGHT_MODE, !nightMode);
        getDelegate().setLocalNightMode(nightMode ?
                AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        recreate();//重新启动当前activity
    }

    public boolean isNightMode() {
        return SPUtils.getBooleanByName(this, SPUtils.APP_NIGHT_MODE, false);
    }

    protected void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x * displayWidthPercentage)));
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
        }
    }

}
