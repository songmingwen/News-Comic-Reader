package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.adapters.CollectionComicAdapter;
import com.song.sunset.beans.CollectionOnlineListBean;
import com.song.sunset.beans.ComicCollectionBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.mvp.models.ComicCollectionModel;
import com.song.sunset.mvp.presenters.ComicCollectionPresenter;
import com.song.sunset.mvp.views.ComicCollectionView;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.SPUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.rxjava.RxUtil;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 * E-mail:z53520@qq.com
 */
public class CollectionFragment extends BaseFragment implements RetrofitCallback<CollectionOnlineListBean>, ComicCollectionView {

    private ProgressLayout progressLayout;
    private CollectionComicAdapter adapter;
    private ComicLocalCollectionDao comicLocalCollectionDao;
    private ComicCollectionPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comic_collection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressLayout = view.findViewById(R.id.progress);
        progressLayout.showLoading();

        RecyclerView recyclerView = view.findViewById(R.id.id_comic_collection);
        adapter = new CollectionComicAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        });

        mPresenter = new ComicCollectionPresenter();
        mPresenter.attachVM(this, new ComicCollectionModel());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getNewestCollectedComic();
    }

    private void getDataFromSQLite() {
        List<ComicLocalCollection> list = comicLocalCollectionDao.loadAll();
        if (list == null || list.size() <= 0 || adapter == null) {
            progressLayout.showEmpty();
            return;
        }
        adapter.setData(list);
        progressLayout.showContent();
    }

    @Override
    public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Boolean>)
                emitter -> emitter.onNext(saveCollectedComic(collectionOnlineListBean)))
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        getDataFromSQLite();
                    } else {
                        if (adapter == null) return;
                        adapter.setCollectionList(collectionOnlineListBean.getFavList());
                    }
                });
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        if (SPUtils.getBooleanByName(getActivity(), SPUtils.APP_FIRST_INSTALL, true)) {
            progressLayout.showEmpty();
        }
    }

    private boolean saveCollectedComic(CollectionOnlineListBean collectionOnlineListBean) {
        if (collectionOnlineListBean == null || collectionOnlineListBean.getFavList().isEmpty())
            return false;
        GreenDaoUtil.getDb().beginTransaction();
        for (ComicCollectionBean bean : collectionOnlineListBean.getFavList()) {
            ComicLocalCollection localCollection = new ComicLocalCollection();
            localCollection.setAuthor(bean.getAuthor_name());
            localCollection.setComicId(Long.parseLong(bean.getComic_id()));
            localCollection.setCover(bean.getCover());
            localCollection.setName(bean.getName());
            localCollection.setChapterNum(String.valueOf(bean.getPass_chapter_num()));
            comicLocalCollectionDao.insertOrReplace(localCollection);
        }
        GreenDaoUtil.getDb().setTransactionSuccessful();
        GreenDaoUtil.getDb().endTransaction();
        return true;
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        super.onDestroy();
    }
}
