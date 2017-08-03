package com.song.sunset.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.ComicReadImageListBean;
import com.song.sunset.holders.ComicReadViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

import java.util.List;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadMVPAdapter extends BaseRecyclerViewAdapter<ComicReadImageListBean, ComicReadViewHolder> {
    private Context context;

    public ComicReadMVPAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ComicReadViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicReadViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_read_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicReadViewHolder comicReadViewHolder = (ComicReadViewHolder) holder;
        List<ComicReadImageListBean> data = getData();
        if (data != null) {
            ComicReadImageListBean imageListBean = data.get(position);
            int realWidth = ViewUtil.getScreenWidth();
            int realHeight = realWidth * imageListBean.getHeight() / imageListBean.getWidth();
            FrescoUtil.setFrescoCoverImage(comicReadViewHolder.simpleDraweeView, data.get(position).getImg50(), realWidth, realHeight, false);
        }
    }

    @Override
    protected boolean getItemClickState(){
        return false;
    }
}
