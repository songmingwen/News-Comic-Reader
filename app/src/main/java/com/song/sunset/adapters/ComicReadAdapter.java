package com.song.sunset.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.base.adapter.BaseRecyclerViewAdapter;
import com.song.sunset.comic.bean.ComicReadBean.ImageListBean.ImagesBean;
import com.song.sunset.holders.ComicReadViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.FrescoUtil;

import java.util.List;

/**
 * Created by Song on 2016/8/30 0030.
 * Email:z53520@qq.com
 */
public class ComicReadAdapter extends BaseRecyclerViewAdapter<ImagesBean, ComicReadViewHolder> {
    private Context context;

    public ComicReadAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ComicReadViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicReadViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_read_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicReadViewHolder comicReadViewHolder = (ComicReadViewHolder) holder;
        List<ImagesBean> data = getData();
        if (data != null) {
            ImagesBean imageListBean = data.get(position);
            int realWidth = ViewUtil.getScreenWidth();
            int realHeight = realWidth * imageListBean.getHeight() / imageListBean.getWidth();
            FrescoUtil.setFrescoComicImage(comicReadViewHolder.simpleDraweeView, data.get(position).getImg50(), position + 1, realWidth, realHeight);
        }
    }

    @Override
    protected boolean getItemClickState(){
        return false;
    }
}
