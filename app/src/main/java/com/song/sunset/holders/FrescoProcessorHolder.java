package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * @author songmingwen
 * @description
 * @since 2018/9/27
 */
public class FrescoProcessorHolder extends RecyclerView.ViewHolder{
    public SimpleDraweeView image;
    public FrescoProcessorHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.id_img_fresco_processor);
    }
}
