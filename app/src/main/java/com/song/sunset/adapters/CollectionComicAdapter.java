package com.song.sunset.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicDetailMVPActivity;
import com.song.sunset.beans.ComicCollectionBean;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.holders.ComicListViewHolder;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.api.U17ComicApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 * E-mail:z53520@qq.com
 */
public class CollectionComicAdapter extends RecyclerView.Adapter<ComicListViewHolder> {
    private Context context;
    private List<ComicLocalCollection> data;
    private List<ComicCollectionBean> mCollectionList;

    public CollectionComicAdapter(Context context) {
        this.context = context;
        if (data == null) {
            data = new ArrayList<>();
        }
    }

    @Override
    public ComicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_list_sample_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ComicListViewHolder holder, final int position) {
        holder.haveUpdate.setVisibility(View.GONE);
        if (data != null) {
            final int chapterNum = Integer.parseInt(data.get(position).getChapterNum());
            final int comicId = (int) data.get(position).getComicId();

            holder.comicName.setText(data.get(position).getName());
            FrescoUtil.setFrescoImage(holder.simpleDraweeView, data.get(position).getCover());
            //显示更新状态
            if (mCollectionList != null) {
                for (ComicCollectionBean bean : mCollectionList) {
                    if (TextUtils.equals(bean.getComic_id(), comicId + "")) {
                        int diff = bean.getPass_chapter_num() - chapterNum;
                        if (diff != 0) {
                            holder.haveUpdate.setVisibility(View.VISIBLE);
                            holder.haveUpdate.setText(String.format(context.getString(R.string.have_update), diff));
                        }
                    }
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComicDetailMVPActivity.start(context, comicId);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(List<ComicLocalCollection> data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        if (data != null && data.size() > 0) {
            if (this.data.size() > 0) {
                this.data.clear();
            }
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void setCollectionList(List<ComicCollectionBean> collectionList) {
        mCollectionList = collectionList;
    }
}
