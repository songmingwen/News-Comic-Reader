package com.song.sunset.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.sunset.utils.ViewUtil;
import com.song.video.SimplePlayerLayout;

/**
 * Created by Song on 2017/7/3 0003.
 * E-mail: z53520@qq.com
 */

public class VideoAutoPlayRecyclerView extends LoadMoreRecyclerView {

    private boolean changed = false;

    private int mCenterLine;

    private SimplePlayerLayout mPlayer;

    private int currentPlayerPosition = RecyclerView.NO_POSITION;

    private TextView mTextView;

    public interface VideoListPlayListener {
        void playVideo(SimplePlayerLayout player, int position);

        void stopVideo();
    }

    private VideoListPlayListener videoListPlayListener;

    public void setVideoListener(VideoListPlayListener videoListener) {
        videoListPlayListener = videoListener;
    }

    public VideoAutoPlayRecyclerView(Context context) {
        super(context);
        initData();
    }

    public VideoAutoPlayRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public VideoAutoPlayRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData();
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (currentPlayerPosition == RecyclerView.NO_POSITION) {
            return;
        }
        if (videoListPlayListener == null) return;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            View targetView = linearManager.findViewByPosition(currentPlayerPosition);
            float top = getTopPosition(targetView);
            if (top == Float.MIN_VALUE) {
                videoListPlayListener.stopVideo();
                currentPlayerPosition = RecyclerView.NO_POSITION;
                return;
            }
        }
    }

    @Override
    public void onScrollStateChanged(int newState) {
        super.onScrollStateChanged(newState);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (videoListPlayListener == null) return;
        if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            int lastPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            int firstPosition = linearManager.findFirstVisibleItemPosition();

            if (firstPosition <= lastPosition &&
                    (firstPosition != RecyclerView.NO_POSITION || lastPosition != RecyclerView.NO_POSITION)) {
                for (int currentPosition = firstPosition; currentPosition < lastPosition + 1; currentPosition++) {
                    ViewGroup tempView = (ViewGroup) linearManager.findViewByPosition(currentPosition);
                    if (isCenterItemView(tempView)) {
                        if (currentPlayerPosition != currentPosition) {
                            changed = true;
                            ViewGroup viewGroup = (ViewGroup) linearManager.findViewByPosition(currentPlayerPosition);
                            if (viewGroup != null) {
                                viewGroup.removeView(mPlayer);
                            }
                        } else {
                            changed = false;
                        }
                        currentPlayerPosition = currentPosition;
                        break;
                    }
                }
            }
            if (changed) {
                ViewGroup targetView = (ViewGroup) linearManager.findViewByPosition(currentPlayerPosition);
                float top = getTopPosition(targetView);
                if (top == Float.MIN_VALUE) {
                    return;
                }

                targetView.addView(mPlayer);

                videoListPlayListener.playVideo(mPlayer, currentPlayerPosition);
            }
        }
    }

    private int getDy(View targetView) {
        if (targetView == null) return 0;
        int top = (int) getTopPosition(targetView);
        int height = targetView.getHeight();
        int targetTop = mCenterLine - height / 2;
        return top - targetTop;
    }

    private boolean isCenterItemView(View tempView) {
        if (tempView == null) {
            return false;
        }
        float top = getTopPosition(tempView);
        float bottom = top + tempView.getHeight();
        return ((top < mCenterLine && bottom > mCenterLine) || top == mCenterLine || bottom == mCenterLine);
    }

    private float getTopPosition(View targetView) {
        if (targetView == null) {
            return Float.MIN_VALUE;
        }
        final int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        return location[1];
    }

    private void initData() {
        mCenterLine = ViewUtil.getScreenHeigth() / 2;
        mPlayer = new SimplePlayerLayout(getContext());
        mPlayer.setOnScrollListener(false);
        mPlayer.setCanChangeOrientation(false);
    }


    /**
     * 设置点击的itemiew，使其滑动到屏幕中间
     *
     * @param position
     */
    public void setClickViewToCenter(int position) {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            View targetView = layoutManager.findViewByPosition(position);
            int dy = getDy(targetView);
            this.smoothScrollBy(0, dy);
        }
    }
}
