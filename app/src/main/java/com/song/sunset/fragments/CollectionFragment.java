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
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.List;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 * E-mail:z53520@qq.com
 */
public class CollectionFragment extends BaseFragment {

    private LoadingAndRetryManager mLoadingAndRetryManager;
    private CollectionComicAdapter adapter;
    private ComicLocalCollectionDao comicLocalCollectionDao;

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
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoadingAndRetryManager.showLoading();
                    }
                });
            }
        });
        mLoadingAndRetryManager.showLoading();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_comic_collection);
        adapter = new CollectionComicAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromSQLite();
    }

    public void getDataFromSQLite() {
        List<ComicLocalCollection> list = comicLocalCollectionDao.loadAll();
        adapter.setData(list);
        mLoadingAndRetryManager.showContent();
    }
}
