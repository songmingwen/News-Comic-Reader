package com.song.sunset.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicReadMVPActivity;
import com.song.sunset.base.adapter.BaseRecyclerViewAdapter;
import com.song.sunset.comic.bean.ChapterListBean;
import com.song.sunset.holders.ComicDetailListItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2016/8/30 0030.
 * Email:z53520@qq.com
 */
public class ComicDetailListAdapter extends BaseRecyclerViewAdapter<ChapterListBean, ComicDetailListItemViewHolder> {
    private Context context;
    private String comicId = "";

    public ComicDetailListAdapter(Context context, String comic_id) {
        this.context = context;
        this.comicId = comic_id;
    }

    @Override
    protected ComicDetailListItemViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicDetailListItemViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_detail_list_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ComicDetailListItemViewHolder listItemViewHolder = (ComicDetailListItemViewHolder) holder;
        List<ChapterListBean> data = getData();
        listItemViewHolder.comicListText.setText(data.get(data.size() - 1 - position).getName());
    }

    @Override
    protected void onItemClick(View view, int position) {
//        ComicReadActivity.start(context, comicId, getData().size() - 1 - position);
        ComicReadMVPActivity.start(context, getData().size() - 1 - position, (ArrayList<ChapterListBean>) getData());
    }
}
