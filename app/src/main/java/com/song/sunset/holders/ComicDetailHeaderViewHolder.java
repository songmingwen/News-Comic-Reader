package com.song.sunset.holders;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout comicDetailLayout;
    public SimpleDraweeView simpleDraweeView, imageBg;
    public TextView comicName;
    public TextView authorName;

    public ComicDetailHeaderViewHolder(View itemview) {
        super(itemview);
        comicDetailLayout = itemview.findViewById(R.id.id_comic_detail_header_layout);
        simpleDraweeView = itemview.findViewById(R.id.id_comic_detail_header_image);
        comicName = itemview.findViewById(R.id.id_comic_detail_header_comic_name);
        authorName = itemview.findViewById(R.id.id_comic_detail_header_author_name);
        imageBg = itemview.findViewById(R.id.img_bg);
    }
}
