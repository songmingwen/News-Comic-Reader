package com.song.sunset.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.PhoenixVideoActivity;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.holders.VideoListViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by songmw3 on 2016/12/21.
 * E-mail:z53520@qq.com
 */
public class VideoListAdapter extends BaseRecyclerViewAdapter<VideoBean.ItemBean, VideoListViewHolder> {

    private Activity context;

    public VideoListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    protected VideoListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(LayoutInflater.from(context).inflate(R.layout.videolist_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() == null) {
            return;
        }

        final VideoBean.ItemBean bean = getData().get(position);
        int realWidth = ViewUtil.getScreenWidth();
        int realHeight = realWidth * 9 / 16;

        VideoListViewHolder videoListViewHolder = (VideoListViewHolder) holder;
        FrescoUtil.setFrescoImageWith2Url(videoListViewHolder.cover, bean.getThumbnail(), bean.getImage(), realWidth, realHeight);
        videoListViewHolder.videoName.setText(bean.getTitle());
    }

    protected void onItemClick(View view, int position) {
        VideoBean.ItemBean bean = getData().get(position);
        PhoenixVideoActivity.start(context, bean.getVideo_url(), bean.getTitle(), bean.getImage());
    }
}
