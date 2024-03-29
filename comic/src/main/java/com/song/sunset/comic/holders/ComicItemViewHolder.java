package com.song.sunset.comic.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.comic.ComicDetailActivity;
import com.song.sunset.comic.R;
import com.song.sunset.comic.R2;
import com.song.sunset.comic.bean.ComicsBean;
import com.zhihu.android.sugaradapter.Layout;
import com.zhihu.android.sugaradapter.SugarHolder;

import androidx.annotation.NonNull;

/**
 * @author songmingwen
 * @description
 * @since 2020/12/17
 */
@Layout(R2.layout.comic_list_full_item)
public class ComicItemViewHolder extends SugarHolder<ComicsBean> {

    public SimpleDraweeView simpleDraweeView;
    public TextView comicName, comicDesc, comicAuthor, comicTags, haveUpdate;
    public ImageView cover;

    public ComicItemViewHolder(View itemview) {
        super(itemview);
        simpleDraweeView = itemview.findViewById(R.id.id_comic_list_simple_drawee_view);
        cover = itemview.findViewById(R.id.id_comic_list_image_view);
        comicName = itemview.findViewById(R.id.id_comic_list_simple_comic_name);
        comicDesc = itemview.findViewById(R.id.id_comic_list_simple_comic_desc);
        comicAuthor = itemview.findViewById(R.id.id_comic_list_simple_comic_author);
        comicTags = itemview.findViewById(R.id.id_comic_list_simple_comic_tags);
        haveUpdate = itemview.findViewById(R.id.id_comic_collection_have_update);
    }

    @Override
    protected void onBindData(@NonNull ComicsBean comicsBean) {
        comicName.setText(comicsBean.getName());
        comicDesc.setText(comicsBean.getDescription());
        comicAuthor.setText(comicsBean.getAuthor());
        comicTags.setText(getTags(comicsBean));
        simpleDraweeView.setImageURI(comicsBean.getCover());
        itemView.setOnClickListener(v -> ARouter.getInstance()
                .build("/comic/detail")
                .withInt(ComicDetailActivity.COMIC_ID, comicsBean.getComicId())
                .navigation());
    }

    @NonNull
    private StringBuffer getTags(ComicsBean comicsBean) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String tag : comicsBean.getTags()) {
            stringBuffer.append(tag).append(" | ");
        }
        stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length() - 1);
        return stringBuffer;
    }
}
