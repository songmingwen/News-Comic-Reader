package com.song.sunset.mvp.models;

import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.VideoViewViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;

import static com.song.sunset.utils.fresco.FrescoUtil.NO_CIRCLE;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class VideoViewRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        VideoViewViewHolder viewHolder = (VideoViewViewHolder) holder;
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
