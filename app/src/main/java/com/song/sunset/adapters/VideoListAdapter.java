package com.song.sunset.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.song.sunset.R;
import com.song.sunset.activitys.VideoActivity;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.holders.VideoListViewHolder;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by songmw3 on 2016/12/21.
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

        VideoListViewHolder videoListViewHolder = (VideoListViewHolder) holder;
        Glide.with(context)
                .load(bean.getImage())
                .into(videoListViewHolder.cover);
        videoListViewHolder.videoName.setText(bean.getTitle());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.start(context, bean.getVideo_url(), bean.getTitle(), bean.getImage());
            }
        };
        videoListViewHolder.cover.setOnClickListener(listener);
        videoListViewHolder.videoName.setOnClickListener(listener);
    }

}
