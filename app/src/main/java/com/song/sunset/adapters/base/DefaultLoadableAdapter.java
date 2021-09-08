package com.song.sunset.adapters.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.song.sunset.R;
import com.song.sunset.base.adapter.BaseRecyclerViewAdapter;
import com.song.sunset.comic.bean.ComicsBean;
import com.song.sunset.comic.holders.ComicListViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.FrescoUtil;

import static com.song.sunset.comic.ComicDetailActivity.COMIC_ID;

/**
 * Created by Song on 2017/3/3.
 * E-mail:z53520@qq.com
 */
public class DefaultLoadableAdapter extends BaseRecyclerViewAdapter<ComicsBean, ComicListViewHolder> {

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
        ARouter.getInstance().build("/comic/detail").withInt(COMIC_ID, getData().get(position).getComicId()).navigation();
    }
}
