package com.song.sunset.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.IfengNewsActivity;
import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.BigImage2ViewHolder;
import com.song.sunset.holders.BigImageViewHolder;
import com.song.sunset.holders.BigTopViewHolder;
import com.song.sunset.holders.ListFocusSlideViewHolder;
import com.song.sunset.holders.LiveImageViewHolder;
import com.song.sunset.holders.LongImageViewHolder;
import com.song.sunset.holders.MatchImageViewHolder;
import com.song.sunset.holders.MatchScoreViewHolder;
import com.song.sunset.holders.ScompreViewViewHolder;
import com.song.sunset.holders.SingleTitleViewHolder;
import com.song.sunset.holders.SlideImage2ViewHolder;
import com.song.sunset.holders.SlideImageViewHolder;
import com.song.sunset.holders.TitleImageViewHolder;
import com.song.sunset.holders.TopicBannerAdViewHolder;
import com.song.sunset.holders.TopicTitleViewHolder;
import com.song.sunset.holders.VideoBigImageViewHolder;
import com.song.sunset.holders.VideoViewViewHolder;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class IfengBaseAdapterModel implements RecyclerViewAdapterModel<IfengChannelBean>{

    public static final String SINGLE_TITLE = "singletitle";               //单标题
    public static final String TITLE_IMAGE = "titleimg";                  //左图右文
    public static final String SLIDE_IMAGE = "slideimg";                  //三小图
    public static final String SLIDE_IMAGE2 = "slideimg2";               //一大两小
    public static final String BIG_IMG = "bigimg";                           //大图
    public static final String BIG_IMG2 = "bigimg2";                        //美女段子大图
    public static final String VIDEO = "video";                                //视频当前页播放
    public static final String MATCH_SCORE = "matchscore";           //比赛有比分
    public static final String MATCH_SCOMPRE = "matchscompre";   //比赛无比分
    public static final String MATCH_IMG = "matchimg";                  //比赛底图
    public static final String LONG_IMG = "longimg";                       //直播长图
    public static final String LIVE_IMG = "liveimg";                         //直播浮层
    public static final String BIG_TOPIC = "bigtopic";                      //大专题
    public static final String LIST_FOCUS_SLIDER = "focusslider";
    public static final String TOPIC_TITLE = "topictitle";
    public static final String TOPIC_BANNER_ADV = "topicbanneradv";
    public static final String VIDEO_BIG_IMG = "videobigimg";           //视频大图当页播放样式

    public static final int SINGLE_TITLE_VIEW_TYPE = 0;             //单标题
    public static final int TITLE_IMAGE_VIEW_TYPE = 1;              //左图右文
    public static final int SLIDE_IMAGE_VIEW_TYPE = 2;              //三小图
    public static final int SLIDE_IMAGE2_VIEW_TYPE = 3;            //一大两小
    public static final int BIG_IMG_VIEW_TYPE = 4;                     //大图
    public static final int BIG_IMG2_VIEW_TYPE = 5;                   //美女段子大图
    public static final int VIDEO_VIEW_TYPE = 6;                        //视频当前页播放
    public static final int MATCH_SCORE_VIEW_TYPE = 7;           //比赛有比分
    public static final int MATCH_SCOMPRE_VIEW_TYPE = 8;       //比赛无比分
    public static final int MATCH_IMG_VIEW_TYPE = 9;                //比赛底图
    public static final int LONG_IMG_VIEW_TYPE = 10;                //直播长图
    public static final int LIVE_IMG_VIEW_TYPE = 11;                  //直播浮层
    public static final int BIG_TOPIC_VIEW_TYPE = 12;                //大专题
    public static final int LIST_FOCUS_SLIDER_VIEW_TYPE = 13;
    public static final int TOPIC_TITLE_VIEW_TYPE = 14;
    public static final int TOPIC_BANNER_ADV_VIEW_TYPE = 15;
    public static final int VIDEO_BIG_IMG_VIEW_TYPE = 16;          //视频大图当页播放样式

    public int getViewType(IfengChannelBean ifengChannelBean) {
        if (ifengChannelBean == null
                || ifengChannelBean.getStyle() == null
                || TextUtils.isEmpty(ifengChannelBean.getStyle().getView())) {
            return SINGLE_TITLE_VIEW_TYPE;
        }
        String viewType = ifengChannelBean.getStyle().getView();
        if (TextUtils.equals(viewType, SINGLE_TITLE)) {
            return SINGLE_TITLE_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, TITLE_IMAGE)) {
            return TITLE_IMAGE_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, SLIDE_IMAGE)) {
            return SLIDE_IMAGE_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, SLIDE_IMAGE2)) {
            return SLIDE_IMAGE2_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, BIG_IMG)) {
            return BIG_IMG_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, BIG_IMG2)) {
            return BIG_IMG2_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, VIDEO)) {
            return VIDEO_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, MATCH_SCORE)) {
            return MATCH_SCORE_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, MATCH_SCOMPRE)) {
            return MATCH_SCOMPRE_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, MATCH_IMG)) {
            return MATCH_IMG_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, LONG_IMG)) {
            return LONG_IMG_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, LIVE_IMG)) {
            return LIVE_IMG_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, BIG_TOPIC)) {
            return BIG_TOPIC_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, LIST_FOCUS_SLIDER)) {
            return LIST_FOCUS_SLIDER_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, TOPIC_TITLE)) {
            return TOPIC_TITLE_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, TOPIC_BANNER_ADV)) {
            return TOPIC_BANNER_ADV_VIEW_TYPE;
        } else if (TextUtils.equals(viewType, VIDEO_BIG_IMG)) {
            return VIDEO_BIG_IMG_VIEW_TYPE;
        } else {
            return SINGLE_TITLE_VIEW_TYPE;
        }
    }

    public RecyclerView.ViewHolder getViewHolder(Context context, ViewGroup parent, int viewType) {
        switch (viewType) {
            case SINGLE_TITLE_VIEW_TYPE:
                return new SingleTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_single_title, parent, false));
            case TITLE_IMAGE_VIEW_TYPE:
                return new TitleImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_title_image, parent, false));
            case SLIDE_IMAGE_VIEW_TYPE:
                return new SlideImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_slide_image, parent, false));
            case SLIDE_IMAGE2_VIEW_TYPE:
                return new SlideImage2ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_slide_image2, parent, false));
            case BIG_IMG_VIEW_TYPE:
                return new BigImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_big_image, parent, false));
            case BIG_IMG2_VIEW_TYPE:
                return new BigImage2ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_big_image2, parent, false));
            case VIDEO_VIEW_TYPE:
                return new VideoViewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_video_view, parent, false));
            case MATCH_SCORE_VIEW_TYPE:
                return new MatchScoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_match_score, parent, false));
            case MATCH_SCOMPRE_VIEW_TYPE:
                return new ScompreViewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_scompre_view, parent, false));
            case MATCH_IMG_VIEW_TYPE:
                return new MatchImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_match_image, parent, false));
            case LONG_IMG_VIEW_TYPE:
                return new LongImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_long_view, parent, false));
            case LIVE_IMG_VIEW_TYPE:
                return new LiveImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_live_image, parent, false));
            case BIG_TOPIC_VIEW_TYPE:
                return new BigTopViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_big_topic, parent, false));
            case LIST_FOCUS_SLIDER_VIEW_TYPE:
                return new ListFocusSlideViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_list_focus_slide, parent, false));
            case TOPIC_TITLE_VIEW_TYPE:
                return new TopicTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_topic_title, parent, false));
            case TOPIC_BANNER_ADV_VIEW_TYPE:
                return new TopicBannerAdViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_banner_adv, parent, false));
            case VIDEO_BIG_IMG_VIEW_TYPE:
                return new VideoBigImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_video_big_image, parent, false));
            default:
                return new SingleTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ifeng_single_title, parent, false));
        }
    }

    public void render(final Context context, int itemViewType, RecyclerView.ViewHolder holder, final IfengChannelBean ifengChannelBean) {
        IfengRenderModel model = new IfengRenderModel();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IfengNewsActivity.start(context, ifengChannelBean.getLink().getWeburl());
            }
        });

        switch (itemViewType) {
            case SINGLE_TITLE_VIEW_TYPE:
                model.renderSingleTitle(holder, ifengChannelBean);
                break;
            case TITLE_IMAGE_VIEW_TYPE:
                model.renderTitleImage(holder, ifengChannelBean);
                break;
            case SLIDE_IMAGE_VIEW_TYPE:
                model.renderSlideImage(holder, ifengChannelBean);
                break;
            case SLIDE_IMAGE2_VIEW_TYPE:
                model.renderSlideImage2(holder, ifengChannelBean);
                break;
            case BIG_IMG_VIEW_TYPE:
                model.renderBigImage(holder, ifengChannelBean);
                break;
            case BIG_IMG2_VIEW_TYPE:
                model.renderBigImage2(holder, ifengChannelBean);
                break;
            case VIDEO_VIEW_TYPE:
                model.renderVideoView(holder, ifengChannelBean);
                break;
            case MATCH_SCORE_VIEW_TYPE:
                model.renderMatchScore(holder, ifengChannelBean);
                break;
            case MATCH_SCOMPRE_VIEW_TYPE:
                model.renderScompreView(holder, ifengChannelBean);
                break;
            case MATCH_IMG_VIEW_TYPE:
                model.renderMatchImage(holder, ifengChannelBean);
                break;
            case LONG_IMG_VIEW_TYPE:
                model.renderLongImage(holder, ifengChannelBean);
                break;
            case LIVE_IMG_VIEW_TYPE:
                model.renderLiveImage(holder, ifengChannelBean);
                break;
            case BIG_TOPIC_VIEW_TYPE:
                model.renderBigTop(holder, ifengChannelBean);
                break;
            case LIST_FOCUS_SLIDER_VIEW_TYPE:
                model.renderListFocusSlide(holder, ifengChannelBean);
                break;
            case TOPIC_TITLE_VIEW_TYPE:
                model.renderTopicTitle(holder, ifengChannelBean);
                break;
            case TOPIC_BANNER_ADV_VIEW_TYPE:
                model.renderTopicBannerAd(holder, ifengChannelBean);
                break;
            case VIDEO_BIG_IMG_VIEW_TYPE:
                model.renderVideoBigImage(holder, ifengChannelBean);
                break;
            default:
                model.renderSingleTitle(holder, ifengChannelBean);
                break;
        }
    }
}
