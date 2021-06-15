package com.song.sunset.mvp.models;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.song.sunset.R;
import com.song.sunset.activitys.PhoenixNewsActivity;
import com.song.sunset.holders.BigImageViewHolder;
import com.song.sunset.holders.SlideImageViewHolder;
import com.song.sunset.holders.TitleImageViewHolder;
import com.song.sunset.holders.VideoViewViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.mvp.models.base.RecyclerViewAdapterModel;
import com.song.sunset.phoenix.bean.PhoenixChannelBean;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class PhoenixBaseAdapterModel implements RecyclerViewAdapterModel<PhoenixChannelBean> {

    private static final String TITLE_IMAGE = "titleimg";                //左图右文
    private static final String SLIDE_IMAGE = "slideimg";                //三小图
    private static final String BIG_IMG = "bigimg";                      //大图
    private static final String COMMIT_BIG_IMG = "commitbigimg";         //大图
    private static final String COMMIT_VIDEO_BIG_IMG = "commitvideobigimg";//大图


    private static final int TYPE_TITLE_IMAGE_VIEW = 1;                  //左图右文
    private static final int TYPE_SLIDE_IMAGE_VIEW = 2;                  //三小图
    private static final int TYPE_BIG_IMG_VIEW = 3;                      //大图
    private static final int TYPE_COMMIT_BIG_IMG = 4;                      //大图
    private static final int TYPE_COMMIT_VIDEO_BIG_IMG = 5;                      //大图

    public int getViewType(PhoenixChannelBean phoenixChannelBean) {
        if (phoenixChannelBean == null
                || phoenixChannelBean.getStyle() == null
                || TextUtils.isEmpty(phoenixChannelBean.getStyle().getView())) {
            return TYPE_TITLE_IMAGE_VIEW;
        }
        String viewType = phoenixChannelBean.getStyle().getView();
        switch (viewType) {
            case SLIDE_IMAGE:
                return TYPE_SLIDE_IMAGE_VIEW;
            case BIG_IMG:
                return TYPE_BIG_IMG_VIEW;
            case COMMIT_BIG_IMG:
                return TYPE_COMMIT_BIG_IMG;
            case COMMIT_VIDEO_BIG_IMG:
                return TYPE_COMMIT_VIDEO_BIG_IMG;
            default:
                return TYPE_TITLE_IMAGE_VIEW;
        }
    }

    public RecyclerView.ViewHolder getViewHolder(Context context, ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SLIDE_IMAGE_VIEW:
                return new SlideImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_phoenix_slide_image, parent, false));
            case TYPE_BIG_IMG_VIEW:
            case TYPE_COMMIT_BIG_IMG:
                return new BigImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_phoenix_big_image, parent, false));
            case TYPE_COMMIT_VIDEO_BIG_IMG:
                return new VideoViewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_phoenix_video_view, parent, false));
            default:
                return new TitleImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_phoenix_title_image, parent, false));
        }
    }

    public void render(final Context context, int itemViewType, RecyclerView.ViewHolder holder, final PhoenixChannelBean phoenixChannelBean) {
        holder.itemView.setOnClickListener(v ->
                ARouter.getInstance().build("/song/phoenix/news")
                        .withString(PhoenixNewsActivity.PHOENIX_NEWS_URL, phoenixChannelBean.getLink().getWeburl())
                        .navigation());

        PhoenixBaseRenderModel model;

        switch (itemViewType) {
            case TYPE_SLIDE_IMAGE_VIEW:
                model = new SlideImageRenderModel();
                break;
            case TYPE_BIG_IMG_VIEW:
            case TYPE_COMMIT_BIG_IMG:
                model = new BigImageRenderModel();
                break;
            case TYPE_COMMIT_VIDEO_BIG_IMG:
                model = new VideoViewRenderModel();
                break;
            default:
                model = new TitleImageRenderModel();
                break;
        }
        model.render(holder, phoenixChannelBean);
    }
}
