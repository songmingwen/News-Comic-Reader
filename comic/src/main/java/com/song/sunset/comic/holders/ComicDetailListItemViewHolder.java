package com.song.sunset.comic.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.song.sunset.comic.R;

/**
 * Created by Song on 2016/8/30 0030.
 * Email:z53520@qq.com
 */
public class ComicDetailListItemViewHolder extends RecyclerView.ViewHolder {

    public TextView comicListText;

    public ComicDetailListItemViewHolder(View itemView) {
        super(itemView);
        comicListText = itemView.findViewById(R.id.id_comic_detail_list_item_text);
    }
}
