package com.song.sunset.mvp.models;

import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.song.sunset.phoenix.bean.PhoenixChannelBean;
import com.song.sunset.holders.BigImageViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.FrescoUtil;

import static com.song.sunset.utils.FrescoUtil.NO_CIRCLE;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class BigImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        BigImageViewHolder viewHolder = (BigImageViewHolder) holder;
        viewHolder.videoImage.setVisibility(TextUtils.equals(phoenixChannelBean.getType(), "phvideo") ? View.VISIBLE : View.GONE);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(phoenixChannelBean.getThumbnail()))
                .setAutoPlayAnimations(true)
                .setOldController(viewHolder.image.getController())
                .build();
        viewHolder.image.setHierarchy(FrescoUtil.getHierarchy(NO_CIRCLE, false));
        viewHolder.image.setController(controller);
        PhoenixRenderModel.setTitleAndBottomData(phoenixChannelBean, viewHolder);
    }
}
