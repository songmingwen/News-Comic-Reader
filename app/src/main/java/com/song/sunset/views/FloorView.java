package com.song.sunset.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.song.sunset.R;
import com.song.sunset.beans.Comment;
import com.song.sunset.beans.User;
import com.song.sunset.utils.ViewUtil;

import java.util.List;

/**
 * Created by Song on 2016/12/5.
 */

public class FloorView extends LinearLayout {
    private Context context;//上下文环境引用

    private Drawable drawable;//背景Drawable

    public FloorView(Context context) {
        this(context, null);
    }

    public FloorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public FloorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        //获取背景Drawable的资源文件
        drawable = context.getResources().getDrawable(R.drawable.shape_comment_bg);
    }

    /**
     * 设置Comment数据
     *
     * @param comments Comment数据列表
     */
    public void setComments(List<Comment> comments) {
        //清除子View
        removeAllViews();

        //获取评论数
        int count = comments.size();

        /**
         *如果评论条数小于9条则直接显示，否则我们只显示评论的头两条和最后一条（这里的最后一条是相对于PostView中已经显示的一条评论来说的）
         */
        if (count < 9) {
            initViewWithAll(comments);
        } else {
            initViewWithHide(comments);
        }
    }

    /**
     * 初始化所有的View
     *
     * @param comments 评论数据列表
     */
    private void initViewWithAll(List<Comment> comments) {
        for (int i = 0; i < comments.size(); i++) {
            View commentView = getView(comments.get(i), i, i + 1, comments.size(), false);
            addView(commentView);
        }
    }

    /**
     * 初始化带有隐藏楼层的View
     *
     * @param comments 评论数据列表
     */
    private void initViewWithHide(final List<Comment> comments) {
        View commentView = null;

        //初始化一楼
        commentView = getView(comments.get(0), 0, 1, 4, false);
        addView(commentView);

        //初始化二楼
        commentView = getView(comments.get(1), 1, 2, 4, false);
        addView(commentView);

        //初始化隐藏楼层标识
        commentView = getView(null, 2, 3, 4, true);
        commentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllViews();
                initViewWithAll(comments);
            }
        });
        addView(commentView);

        //初始化最后一楼
        commentView = getView(comments.get(comments.size() - 1), 3, comments.size(), 4, false);
        addView(commentView);
    }

    /**
     * 获取单个评论子视图
     *
     * @param comment    评论对象
     * @param index      第几个评论
     * @param showNumber 评论显示的楼层数
     * @param count      总共有几个评论
     * @param isHide     是否是隐藏显示
     * @return 一个评论子视图
     */
    private View getView(Comment comment, int index, int showNumber, int count, boolean isHide) {
        //获取根布局
        View commentView = LayoutInflater.from(context).inflate(R.layout.view_post_comment, null);

        //获取控件
        TextView tvUserName = (TextView) commentView.findViewById(R.id.view_post_comment_username_tv);
        TextView tvContent = (TextView) commentView.findViewById(R.id.view_post_comment_content_tv);
        TextView tvNum = (TextView) commentView.findViewById(R.id.view_post_comment_num_tv);
        TextView tvAddress = (TextView) commentView.findViewById(R.id.view_post_comment_username_address);
        TextView tvPhone = (TextView) commentView.findViewById(R.id.view_post_comment_username_phone);
        TextView tvHide = (TextView) commentView.findViewById(R.id.view_post_comment_hide_tv);

        /**
         *判断是否是隐藏楼层
         */
        if (isHide) {
            /**
             *是则显示“点击显示隐藏楼层”控件而隐藏其他的不相干控件
             */
            tvUserName.setVisibility(GONE);
            tvContent.setVisibility(GONE);
            tvAddress.setVisibility(GONE);
            tvNum.setVisibility(GONE);
            tvPhone.setVisibility(GONE);
            tvHide.setVisibility(VISIBLE);
        } else {
            /**
             *否则隐藏“点击显示隐藏楼层”控件而显示其他的不相干控件
             */
            tvUserName.setVisibility(VISIBLE);
            tvContent.setVisibility(VISIBLE);
            tvAddress.setVisibility(VISIBLE);
            tvNum.setVisibility(VISIBLE);
            tvPhone.setVisibility(VISIBLE);
            tvHide.setVisibility(GONE);

            //获取用户对象
            User user = comment.getUser();

            //设置显示数据
            tvUserName.setText(user.getUserName());
            tvContent.setText(comment.getContent());
            tvAddress.setText(user.getAddress());
            tvPhone.setText(user.getPhone());
            tvNum.setText(String.valueOf(showNumber));
        }

        //设置布局参数
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        //计算margin指数，这个指数的意义在于将第一个的margin值设置为最大的，然后依次递减体现层叠效果
        int marginIndex = count - index;
        int margin = marginIndex * ViewUtil.dip2px(3);

        if (index == 0) {
            params.setMargins(margin, margin, margin, 0);
        } else {
            params.setMargins(margin, 0, margin, 0);
        }

        commentView.setLayoutParams(params);

        return commentView;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        /**
         *在FloorView绘制子控件前先绘制层叠的背景图片
         */
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            drawable.setBounds(view.getLeft(), view.getLeft(), view.getRight(), view.getBottom());
            drawable.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }
}
