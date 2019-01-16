package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */
public class BigTopViewHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public BigTopViewHolder(View inflate) {
        super(inflate);
        title = inflate.findViewById(R.id.title);
    }
}
