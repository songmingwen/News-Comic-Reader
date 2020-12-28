package com.song.sunset.holders;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.sunset.R;
import com.song.sunset.R2;
import com.song.sunset.utils.ViewUtil;
import com.zhihu.android.sugaradapter.Layout;
import com.zhihu.android.sugaradapter.SugarHolder;

import androidx.annotation.NonNull;

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
@Layout(R2.layout.recycler_item_empty)
public class DefaultRefreshEmptyHolder extends SugarHolder<DefaultRefreshEmptyHolder.EmptyInfo> {

    private TextView button;
    private View root;
    private ImageView icon;
    private TextView title;
    private TextView message;

    public DefaultRefreshEmptyHolder(@NonNull View view) {
        super(view);
        root = view;
        button = view.findViewById(R.id.button);
        icon = view.findViewById(R.id.icon);
        title = view.findViewById(R.id.title);
        message = view.findViewById(R.id.message);
    }

    @Override
    protected void onBindData(@NonNull EmptyInfo pEmptyInfo) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        if (pEmptyInfo.mBtnHasStrokeCorner) {
            params.topMargin = ViewUtil.dip2px(24);
        } else {
            params.topMargin = ViewUtil.dip2px(0);
        }
        button.setLayoutParams(params);

        if (pEmptyInfo.mEmptyActionListener != null) {
            button.setOnClickListener(pEmptyInfo.mEmptyActionListener);
            button.setVisibility(View.VISIBLE);
            button.setText(pEmptyInfo.mEmptyBtnStringRid);
        } else {
            button.setVisibility(View.GONE);
        }

        if (pEmptyInfo.mEmptyBackgroundId > 0) {
            root.setBackgroundResource(pEmptyInfo.mEmptyBackgroundId);
        }

        if (!TextUtils.isEmpty(pEmptyInfo.mTitle)) {
            title.setVisibility(View.VISIBLE);
            title.setText(pEmptyInfo.mTitle);
        } else {
            title.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(pEmptyInfo.mErrorMessage)) {
            message.setText(pEmptyInfo.mErrorMessage);
        } else {
            message.setText(pEmptyInfo.mEmptyStringRid);
        }

        if (pEmptyInfo.mEmptyIconRid <= 0) {

            if (pEmptyInfo.mEmptyTintIconId <= 0) {
                icon.setVisibility(View.GONE);
            } else {
                icon.setVisibility(View.VISIBLE);
                icon.setImageResource(pEmptyInfo.mEmptyTintIconId);
            }
        } else {
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(pEmptyInfo.mEmptyIconRid);
        }

        itemView.post(() -> {
            this.itemView.getLayoutParams().height = Math.max(this.itemView.getHeight(), pEmptyInfo.mEmptyViewHeight);
            this.itemView.requestLayout();
        });
    }

    public static class EmptyInfo {

        public String mTitle;

        public String mErrorMessage;

        public int mEmptyStringRid;

        public int mEmptyIconRid;

        public int mEmptyBtnStringRid;

        public View.OnClickListener mEmptyActionListener;

        public int mEmptyViewHeight;

        public boolean mBtnHasStrokeCorner = false;

        /**
         * 单张图片，日夜间切换主题，只改变 TintColor
         */
        public int mEmptyTintColorId;
        public int mEmptyTintIconId;

        public int mEmptyBackgroundId = 0;

        public boolean mHasError;

        public EmptyInfo(int pEmptyStringRid, int pIconRid, int pHeight) {
            this(pEmptyStringRid, pIconRid, pHeight, 0, null);
        }

        public EmptyInfo(int pLabelStringRid, int pIconRid, int pHeight, int pEmptyBtnStringRid, View.OnClickListener
                listener) {
            this.mEmptyStringRid = pLabelStringRid;
            this.mEmptyIconRid = pIconRid;
            this.mEmptyViewHeight = pHeight;
            this.mEmptyBtnStringRid = pEmptyBtnStringRid;
            this.mEmptyActionListener = listener;
        }

        public EmptyInfo(String errorMessage, int iconRid, int height, int emptyBtnStringRid, View.OnClickListener
                listener) {
            this.mErrorMessage = errorMessage;
            this.mEmptyIconRid = iconRid;
            this.mEmptyViewHeight = height;
            this.mEmptyBtnStringRid = emptyBtnStringRid;
            this.mEmptyActionListener = listener;
        }


        public EmptyInfo(String title, String errorMessage, int iconRid, int height, int emptyBtnStringRid, View
                .OnClickListener listener) {
            this.mTitle = title;
            this.mErrorMessage = errorMessage;
            this.mEmptyIconRid = iconRid;
            this.mEmptyViewHeight = height;
            this.mEmptyBtnStringRid = emptyBtnStringRid;
            this.mEmptyActionListener = listener;
        }

        public EmptyInfo(int emptyStringRid, int pIconRid, int tintColorId, int pHeight) {
            this(emptyStringRid, pIconRid, tintColorId, pHeight, 0, false, null);
        }

        /**
         * @param emptyStringRid    empty 信息
         * @param iconTintRid       待着色 ImageResource
         * @param tintColorId       着色 Id
         * @param height            height
         * @param emptyBtnStringRid btn内容
         * @param listener          btn 点击事件
         */
        public EmptyInfo(int emptyStringRid, int iconTintRid, int tintColorId, int height, int emptyBtnStringRid,
                         boolean hasStrokeCorner, View.OnClickListener listener) {
            this.mEmptyStringRid = emptyStringRid;
            this.mEmptyTintIconId = iconTintRid;
            this.mEmptyTintColorId = tintColorId;
            this.mEmptyViewHeight = height;
            this.mEmptyBtnStringRid = emptyBtnStringRid;
            this.mEmptyActionListener = listener;
            this.mBtnHasStrokeCorner = hasStrokeCorner;
        }

        public void setHasError(boolean hasError) {
            mHasError = hasError;
        }
    }
}