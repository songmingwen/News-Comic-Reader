package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.song.sunset.R;

/**
 * Created by Song on 2017/8/21 0021.
 * E-mail: z53520@qq.com
 */

public class TransViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;

    public TransViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.id_image_trans);
    }
}
