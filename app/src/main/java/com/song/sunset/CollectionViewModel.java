package com.song.sunset;

import com.song.sunset.beans.CollectionOnlineListBean;
import com.song.sunset.beans.ComicCollectionBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.GreenDaoUtil;
import com.song.sunset.utils.SPUtils;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.rxjava.RxUtil;
import com.sunset.greendao.gen.ComicLocalCollectionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * @author songmingwen
 * @description
 * @since 2019/1/10
 */
public class CollectionViewModel extends ViewModel {

    private MutableLiveData<List<ComicLocalCollection>> mLocalCollectionLiveData;
    private MutableLiveData<List<ComicCollectionBean>> mCollectionLiveData;
    private static ComicLocalCollectionDao comicLocalCollectionDao;

    static {
        comicLocalCollectionDao = GreenDaoUtil.getDaoSession().getComicLocalCollectionDao();
    }

    public MutableLiveData<List<ComicLocalCollection>> getLocalCollectedLiveData() {
        if (mLocalCollectionLiveData == null) {
            mLocalCollectionLiveData = new MutableLiveData<>();
        }
        return mLocalCollectionLiveData;
    }

    public MutableLiveData<List<ComicCollectionBean>> getCollectedLiveData() {
        if (mCollectionLiveData == null) {
            mCollectionLiveData = new MutableLiveData<>();
        }
        return mCollectionLiveData;
    }

    public void getNewestCollectedComic() {
        Observable<BaseBean<CollectionOnlineListBean>> observable = RetrofitFactory.createApi(U17ComicApi.class, getCollectionMap()).queryComicCollectionListRDByObservable("");
        RxUtil.comicSubscribe(observable, new RetrofitCallback<CollectionOnlineListBean>() {
            @Override
            public void onSuccess(CollectionOnlineListBean collectionOnlineListBean) {
                save(collectionOnlineListBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {

            }
        });
    }

    public void getLocalCollectedComic() {
        createDao();
        getLocalCollectedLiveData().postValue(comicLocalCollectionDao.loadAll());
    }

    private void save(CollectionOnlineListBean collectionOnlineListBean) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<Boolean>)
                emitter -> emitter.onNext(saveCollectedComic(collectionOnlineListBean)))
                .compose(RxUtil.getDefaultScheduler())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        createDao();
                        getLocalCollectedComic();
                    }
                    getCollectedLiveData().postValue(collectionOnlineListBean.getFavList());
                });
    }

    private boolean saveCollectedComic(CollectionOnlineListBean collectionOnlineListBean) {
        if (collectionOnlineListBean == null || collectionOnlineListBean.getFavList().isEmpty())
            return false;
        if (!SPUtils.getBooleanByName(AppConfig.getApp(), SPUtils.APP_FIRST_INSTALL, true)) {
            return false;
        }

        GreenDaoUtil.getDb().beginTransaction();
        createDao();
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

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
