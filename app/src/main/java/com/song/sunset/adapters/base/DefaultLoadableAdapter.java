package com.song.sunset.adapters.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicDetailMVPActivity;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.beans.ComicsBean;
import com.song.sunset.holders.ComicListViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by songmw3 on 2017/3/3.
 * E-mail:z53520@qq.com
 */
public class DefaultLoadableAdapter extends  BaseRecyclerViewAdapter<ComicsBean, ComicListViewHolder> {

    private Context context;

    public DefaultLoadableAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ComicListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_list_full_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicsBean comicsBean = getData().get(position);
        ComicListViewHolder comicListViewHolder = (ComicListViewHolder) holder;

        int realWidth = (ViewUtil.getScreenWidth() - ViewUtil.dip2px(56)) / 3;
        int realHeight = realWidth * 143 / 113;

        comicListViewHolder.comicName.setText(comicsBean.getName());
        comicListViewHolder.comicDesc.setText(comicsBean.getDescription());
        comicListViewHolder.comicAuthor.setText(comicsBean.getAuthor());
        comicListViewHolder.comicTags.setText(getTags(comicsBean));
        FrescoUtil.setFrescoCoverImage(comicListViewHolder.simpleDraweeView, comicsBean.getCover(), realWidth, realHeight);
    }

    @NonNull
    private StringBuffer getTags(ComicsBean comicsBean) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String tag : comicsBean.getTags()) {
            stringBuffer.append(tag).append(" | ");
        }
        stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length() - 1);
        return stringBuffer;
    }

    @Override
    protected void onItemClick(View view, int position) {
        ComicDetailMVPActivity.start(context, getData().get(position).getComicId());
    }
}
