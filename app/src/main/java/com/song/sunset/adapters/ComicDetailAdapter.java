package com.song.sunset.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.song.glide_40_transformations.BlurTransformation;
import com.song.sunset.R;
import com.song.sunset.activitys.ComicReadMVPActivity;
import com.song.sunset.activitys.ScalePicActivity;
import com.song.sunset.beans.ChapterListBean;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.holders.ComicDetailBottomViewHolder;
import com.song.sunset.holders.ComicDetailHeaderViewHolder;
import com.song.sunset.holders.ComicDetailListItemViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    public static final int COMIC_DETAIL_TYPE = 0;
    public static final int COMIC_LIST_TYPE = 1;
    public static final int COMIC_BOTTOM_TYPE = 2;
    public static final int EXTRA_ITEM_COUNT = 2;

    private ComicDetailBean data;
    private int color = Color.WHITE;
    private final LayoutInflater mLayoutInflater;

    public ComicDetailAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMIC_DETAIL_TYPE) {
            return new ComicDetailHeaderViewHolder(mLayoutInflater.inflate(R.layout.comic_detail_header, parent, false));
        } else if (viewType == COMIC_LIST_TYPE) {
            return new ComicDetailListItemViewHolder(mLayoutInflater.inflate(R.layout.comic_detail_list_item, parent, false));
        } else if (viewType == COMIC_BOTTOM_TYPE) {
            return new ComicDetailBottomViewHolder(mLayoutInflater.inflate(R.layout.comic_detail_bottom, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (data != null) {
            if (getItemViewType(position) == COMIC_DETAIL_TYPE) {
                ComicDetailHeaderViewHolder headViewHolder = (ComicDetailHeaderViewHolder) holder;
                FrescoUtil.setFrescoCoverImage(headViewHolder.simpleDraweeView, data.getComic().getCover(), ViewUtil.dip2px(113), ViewUtil.dip2px(143));
//                Glide.with(context).load(data.getComic().getCover()).into(headViewHolder.cover);

                headViewHolder.comicName.setText(data.getComic().getName());
                headViewHolder.authorName.setText(data.getComic().getAuthor().getName());
                headViewHolder.authorName.setTextColor(color);
                headViewHolder.comicDetailLayout.setBackgroundColor(color);
                Glide.with(context).load(data.getComic().getCover())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(context, 15)))
                        .into(headViewHolder.imageBg);

                headViewHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScalePicActivity.start(context, data.getComic().getOri(), data.getComic().getComic_id());
                    }
                });
            } else if (getItemViewType(position) == COMIC_LIST_TYPE) {
//                ComicDetailListViewHolder comicDetailListViewHolder = (ComicDetailListViewHolder) holder;
//                ComicDetailListAdapter adapter = new ComicDetailListAdapter(context, data.getComic().getComic_id());
//                comicDetailListViewHolder.recyclerView.setAdapter(adapter);
//                comicDetailListViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
//                adapter.setData(data.getChapter_list());
                ComicDetailListItemViewHolder listItemViewHolder = (ComicDetailListItemViewHolder) holder;
                final List<ChapterListBean> dataList = data.getChapter_list();
                listItemViewHolder.comicListText.setText(dataList.get(dataList.size() - position).getName());
                listItemViewHolder.comicListText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ComicReadMVPActivity.start(context, dataList.size() - position, (ArrayList<ChapterListBean>) dataList);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.getChapter_list() != null && data.getChapter_list().size() >= 1) {
            return data.getChapter_list().size() + EXTRA_ITEM_COUNT;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return COMIC_DETAIL_TYPE;
        } else if (position >= 1 && position < getItemCount() - 1) {
            return COMIC_LIST_TYPE;
        } else if (position >= getItemCount() - 1) {
            return COMIC_BOTTOM_TYPE;
        } else {
            return -1;
        }
    }

    public void setData(ComicDetailBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }
}
