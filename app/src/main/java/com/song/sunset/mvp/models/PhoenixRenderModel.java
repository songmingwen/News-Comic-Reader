package com.song.sunset.mvp.models;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.beans.PhoenixLiveExt;
import com.song.sunset.beans.PhoenixSportLiveExt;
import com.song.sunset.holders.PhoenixBaseBottomViewHolder;
import com.song.sunset.holders.MatchScoreViewHolder;
import com.song.sunset.utils.fresco.FrescoUtil;

import java.util.ArrayList;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */

public class PhoenixRenderModel {

    @NonNull
    static ArrayList<SimpleDraweeView> getSimpleDraweeViews(SimpleDraweeView image1, SimpleDraweeView image2, SimpleDraweeView image3) {
        ArrayList<SimpleDraweeView> imageList = new ArrayList<>();
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        return imageList;
    }

    static void setSlideImage(PhoenixChannelBean phoenixChannelBean, ArrayList<SimpleDraweeView> imageList) {
        for (int i = 0; i < 3; i++) {
            if (phoenixChannelBean.getStyle().getImages() != null) {
                int count = phoenixChannelBean.getStyle().getImages().size();
                if (i <= count) {
                    FrescoUtil.setFrescoImage(imageList.get(i), phoenixChannelBean.getStyle().getImages().get(i % count));
                } else {
                    FrescoUtil.setFrescoImage(imageList.get(i), null);
                }
            }
        }
    }

    static void setTitleAndBottomData(PhoenixChannelBean phoenixChannelBean, PhoenixBaseBottomViewHolder viewHolder) {
        viewHolder.title.setText(phoenixChannelBean.getTitle());
        if (phoenixChannelBean.getSubscribe() != null) {
            viewHolder.txSource.setText(phoenixChannelBean.getSubscribe().getCatename());
            if (!TextUtils.isEmpty(phoenixChannelBean.getSubscribe().getLogo())) {
                viewHolder.imgSource.setVisibility(View.VISIBLE);
                viewHolder.imgSource.setImageURI(phoenixChannelBean.getSubscribe().getLogo());
            }
            if (phoenixChannelBean.getStyle().getSlideCount() != 0) {
                viewHolder.picCount.setVisibility(View.VISIBLE);
                viewHolder.picCount.setText(phoenixChannelBean.getStyle().getSlideCount() + "");
            }
        }

        if (!TextUtils.equals(phoenixChannelBean.getCommentsall(), "0")) {
            viewHolder.commentCount.setVisibility(View.VISIBLE);
            viewHolder.commentCount.setText(phoenixChannelBean.getCommentsall());
        }

        if (phoenixChannelBean.getUpdateTime() != null) {
            viewHolder.updateTime.setText(phoenixChannelBean.getUpdateTime().substring(11));
        }
    }

    static void setBaseLiveWithScore(PhoenixChannelBean phoenixChannelBean, MatchScoreViewHolder viewHolder) {
        PhoenixSportLiveExt bean = phoenixChannelBean.getSportsLiveExt();
        if (bean == null) {
            return;
        }
        viewHolder.leftLogo.setImageURI(bean.getLeftLogo());
        viewHolder.rightLogo.setImageURI(bean.getRightLogo());
        viewHolder.leftTeam.setText(bean.getLeftName());
        viewHolder.rightTeam.setText(bean.getRightName());
        viewHolder.score.setText(bean.getLeftScore() + " - " + bean.getRightScore());
        viewHolder.beginTime.setText(phoenixChannelBean.getStartTimeStr());
    }

    @NonNull
    static String getLiveState(PhoenixChannelBean phoenixChannelBean) {
        PhoenixLiveExt bean = phoenixChannelBean.getLiveExt();
        String realState;
        if (bean == null) {
            realState = "正在直播";
        } else {
            if (TextUtils.equals(bean.getStatus(), "1")) {
                realState = "即将开始";
            } else if (TextUtils.equals(bean.getStatus(), "2")) {
                realState = "正在直播";
            } else if (TextUtils.equals(bean.getStatus(), "3")) {
                realState = "直播结束";
            } else {
                realState = "正在直播";
            }
        }
        return realState;
    }
}
