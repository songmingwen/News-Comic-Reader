package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.song.sunset.R;

/**
 * Created by Song on 2017/12/12 0012.
 * E-mail: z53520@qq.com
 */

public class ComicSearchResultViewHolder extends RecyclerView.ViewHolder{

    public TextView mName;

    public ComicSearchResultViewHolder(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.txt_search);
    }
}
