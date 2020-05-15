package com.song.sunset.adapters;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.VideoListActivity;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.TV;
import com.song.sunset.holders.TVListViewHolder;
import com.song.video.DanMuVideoController;
import com.song.video.NormalVideoPlayer;
import com.song.video.VideoManager;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class TVListAdapter extends BaseRecyclerViewAdapter<TV, TVListViewHolder> {
    private Activity mActivity;
    private final NormalVideoPlayer mPlayer;

    public TVListAdapter(Activity context) {
        this.mActivity = context;
        mPlayer = VideoManager.instance().getCurrentNormalVideoPlayer(mActivity);
    }

    @Override
    protected TVListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new TVListViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.tv_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() == null || getData().isEmpty()) {
            return;
        }

        TV tv = getData().get(position);
        mPlayer.release();
        TVListViewHolder tvListViewHolder = (TVListViewHolder) holder;
        tvListViewHolder.textView.setText(tv.tvName);
        tvListViewHolder.content.removeView(mPlayer);

        if (tv.selected) {
            int width = tvListViewHolder.content.getWidth();
            int height = width * 9 / 16;
            ViewGroup.LayoutParams params = tvListViewHolder.content.getLayoutParams();
            params.height = height;
            tvListViewHolder.content.setLayoutParams(params);

            tvListViewHolder.content.addView(mPlayer);
            DanMuVideoController controller = new DanMuVideoController(mActivity);
            controller.setTitle(tv.tvName);
            mPlayer.setController(controller);
            mPlayer.setUp(tv.tvUrl, null);
            mPlayer.start();
        } else {
            ViewGroup.LayoutParams params = tvListViewHolder.content.getLayoutParams();
            params.height = 0;
            tvListViewHolder.content.setLayoutParams(params);
        }
    }

    @Override
    protected void onItemClick(View view, int position) {
        for (int i = 0; i < getData().size(); i++) {
            getData().get(i).selected = i == position;
        }
        notifyDataSetChanged();
    }
}
