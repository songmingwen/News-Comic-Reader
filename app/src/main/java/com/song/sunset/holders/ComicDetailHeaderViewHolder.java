package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailHeaderViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout comicDetailLayout;
    public SimpleDraweeView simpleDraweeView;
    public TextView comicName;
    public TextView authorName;
    public ImageView cover, imageBg;

    public ComicDetailHeaderViewHolder(View itemview) {
        super(itemview);
        comicDetailLayout = (RelativeLayout) itemview.findViewById(R.id.id_comic_detail_header_layout);
        simpleDraweeView = (SimpleDraweeView) itemview.findViewById(R.id.id_comic_detail_header_image);
        cover = (ImageView) itemview.findViewById(R.id.id_comic_detail_header_img);
        comicName = (TextView) itemview.findViewById(R.id.id_comic_detail_header_comic_name);
        authorName = (TextView) itemview.findViewById(R.id.id_comic_detail_header_author_name);
        imageBg = (ImageView) itemview.findViewById(R.id.img_bg);
    }
}
