package com.song.sunset.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.song.sunset.adapters.base.BaseRVAdapterWithoutVH;
import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.model.base.BaseRenderModel;
import com.song.sunset.model.base.RecyclerViewAdapterModel;
import com.song.sunset.model.IfengDefaultAdapterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class IfengListAdapter extends BaseRVAdapterWithoutVH<IfengChannelBean> {

    private Activity context;
    private final RecyclerViewAdapterModel<IfengChannelBean> model;

    public IfengListAdapter(Activity context) {
        this.context = context;
        model = new IfengDefaultAdapterModel();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return model.getViewHolder(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() == null || getData().size() <= 0) {
            return;
        }
        model.render(context, getItemViewType(position), holder, getData().get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (getData() == null || getData().size() <= 0) {
            return 0;
        }
        return model.getViewType(getData().get(position));
    }
}
