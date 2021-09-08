package com.song.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.song.sunset.comic.mvp.CoreBaseView;
import com.song.sunset.comic.mvp.models.CoreBaseModel;
import com.song.sunset.comic.mvp.presenters.CoreBasePresenter;
import com.trello.rxlifecycle3.components.RxFragment;

/**
 * Created by hpw on 16/10/27.
 */

public abstract class CoreBaseFragment<T extends CoreBasePresenter, E extends CoreBaseModel> extends RxFragment {
    protected String TAG;

    public T mPresenter;
    public E mModel;
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            return inflater.inflate(getLayoutId(), null);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //设置状态栏透明
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        initUI(view, savedInstanceState);
        if (this instanceof CoreBaseView) mPresenter.attachVM(this, mModel);
        getBundle(getArguments());
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {

    }

    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
