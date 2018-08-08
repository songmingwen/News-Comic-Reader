package com.song.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;


import com.song.core.statusbar.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by hpw on 16/10/12.
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter, M extends CoreBaseModel> extends SwipeBackActivity {

    protected String TAG;

    public P mPresenter;
    public M mModel;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        setStatusBarColor();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

        this.setContentView(this.getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView)
            mPresenter.attachVM(this, mModel);
        this.initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        if (mPresenter != null) mPresenter.detachVM();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置无动画
//        return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);
        // 默认竖向(和安卓5.0以上的动画相同)
//        return super.onCreateFragmentAnimator();
    }

    public void setStatusBarColor() {
        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this);
    }

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle bundle) {
        Intent intent = new Intent(this, tarActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        startActivity(tarActivity, null);
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
