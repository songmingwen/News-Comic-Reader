package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.song.sunset.R;
import com.song.sunset.adapters.CollectionComicAdapter;
import com.song.sunset.beans.CollectionOnlineListBean;
import com.song.sunset.beans.ComicCollectionBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.SPUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.rxjava.RxUtil;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 * E-mail:z53520@qq.com
 */
public class CollectionFragment extends BaseFragment implements RetrofitCallback<CollectionOnlineListBean> {

    private ProgressLayout progressLayout;
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
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress);
        progressLayout.showLoading();

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
        if (SPUtils.getBooleanByName(getActivity(), SPUtils.APP_FIRST_INSTALL, false)) {
            getNewestCollectionList();
        } else {
            getDataFromSQLite();
            getNewestCollectionList();
        }
    }

    private void getDataFromSQLite() {
        List<ComicLocalCollection> list = comicLocalCollectionDao.loadAll();
        if (list == null || list.size() <= 0) {
            progressLayout.showEmpty();
            return;
        }
        adapter.setData(list);
        progressLayout.showContent();
    }

    private void getNewestCollectionList() {
        Observable<BaseBean<CollectionOnlineListBean>> observable = RetrofitService.createApi(U17ComicApi.class, getCollectionMap()).queryComicCollectionListRDByObservable("");
        RxUtil.comicSubscribe(observable, this);
    }

    @NonNull
    public static Map<String, String> getCollectionMap() {
        Map<String, String> map = new HashMap<>();
        map.put("v", "3360100");
        map.put("key", "387df5b33fc7fe893a7ca573591a9d82ee5695909ca89b94bc237d734e13762664c4437ea3069d86847388c198390f44b7c0947136188de4aca46c4adfd7eaf9c0844103fd9f7b9f554531ff99da3430222d17ed61d61cfede2d27cb667b3173%3A%3A%3Au17");
        map.put("t", System.currentTimeMillis() + "");
        return map;
    }

    @Override
    public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
        if (SPUtils.getBooleanByName(getActivity(), SPUtils.APP_FIRST_INSTALL, true)) {
            saveCollectedComic(collectionOnlineListBean);
            getDataFromSQLite();
        }
        if (adapter == null) return;
        adapter.setCollectionList(collectionOnlineListBean.getFavList());
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        if (SPUtils.getBooleanByName(getActivity(), SPUtils.APP_FIRST_INSTALL, true)) {
            progressLayout.showEmpty();
        }
    }

    private void saveCollectedComic(CollectionOnlineListBean collectionOnlineListBean) {
        if (collectionOnlineListBean == null || collectionOnlineListBean.getFavList().isEmpty())
            return;
        GreenDaoUtil.getDb().beginTransaction();
        for (ComicCollectionBean bean : collectionOnlineListBean.getFavList()) {
            ComicLocalCollection localCollection = new ComicLocalCollection();
            localCollection.setAuthor(bean.getAuthor_name());
            localCollection.setComicId(Long.parseLong(bean.getComic_id()));
            localCollection.setCover(bean.getCover());
            localCollection.setName(bean.getName());
            localCollection.setChapterNum(String.valueOf(bean.getPass_chapter_num()));
            comicLocalCollectionDao.insert(localCollection);
        }
        GreenDaoUtil.getDb().setTransactionSuccessful();
        GreenDaoUtil.getDb().endTransaction();
    }
}
