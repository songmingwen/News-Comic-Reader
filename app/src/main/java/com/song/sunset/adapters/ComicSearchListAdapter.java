package com.song.sunset.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.song.sunset.R;
import com.song.sunset.activitys.ComicDetailMVPActivity;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.ComicSearchResultBean;
import com.song.sunset.holders.ComicSearchResultViewHolder;

import static com.song.sunset.activitys.ComicDetailActivity.COMIC_ID;

/**
 * Created by Song on 2017/12/12 0012.
 * E-mail: z53520@qq.com
 */

public class ComicSearchListAdapter extends BaseRecyclerViewAdapter<ComicSearchResultBean, ComicSearchResultViewHolder> {

    private Context mContent;

    public ComicSearchListAdapter(Context context) {
        mContent = context;
    }

    @Override
    protected ComicSearchResultViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicSearchResultViewHolder(LayoutInflater.from(mContent).inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() != null) {
            final ComicSearchResultBean bean = getData().get(position);
            ComicSearchResultViewHolder viewHolder = (ComicSearchResultViewHolder) holder;
            viewHolder.mName.setText(bean.getName());
        }
    }

    @Override
    protected void onItemClick(View view, int position) {
        if (getData() != null) {
            ARouter.getInstance().build("/song/comic/detail").withInt(COMIC_ID, getData().get(position).getComic_id()).navigation();
        }
    }
}
