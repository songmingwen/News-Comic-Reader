package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
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
        leftLogo = (SimpleDraweeView) inflate.findViewById(R.id.img_left_logo);
        rightLogo = (SimpleDraweeView) inflate.findViewById(R.id.img_right_logo);
        leftTeam = (TextView) inflate.findViewById(R.id.tx_left_team);
        rightTeam = (TextView) inflate.findViewById(R.id.tx_right_team);
        score = (TextView) inflate.findViewById(R.id.tx_score);
        beginTime = (TextView) inflate.findViewById(R.id.tx_begin_time);
    }
}
