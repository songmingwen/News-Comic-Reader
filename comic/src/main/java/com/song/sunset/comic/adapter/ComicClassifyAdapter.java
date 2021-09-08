package com.song.sunset.comic.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.comic.ComicListActivity;
import com.song.sunset.comic.R;
import com.song.sunset.comic.bean.ComicClassifyBean;
import com.song.sunset.comic.holders.ComicClassifyViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.FrescoUtil;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
public class ComicClassifyAdapter extends RecyclerView.Adapter {

    public static final int TOP_TYPE = 1;
    public static final int BOTTOM_TYPE = 2;
    private Context context;
    private ComicClassifyBean data;

    public ComicClassifyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComicClassifyViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_classify_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicClassifyViewHolder classifyViewHolder = (ComicClassifyViewHolder) holder;
        if (data != null) {
            if (getItemViewType(position) == TOP_TYPE) {
                final ComicClassifyBean.TopListBean topListBean = data.getTopList().get(position);
                bindTopData(classifyViewHolder, topListBean);
            } else if (getItemViewType(position) == BOTTOM_TYPE) {
                final ComicClassifyBean.RankingListBean rankingListBean = data.getRankingList().get(position - data.getTopList().size());
                bindBottomData(classifyViewHolder, rankingListBean);
            }
        }
    }

    private void bindTopData(ComicClassifyViewHolder classifyViewHolder, final ComicClassifyBean.TopListBean topListBean) {
        int realWidth = (ViewUtil.getScreenWidth() - ViewUtil.dip2px(56)) / 3;
        int realHeight = realWidth * 28 / 57;
        classifyViewHolder.textView.setText(topListBean.getSortName());
        FrescoUtil.setFrescoCoverImage(classifyViewHolder.simpleDraweeView, topListBean.getCover(), realWidth, realHeight, false);
        final ComicClassifyBean.TopListBean.ExtraBean.TabListBean tabListBean = topListBean.getExtra().getTabList().get(0);

        classifyViewHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComicListActivity.start(context, tabListBean.getArgName(), tabListBean.getArgValue(), tabListBean.getTabTitle());
            }
        });
    }

    private void bindBottomData(ComicClassifyViewHolder classifyViewHolder, final ComicClassifyBean.RankingListBean rankingListBean) {
        int realWidth = ((ViewUtil.getScreenWidth() - ViewUtil.dip2px(56))) / 3;
        int realHeight = realWidth * 17 / 23;
        classifyViewHolder.textView.setText(rankingListBean.getSortName());
        FrescoUtil.setFrescoCoverImage(classifyViewHolder.simpleDraweeView, rankingListBean.getCover(), realWidth, realHeight);

        classifyViewHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComicListActivity.start(context, rankingListBean.getArgName(), rankingListBean.getArgValue(), rankingListBean.getSortName());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.getTopList().size() + data.getRankingList().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data != null) {
            int size = data.getTopList().size();
            if (position < size) {
                return TOP_TYPE;
            } else {
                return BOTTOM_TYPE;
            }
        } else {
            return 0;
        }
    }

    public void setData(ComicClassifyBean data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
