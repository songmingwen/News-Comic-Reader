package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */
public class ScompreViewViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView image;
    public TextView title, state;

    public ScompreViewViewHolder(View inflate) {
        super(inflate);
        image = inflate.findViewById(R.id.phoenix_image);
        title = inflate.findViewById(R.id.title);
        state = inflate.findViewById(R.id.tx_live_state);
    }
}
