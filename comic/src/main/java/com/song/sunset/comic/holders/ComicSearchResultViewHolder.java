package com.song.sunset.comic.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.song.sunset.comic.R;

/**
 * Created by Song on 2017/12/12 0012.
 * E-mail: z53520@qq.com
 */

public class ComicSearchResultViewHolder extends RecyclerView.ViewHolder{

    public TextView mName;

    public ComicSearchResultViewHolder(View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.txt_search);
    }
}
