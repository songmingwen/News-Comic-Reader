package com.song.sunset.comic.holders;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.comic.R;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout comicDetailLayout;
    public SimpleDraweeView simpleDraweeView, imageBg,image1,image2,image3,image4,imageBg1,imageBg2,imageBg3, imageBg4;
    public TextView comicName;
    public TextView authorName;

    public ComicDetailHeaderViewHolder(View itemview) {
        super(itemview);
        comicDetailLayout = itemview.findViewById(R.id.id_comic_detail_header_layout);
        simpleDraweeView = itemview.findViewById(R.id.id_comic_detail_header_image);
        comicName = itemview.findViewById(R.id.id_comic_detail_header_comic_name);
        authorName = itemview.findViewById(R.id.id_comic_detail_header_author_name);
        imageBg = itemview.findViewById(R.id.img_bg);
        image1 = itemview.findViewById(R.id.fg_1);
        imageBg1 = itemview.findViewById(R.id.bg_1);
        image2 = itemview.findViewById(R.id.fg_2);
        imageBg2 = itemview.findViewById(R.id.bg_2);
        image3 = itemview.findViewById(R.id.fg_3);
        imageBg3 = itemview.findViewById(R.id.bg_3);
        image4 = itemview.findViewById(R.id.fg_4);
        imageBg4 = itemview.findViewById(R.id.bg_4);
    }
}
