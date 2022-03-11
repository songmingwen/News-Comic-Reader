package com.song.sunset.comic.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.song.sunset.comic.R;
import com.song.sunset.utils.BitmapUtil;

/**
 * Created by Song on 2017/12/7 0007.
 * E-mail: z53520@qq.com
 */

public class ComicDetailBottomViewHolder extends RecyclerView.ViewHolder {
    public ComicDetailBottomViewHolder(View inflate) {
        super(inflate);
        ImageView imageView = inflate.findViewById(R.id.icon_comic_detail_end);
        BitmapUtil.setIconColor(imageView, 43, 186, 216, 255);
    }
}
