package com.song.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;


import com.song.sunset.comic.utils.StatusBarUtil;
import com.song.sunset.comic.mvp.CoreBaseView;
import com.song.sunset.comic.mvp.models.CoreBaseModel;
import com.song.sunset.comic.mvp.presenters.CoreBasePresenter;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * Created by hpw on 16/10/12.
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter, M extends CoreBaseModel> extends RxAppCompatActivity {

    protected String TAG;

    public P mPresenter;
    public M mModel;
    protected Context mContext;

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
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView)
            mPresenter.attachVM(this, mModel);
        this.initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
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
