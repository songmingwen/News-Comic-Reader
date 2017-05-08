package com.song.sunset.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.song.sunset.beans.VideoBean;
import com.song.sunset.fragments.VideoListPlayFragment;
import com.song.sunset.impls.LoadingMoreListener;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by Song on 2016/8/31 0031.
 * Email:z53520@qq.com
 */
public class LoadMoreRecyclerView extends RecyclerView {

    public interface VideoListPlayListener {
        void playVideo(int position);

        void stopVideo();

        void locate(float y);
    }

    private VideoListPlayListener videoListPlayListener;

    public void setVideoListener(VideoListPlayListener videoListener) {
        videoListPlayListener = videoListener;
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private LAYOUT_MANAGER_TYPE layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    private LoadingMoreListener loadingMoreListener;

    private boolean changed = false;

    private int currentPlayerPosition = RecyclerView.NO_POSITION;

    private float mRootTop;

    private int mCenterLine;

    public void setLoadingMoreListener(LoadingMoreListener loadingMoreListener) {
        this.loadingMoreListener = loadingMoreListener;
    }


    public LoadMoreRecyclerView(Context context) {
        super(context);
        initPosition();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPosition();

    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPosition();
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used.");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

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
            videoListPlayListener.locate(top - mRootTop);
        }
    }

    @Override
    public void onScrollStateChanged(int newState) {
        super.onScrollStateChanged(newState);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && loadingMoreListener != null) {
            loadingMoreListener.onLoadingMore();
        }
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
                    View tempView = linearManager.findViewByPosition(currentPosition);
                    if (isCenterItemView(tempView)) {
                        if (currentPlayerPosition != currentPosition) {
                            changed = true;
                        } else {
                            changed = false;
                        }
                        currentPlayerPosition = currentPosition;
                        break;
                    }
                }
            }
            if (changed) {
                View targetView = linearManager.findViewByPosition(currentPlayerPosition);
                float top = getTopPosition(targetView);
                if (top == Float.MIN_VALUE) {
                    return;
                }
                videoListPlayListener.playVideo(currentPlayerPosition);
                videoListPlayListener.locate(top - mRootTop);
            }
        }
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

    private void initPosition() {
        mCenterLine = ViewUtil.getScreenHeigth() / 2 - ViewUtil.dip2px(70);//中线位置为屏幕中心靠上70dp
        mRootTop = getTopPosition(this);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
