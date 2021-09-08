package com.song.sunset.comic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.song.sunset.comic.ComicReadMVPActivity;
import com.song.sunset.comic.R;
import com.song.sunset.comic.ScalePicActivity;
import com.song.sunset.comic.bean.ChapterListBean;
import com.song.sunset.comic.bean.ComicDetailBean;
import com.song.sunset.comic.holders.ComicDetailBottomViewHolder;
import com.song.sunset.comic.holders.ComicDetailHeaderViewHolder;
import com.song.sunset.comic.holders.ComicDetailListItemViewHolder;
import com.song.sunset.utils.FrescoUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.fresco.processors.BlurPostprocessor;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    public static final int COMIC_DETAIL_TYPE = 0;
    public static final int COMIC_LIST_TYPE = 1;
    public static final int COMIC_BOTTOM_TYPE = 2;
    public static final int EXTRA_ITEM_COUNT = 2;

    private ComicDetailBean data;
    private int color = Color.WHITE;
    private final LayoutInflater mLayoutInflater;

    public ComicDetailAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMIC_DETAIL_TYPE) {
            return new ComicDetailHeaderViewHolder(mLayoutInflater.inflate(R.layout.comic_detail_header, parent, false));
        } else if (viewType == COMIC_LIST_TYPE) {
            return new ComicDetailListItemViewHolder(mLayoutInflater.inflate(R.layout.comic_detail_list_item, parent, false));
        } else if (viewType == COMIC_BOTTOM_TYPE) {
            return new ComicDetailBottomViewHolder(mLayoutInflater.inflate(R.layout.comic_detail_bottom, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (data != null) {
            if (getItemViewType(holder.getAdapterPosition()) == COMIC_DETAIL_TYPE) {
                ComicDetailHeaderViewHolder headViewHolder = (ComicDetailHeaderViewHolder) holder;

                FrescoUtil.setFrescoImage(headViewHolder.simpleDraweeView, data.getComic().getCover());
                headViewHolder.comicName.setText(data.getComic().getName());
                headViewHolder.authorName.setText(data.getComic().getAuthor().getName());
                headViewHolder.authorName.setTextColor(color);
                headViewHolder.comicDetailLayout.setBackgroundColor(color);
//                Glide.with(context).load(data.getComic().getCover())
//                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(context, 15)))
//                        .into(headViewHolder.imageBg);

                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(data.getComic().getCover()))
                        .setPostprocessor(new BlurPostprocessor(context, 20))//FrescoUtil.getPostProcessor(true)
                        .build();
                headViewHolder.imageBg.setImageRequest(imageRequest);

                headViewHolder.simpleDraweeView.setOnClickListener(
                        v -> ScalePicActivity.start(context, data.getComic().getOri(), data.getComic().getComic_id()));
            } else if (getItemViewType(holder.getAdapterPosition()) == COMIC_LIST_TYPE) {
                ComicDetailListItemViewHolder listItemViewHolder = (ComicDetailListItemViewHolder) holder;
                final List<ChapterListBean> dataList = data.getChapter_list();
                listItemViewHolder.comicListText.setText(dataList.get(dataList.size() - holder.getAdapterPosition()).getName());
                listItemViewHolder.comicListText.setOnClickListener(
                        v -> ComicReadMVPActivity.start(context, dataList.size() - position, (ArrayList<ChapterListBean>) dataList));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.getChapter_list() != null && data.getChapter_list().size() >= 1) {
            return data.getChapter_list().size() + EXTRA_ITEM_COUNT;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return COMIC_DETAIL_TYPE;
        } else if (position >= 1 && position < getItemCount() - 1) {
            return COMIC_LIST_TYPE;
        } else if (position >= getItemCount() - 1) {
            return COMIC_BOTTOM_TYPE;
        } else {
            return -1;
        }
    }

    public void setData(ComicDetailBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }
}
