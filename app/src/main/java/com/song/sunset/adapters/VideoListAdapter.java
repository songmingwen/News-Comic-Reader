package com.song.sunset.adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.base.adapter.BaseRecyclerViewAdapter;
import com.song.sunset.phoenix.bean.VideoDetailBean;
import com.song.sunset.holders.VideoListViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.FrescoUtil;
import com.song.video.NormalVideoPlayer;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Song on 2016/12/21.
 * E-mail:z53520@qq.com
 */
public class VideoListAdapter extends BaseRecyclerViewAdapter<VideoDetailBean, VideoListViewHolder> {

    private Activity context;

    public VideoListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    protected VideoListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() == null) {
            return;
        }

        VideoDetailBean bean = getData().get(position);
        int realWidth = ViewUtil.getScreenWidth();
        int realHeight = realWidth * 9 / 16;

        VideoListViewHolder videoListViewHolder = (VideoListViewHolder) holder;
        FrescoUtil.setFrescoImageWith2Url(videoListViewHolder.cover, bean.getThumbnail(), bean.getImage(), realWidth, realHeight);
        videoListViewHolder.videoName.setText(bean.getTitle());
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        ViewGroup viewGroup = (ViewGroup) holder.itemView;
        if (viewGroup.getChildCount() > 2) {
            View view = viewGroup.getChildAt(2);
            if (view instanceof NormalVideoPlayer) {
                Completable.fromRunnable(((NormalVideoPlayer) view)::pause)
                        .subscribeOn(Schedulers.io()) // 挑选对应的调度器
                        .subscribe();
            }
            viewGroup.removeViewAt(2);
        }
        super.onViewDetachedFromWindow(holder);
    }

    protected void onItemClick(View view, int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
