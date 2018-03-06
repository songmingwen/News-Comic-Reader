package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
public class ComicListViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView simpleDraweeView;
    public TextView comicName, comicDesc, comicAuthor, comicTags, haveUpdate;
//    public RelativeLayout comicListLayout;
    public ImageView cover;

    public ComicListViewHolder(View itemview) {
        super(itemview);
//        comicListLayout = (RelativeLayout) itemview.findViewById(R.id.id_comic_list_item);
        simpleDraweeView = itemview.findViewById(R.id.id_comic_list_simple_drawee_view);
        cover = itemview.findViewById(R.id.id_comic_list_image_view);
        comicName = itemview.findViewById(R.id.id_comic_list_simple_comic_name);
        comicDesc = itemview.findViewById(R.id.id_comic_list_simple_comic_desc);
        comicAuthor = itemview.findViewById(R.id.id_comic_list_simple_comic_author);
        comicTags = itemview.findViewById(R.id.id_comic_list_simple_comic_tags);
        haveUpdate = itemview.findViewById(R.id.id_comic_collection_have_update);
    }
}
