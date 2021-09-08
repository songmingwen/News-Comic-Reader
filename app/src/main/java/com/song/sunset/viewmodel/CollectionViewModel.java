package com.song.sunset.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import com.song.sunset.base.viewmodel.BaseViewModel;
import com.song.sunset.comic.bean.CollectionOnlineListBean;
import com.song.sunset.comic.bean.ComicCollectionBean;
import com.song.sunset.comic.bean.ComicLocalCollection;
import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.comic.utils.GreenDaoUtil;
import com.song.sunset.comic.api.U17ComicApi;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.net.Net;
import com.song.sunset.base.rxjava.RxUtil;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import java8.util.Optional;
import java8.util.stream.StreamSupport;

/**
 * @author songmingwen
 * @description
 * @since 2019/1/10
 */
public class CollectionViewModel extends BaseViewModel {

    public MutableLiveData<List<ComicLocalCollection>> mLocalData = new MutableLiveData<>();
    public MutableLiveData<List<ComicCollectionBean>> mOnlineData = new MutableLiveData<>();
    private static ComicLocalCollectionDao comicLocalCollectionDao;

    private CollectionOnlineListBean mCollectionOnlineListBean;

    static {
        comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
    }

    public CollectionViewModel(@NotNull Application application) {
        super(application);
    }

    public void getNewestCollectedComic() {
        Observable<BaseBean<CollectionOnlineListBean>> observable = Net.createService(U17ComicApi.class, getCollectionMap()).queryComicCollectionListRDByObservable("");
        RxUtil.comicSubscribe(observable, new RetrofitCallback<CollectionOnlineListBean>() {
            @Override
            public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
                mCollectionOnlineListBean = collectionOnlineListBean;
                mOnlineData.postValue(collectionOnlineListBean.getFavList());
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {

            }
        });
    }

    public void getLocalCollectedComic() {
        createDao();
        mLocalData.postValue(comicLocalCollectionDao.loadAll());
    }

    public void save(String id) {
        if (mCollectionOnlineListBean == null) {
            return;
        }
        Optional.ofNullable(mCollectionOnlineListBean.getFavList())
                .stream()
                .flatMap(StreamSupport::stream)
                .filter(bean -> TextUtils.equals(bean.getComic_id(), id))
                .findFirst()
                .ifPresent(this::saveCollectedComic);
    }

    private boolean saveCollectedComic(ComicCollectionBean bean) {
        if (bean == null)
            return false;

        GreenDaoUtil.getDb().beginTransaction();
        createDao();
        ComicLocalCollection localCollection = new ComicLocalCollection();
        localCollection.setAuthor(bean.getAuthor_name());
        localCollection.setComicId(Long.parseLong(bean.getComic_id()));
        localCollection.setCover(bean.getCover());
        localCollection.setName(bean.getName());
        localCollection.setChapterNum(String.valueOf(bean.getPass_chapter_num()));
        comicLocalCollectionDao.insertOrReplace(localCollection);
        GreenDaoUtil.getDb().setTransactionSuccessful();
        GreenDaoUtil.getDb().endTransaction();
        return true;
    }

    private void createDao() {
        if (comicLocalCollectionDao == null) {
            comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
        }
    }

    @NonNull
    private Map<String, String> getCollectionMap() {
        Map<String, String> map = new HashMap<>();
        map.put("v", "3360100");
        map.put("key", "387df5b33fc7fe893a7ca573591a9d82ee5695909ca89b94bc237d734e13762664c4437ea3069d86847388c198390f44b7c0947136188de4aca46c4adfd7eaf9c0844103fd9f7b9f554531ff99da3430222d17ed61d61cfede2d27cb667b3173%3A%3A%3Au17");
        map.put("t", System.currentTimeMillis() + "");
        return map;
    }

}
