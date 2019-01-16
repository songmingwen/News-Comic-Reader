package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
public class ComicClassifyViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView simpleDraweeView;
    public TextView textView;

    public ComicClassifyViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = itemView.findViewById(R.id.id_comic_classify_item);
        textView = itemView.findViewById(R.id.id_comic_classify_item_text);
    }
}
