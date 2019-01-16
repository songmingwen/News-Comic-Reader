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
public class MatchScoreViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView leftLogo, rightLogo;
    public TextView leftTeam, rightTeam, score, beginTime;

    public MatchScoreViewHolder(View inflate) {
        super(inflate);
        leftLogo = inflate.findViewById(R.id.img_left_logo);
        rightLogo = inflate.findViewById(R.id.img_right_logo);
        leftTeam = inflate.findViewById(R.id.tx_left_team);
        rightTeam = inflate.findViewById(R.id.tx_right_team);
        score = inflate.findViewById(R.id.tx_score);
        beginTime = inflate.findViewById(R.id.tx_begin_time);
    }
}
