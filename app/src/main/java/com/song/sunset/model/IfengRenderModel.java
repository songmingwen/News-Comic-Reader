package com.song.sunset.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.beans.IfengLiveExt;
import com.song.sunset.beans.IfengSportLiveExt;
import com.song.sunset.holders.BigImageViewHolder;
import com.song.sunset.holders.IfengBaseBottomViewHolder;
import com.song.sunset.holders.LiveImageViewHolder;
import com.song.sunset.holders.LongImageViewHolder;
import com.song.sunset.holders.MatchImageViewHolder;
import com.song.sunset.holders.MatchScoreViewHolder;
import com.song.sunset.holders.ScompreViewViewHolder;
import com.song.sunset.holders.SingleTitleViewHolder;
import com.song.sunset.holders.SlideImage2ViewHolder;
import com.song.sunset.holders.SlideImageViewHolder;
import com.song.sunset.holders.TitleImageViewHolder;
import com.song.sunset.holders.VideoViewViewHolder;
import com.song.sunset.utils.DateTimeUtils;
import com.song.sunset.utils.fresco.FrescoUtil;

import java.util.ArrayList;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */

public class IfengRenderModel {

    public void renderSingleTitle(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SingleTitleViewHolder viewHolder = (SingleTitleViewHolder) holder;
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public void renderTitleImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        TitleImageViewHolder viewHolder = (TitleImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public void renderSlideImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SlideImageViewHolder viewHolder = (SlideImageViewHolder) holder;
        setSlideImage(ifengChannelBean, getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public void renderSlideImage2(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SlideImage2ViewHolder viewHolder = (SlideImage2ViewHolder) holder;
        setSlideImage(ifengChannelBean, getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public void renderBigImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        BigImageViewHolder viewHolder = (BigImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public void renderBigImage2(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public void renderVideoView(RecyclerView.ViewHolder holder, final IfengChannelBean ifengChannelBean) {
        VideoViewViewHolder viewHolder = (VideoViewViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public void renderMatchScore(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        MatchScoreViewHolder viewHolder = (MatchScoreViewHolder) holder;
        setBaseLiveWithScore(ifengChannelBean, viewHolder);
    }

    public void renderScompreView(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        ScompreViewViewHolder viewHolder = (ScompreViewViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        viewHolder.state.setText(getLiveState(ifengChannelBean));
        viewHolder.title.setText(ifengChannelBean.getTitle());
    }

    public void renderMatchImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        MatchImageViewHolder viewHolder = (MatchImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.bg, ifengChannelBean.getThumbnail());
        setBaseLiveWithScore(ifengChannelBean, viewHolder);
    }

    public void renderLongImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        LongImageViewHolder viewHolder = (LongImageViewHolder) holder;
        viewHolder.title.setText(ifengChannelBean.getTitle());
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
    }

    public void renderLiveImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        LiveImageViewHolder viewHolder = (LiveImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        viewHolder.title.setText(ifengChannelBean.getTitle());
        viewHolder.tag.setText(getLiveState(ifengChannelBean));
        if (ifengChannelBean.getLiveExt() == null) {
            return;
        }
        viewHolder.time.setText(DateTimeUtils.getLiveTime(ifengChannelBean.getLiveExt().getStartTimeMillis()));
    }

    public void renderBigTop(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public void renderListFocusSlide(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public void renderTopicTitle(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public void renderTopicBannerAd(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public void renderVideoBigImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    @NonNull
    private ArrayList<SimpleDraweeView> getSimpleDraweeViews(SimpleDraweeView image1, SimpleDraweeView image2, SimpleDraweeView image3) {
        ArrayList<SimpleDraweeView> imageList = new ArrayList<>();
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        return imageList;
    }

    private void setSlideImage(IfengChannelBean ifengChannelBean, ArrayList<SimpleDraweeView> imageList) {
        for (int i = 0; i < 3; i++) {
            if (ifengChannelBean.getStyle().getImages() != null) {
                int count = ifengChannelBean.getStyle().getImages().size();
                if (i <= count) {
                    FrescoUtil.setFrescoImage(imageList.get(i), ifengChannelBean.getStyle().getImages().get(i % count));
                } else {
                    FrescoUtil.setFrescoImage(imageList.get(i), null);
                }
            }
        }
    }

    private void setTitleAndBottomData(IfengChannelBean ifengChannelBean, IfengBaseBottomViewHolder viewHolder) {
        viewHolder.title.setText(ifengChannelBean.getTitle());
        if (ifengChannelBean.getSubscribe() != null) {
            viewHolder.txSource.setText(ifengChannelBean.getSubscribe().getCatename());
            if (!TextUtils.isEmpty(ifengChannelBean.getSubscribe().getLogo())) {
                viewHolder.imgSource.setVisibility(View.VISIBLE);
                viewHolder.imgSource.setImageURI(ifengChannelBean.getSubscribe().getLogo());
            }
            if (ifengChannelBean.getStyle().getSlideCount() != 0) {
                viewHolder.picCount.setVisibility(View.VISIBLE);
                viewHolder.picCount.setText(ifengChannelBean.getStyle().getSlideCount() + "");
            }
        }

        if (!TextUtils.equals(ifengChannelBean.getCommentsall(), "0")) {
            viewHolder.commentCount.setVisibility(View.VISIBLE);
            viewHolder.commentCount.setText(ifengChannelBean.getCommentsall());
        }

        if (ifengChannelBean.getUpdateTime() != null) {
            viewHolder.updateTime.setText(ifengChannelBean.getUpdateTime().substring(11));
        }
    }

    private void setBaseLiveWithScore(IfengChannelBean ifengChannelBean, MatchScoreViewHolder viewHolder) {
        IfengSportLiveExt bean = ifengChannelBean.getSportsLiveExt();
        if (bean == null) {
            return;
        }
        viewHolder.leftLogo.setImageURI(bean.getLeftLogo());
        viewHolder.rightLogo.setImageURI(bean.getRightLogo());
        viewHolder.leftTeam.setText(bean.getLeftName());
        viewHolder.rightTeam.setText(bean.getRightName());
        viewHolder.score.setText(bean.getLeftScore() + " - " + bean.getRightScore());
        viewHolder.beginTime.setText(ifengChannelBean.getStartTimeStr());
    }

    @NonNull
    private String getLiveState(IfengChannelBean ifengChannelBean) {
        IfengLiveExt bean = ifengChannelBean.getLiveExt();
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
