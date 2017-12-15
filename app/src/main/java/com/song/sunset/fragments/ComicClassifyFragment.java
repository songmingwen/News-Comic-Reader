package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.song.sunset.adapters.ComicSearchListAdapter;
import com.song.sunset.beans.ComicSearchResultBean;
import com.song.sunset.utils.StringUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicClassifyBean;
import com.song.sunset.R;
import com.song.sunset.adapters.ComicClassifyAdapter;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.widget.RecyclerViewDivider;

import java.net.URLEncoder;
import java.util.List;

import rx.Observable;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
public class ComicClassifyFragment extends Fragment implements TextWatcher {

    private ProgressLayout progressLayout;
    private ComicClassifyAdapter adapter;
    private EditText mSearchEdit;
    private View mSearchContent;
    private ComicSearchListAdapter mSearchListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comicclassify, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        getDataFromRetrofit2();
    }

    @Override
    public void onPause() {
        super.onPause();
        resetEdit();
    }

    private void initView(View view) {
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress);
        progressLayout.showLoading();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.id_comic_classify_recycler);
        adapter = new ComicClassifyAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth();
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == ComicClassifyAdapter.TOP_TYPE) {
                    return 2;
                } else if (itemViewType == ComicClassifyAdapter.BOTTOM_TYPE) {
                    return 2;
                } else {
                    return 6;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        mSearchEdit = (EditText) view.findViewById(R.id.et_search);
        mSearchContent = view.findViewById(R.id.id_comic_classify_search_list);
        RecyclerView searchRecyclerView = (RecyclerView) view.findViewById(R.id.id_comic_classify_search_recycler);
        mSearchListAdapter = new ComicSearchListAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(mSearchListAdapter);
        searchRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getActivity().getResources().getColor(R.color.Grey_200)));
        mSearchEdit.addTextChangedListener(this);
        ImageView clear = (ImageView) view.findViewById(R.id.img_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEdit();
            }
        });
    }

    private void resetEdit() {
        if (mSearchEdit != null) {
            mSearchEdit.setText("");
        }
    }

    public void getDataFromRetrofit2() {
        Observable<BaseBean<ComicClassifyBean>> observable = RetrofitService.createApi(U17ComicApi.class).queryComicClassifyBeanByObservable(2);
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicClassifyBean>() {
            @Override
            public void onSuccess(ComicClassifyBean comicReadBean) {
                progressLayout.showContent();
                adapter.setData(comicReadBean);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                progressLayout.showRetry(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressLayout.showLoading();
                        getDataFromRetrofit2();
                    }
                });
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String inputString = String.valueOf(s);
        if (TextUtils.isEmpty(inputString)) {
            mSearchContent.setVisibility(View.GONE);
            return;
        }
        Observable<BaseBean<List<ComicSearchResultBean>>> observable = RetrofitService.createApi(U17ComicApi.class).queryComicSearchResultRDByObservable(inputString);
        RxUtil.comicSubscribe(observable, new RetrofitCallback<List<ComicSearchResultBean>>() {
            @Override
            public void onSuccess(List<ComicSearchResultBean> comicSearchResultBeen) {
                mSearchContent.setVisibility(View.VISIBLE);
                mSearchListAdapter.setData(comicSearchResultBeen);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {

            }
        });
    }
}
