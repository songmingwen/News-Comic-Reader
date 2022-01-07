package com.song.sunset.hook.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.song.sunset.hook.R;
import com.song.sunset.hook.bean.RecordData;
import com.song.sunset.hook.utils.DateUtil;

import java.util.List;

/**
 * Desc:    结果展示页面
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/27 15:25
 */
public class HookResultDialog extends Dialog {

    private final List<RecordData> mList;

    private final Context mContext;

    public static void showHookResultDialog(Activity activity, List<RecordData> list) {
        try {
            HookResultDialog dialog = new HookResultDialog(activity, list);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HookResultDialog(@NonNull Context context, List<RecordData> list) {
        super(context);
        this.mList = list;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        View contentView = View.inflate(mContext, R.layout.dialog_hook_result, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        HookResultAdapter adapter = new HookResultAdapter(mContext, mList);
        recyclerView.setAdapter(adapter);
        setContentView(contentView);
    }

    static class HookResultAdapter extends RecyclerView.Adapter<HookResultViewHolder> {

        private final List<RecordData> mAdapterList;

        private final Context mAdapterContext;

        public HookResultAdapter(Context context, List<RecordData> list) {
            this.mAdapterContext = context;
            this.mAdapterList = list;
        }

        @NonNull
        @Override
        public HookResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HookResultViewHolder(LayoutInflater.from(mAdapterContext).inflate(R.layout.viewholder_hook_result, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull HookResultViewHolder holder, int position) {
            if (mAdapterList != null) {
                RecordData recordData = mAdapterList.get(position);
                holder.name.setText(recordData.apiName);
                holder.time.setText(DateUtil.getTime(recordData.timestamp));
                holder.desc.setText(recordData.stacktrace);
            }
        }

        @Override
        public int getItemCount() {
            return mAdapterList.size();
        }
    }

    static class HookResultViewHolder extends RecyclerView.ViewHolder {

        public TextView name, time, desc;

        public HookResultViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
