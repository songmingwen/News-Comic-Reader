package com.song.sunset.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.BigImageViewHolder;
import com.song.sunset.holders.IfengBaseBottomViewHolder;
import com.song.sunset.holders.LongImageViewHolder;
import com.song.sunset.holders.SingleTitleViewHolder;
import com.song.sunset.holders.SlideImage2ViewHolder;
import com.song.sunset.holders.SlideImageViewHolder;
import com.song.sunset.holders.TitleImageViewHolder;
import com.song.sunset.holders.VideoViewViewHolder;
import com.song.sunset.utils.fresco.FrescoUtil;

import java.util.ArrayList;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */

public class IfengRenderUtils {

    public static void renderSingleTitle(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SingleTitleViewHolder viewHolder = (SingleTitleViewHolder) holder;
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public static void renderTitleImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        TitleImageViewHolder viewHolder = (TitleImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public static void renderSlideImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SlideImageViewHolder viewHolder = (SlideImageViewHolder) holder;
        setSlideImage(ifengChannelBean, getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public static void renderSlideImage2(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SlideImage2ViewHolder viewHolder = (SlideImage2ViewHolder) holder;
        setSlideImage(ifengChannelBean, getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public static void renderBigImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        BigImageViewHolder viewHolder = (BigImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public static void renderBigImage2(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderVideoView(RecyclerView.ViewHolder holder, final IfengChannelBean ifengChannelBean) {
        VideoViewViewHolder viewHolder = (VideoViewViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        setTitleAndBottomData(ifengChannelBean, viewHolder);
    }

    public static void renderMatchScore(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderScompreView(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderMatchImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderLongImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        LongImageViewHolder viewHolder = (LongImageViewHolder) holder;
        viewHolder.title.setText(ifengChannelBean.getTitle());
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
    }

    public static void renderLiveImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderBigTop(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderListFocusSlide(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderTopicTitle(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderTopicBannerAd(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    public static void renderVideoBigImage(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {

    }

    @NonNull
    private static ArrayList<SimpleDraweeView> getSimpleDraweeViews(SimpleDraweeView image1, SimpleDraweeView image2, SimpleDraweeView image3) {
        ArrayList<SimpleDraweeView> imageList = new ArrayList<>();
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        return imageList;
    }

    private static void setSlideImage(IfengChannelBean ifengChannelBean, ArrayList<SimpleDraweeView> imageList) {
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

    private static void setTitleAndBottomData(IfengChannelBean ifengChannelBean, IfengBaseBottomViewHolder viewHolder) {
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
}
