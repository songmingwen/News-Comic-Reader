package com.song.sunset.adapters;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.song.sunset.R;
import com.song.sunset.activitys.PhoenixVideoActivity;
import com.song.sunset.beans.VideoDetailBean;
import com.song.sunset.beans.VideoListsBean;
import com.song.sunset.holders.VideoListHelperHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2017/3/13.
 * E-mail:z53520@qq.com
 */
public class VideoListHelperAdapter extends BaseQuickAdapter<VideoDetailBean, VideoListHelperHolder> {

    private Activity context;

    public VideoListHelperAdapter(Activity context) {
        super(R.layout.videolist_item);
        this.context = context;
    }

    @Override
    protected void convert(VideoListHelperHolder helper, final VideoDetailBean item) {
        if (item == null) {
            return;
        }

        int realWidth = ViewUtil.getScreenWidth();
        int realHeight = realWidth * 9 / 16;

        FrescoUtil.setFrescoImageWith2Url(helper.cover, item.getThumbnail(), item.getImage(), realWidth, realHeight);
        helper.setText(R.id.video_text, item.getTitle());
        helper.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoenixVideoActivity.start(context, item);
            }
        });
    }
}
