package com.song.sunset.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.imagepipeline.request.ImageRequest;
import com.song.sunset.R;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.holders.FrescoProcessorHolder;

/**
 * @author songmingwen
 * @description
 * @since 2018/9/27
 */
public class FrescoProcessorAdapter extends BaseRecyclerViewAdapter<ImageRequest, FrescoProcessorHolder> {

    private Context mContext;

    public FrescoProcessorAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected FrescoProcessorHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new FrescoProcessorHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fresco_processor, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() != null && holder instanceof FrescoProcessorHolder) {
            FrescoProcessorHolder viewHolder = (FrescoProcessorHolder) holder;
            viewHolder.image.setImageRequest(getData().get(position));
        }
    }
}
