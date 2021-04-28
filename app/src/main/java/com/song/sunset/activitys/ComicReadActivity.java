package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicReadBean;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicReadAdapter;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.retrofit.Net;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.widget.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Song on 2016/8/30 0030.
 * Email:z53520@qq.com
 */
public class ComicReadActivity extends BaseActivity implements RetrofitCallback<List<ComicReadBean>> {

    public static final String OPEN_POSITION = "open_position";
    private String comicId = "";
    private int openPosition = -1;
    private ScaleRecyclerView recyclerView;
    private ComicReadAdapter adapter;
    private boolean hasCache = false;
    private ProgressLayout progressLayout;

    public static void start(Context context, String comicId, int openPosition) {
        Intent intent = new Intent(context, ComicReadActivity.class);
        intent.putExtra(ComicDetailMVPActivity.COMIC_ID, comicId);
        intent.putExtra(OPEN_POSITION, openPosition);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_read);
        ScreenUtils.fullscreen(this, true);
        progressLayout = (ProgressLayout)findViewById(R.id.progress);
        progressLayout.showLoading();

        if (getIntent() != null) {
            comicId = getIntent().getStringExtra(ComicDetailMVPActivity.COMIC_ID);
            openPosition = getIntent().getIntExtra(OPEN_POSITION, -1);
        }

        initView();
//        getDataFromNet();
        getDataFromRetrofit2();
    }

    private void initView() {
        recyclerView = (ScaleRecyclerView) findViewById(R.id.id_comic_read_recycler);
        adapter = new ComicReadAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(getLinearLayoutManager());

    }

    @NonNull
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        };
    }

    private int getRealPosition(List<ComicReadBean> returnData) {
        int realPosition = 0;
        for (int i = 0; i < openPosition; i++) {
            List<ComicReadBean.ImageListBean> image_list = returnData.get(i).getImage_list();
            for (ComicReadBean.ImageListBean images : image_list) {
                if (images == null || images.getImages() == null) {
                    continue;
                }
                realPosition += images.getImages().size();
            }
        }
        return realPosition;
    }

    @NonNull
    private ArrayList<ComicReadBean.ImageListBean.ImagesBean> getDataList(List<ComicReadBean> returnData) {
        ArrayList<ComicReadBean.ImageListBean.ImagesBean> dataList = new ArrayList<>();
        for (ComicReadBean data : returnData) {
            List<ComicReadBean.ImageListBean> image_list = data.getImage_list();
            for (ComicReadBean.ImageListBean image : image_list) {
                List<ComicReadBean.ImageListBean.ImagesBean> images = image.getImages();
                if (images == null) {
                    continue;
                }
                for (ComicReadBean.ImageListBean.ImagesBean imagesBean : images) {
                    dataList.add(imagesBean);
                }
            }
        }
        return dataList;
    }

    public void getDataFromRetrofit2() {
        progressLayout.showLoading();
        Observable<BaseBean<List<ComicReadBean>>> Observable = Net.createService(U17ComicApi.class).queryComicReadRDByObservable(comicId);
        RxUtil.comicSubscribe(Observable, this);
    }

    @Override
    public void onSuccess(List<ComicReadBean> comicReadBean) {
        progressLayout.showContent();
        ArrayList<ComicReadBean.ImageListBean.ImagesBean> dataList = getDataList(comicReadBean);
        adapter.setData(dataList);
        int realPosition = getRealPosition(comicReadBean);
        recyclerView.scrollToPosition(realPosition);
    }

    @Override
    public void onFailure(int errorCode, String errorMsg) {
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromRetrofit2();
            }
        });
    }
}
