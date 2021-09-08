package com.song.sunset.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.song.sunset.R;
import com.song.sunset.base.adapter.BaseRecyclerViewAdapter;
import com.song.sunset.base.AppConfig;
import com.song.sunset.base.utils.FileUtils;
import com.song.sunset.comic.bean.ComicReadImageListBean;
import com.song.sunset.holders.ComicReadViewHolder;
import com.song.sunset.utils.PictureUtil;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.FrescoUtil;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadMVPAdapter extends BaseRecyclerViewAdapter<ComicReadImageListBean, ComicReadViewHolder> {
    private Context context;

    public ComicReadMVPAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ComicReadViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicReadViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_read_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicReadViewHolder comicReadViewHolder = (ComicReadViewHolder) holder;
        List<ComicReadImageListBean> data = getData();
        if (data != null) {
            ComicReadImageListBean imageListBean = data.get(position);
            int realWidth = ViewUtil.getScreenWidth();
            int realHeight = realWidth * imageListBean.getHeight() / imageListBean.getWidth();
            FrescoUtil.setFrescoComicImage(comicReadViewHolder.simpleDraweeView, data.get(position).getImg50(), position + 1, realWidth, realHeight);
//            长按事件会影响缩放、双击事件
//            holder.itemView.setOnLongClickListener(v -> {
//                savePic(holder.itemView,data.get(position).getImg50());
//                return false;
//            });
        }
    }

    @Override
    protected boolean getItemClickState(){
        return false;
    }

    private void savePic(View itemView, String img50) {
        FrescoUtil.getCachedImageBitmap(FrescoUtil.getDataSource(img50), new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(@Nullable Bitmap bitmap) {
                new Thread(() -> {
                    if (FileUtils.saveFile(bitmap, "/Sunset/SavedCover", img50)) {
                        String filePath = AppConfig.getApp().getExternalCacheDir().getAbsolutePath() + "/Sunset/SavedCover/" + img50;
                        PictureUtil.saveImageToGallery(context, filePath, img50);
                        itemView.post(() -> Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show());

                    } else {
                        itemView.post(() -> Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }

            @Override
            protected void onFailureImpl(@Nonnull DataSource<CloseableReference<CloseableImage>> dataSource) {
                itemView.post(() -> Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
