package com.song.sunset.activitys;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.WindowManager;

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
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
}
