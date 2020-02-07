package com.tunaemre.remotecontroller.listener;

import androidx.recyclerview.widget.RecyclerView;

public class ScrollListener extends RecyclerView.OnScrollListener {

    public interface ScrollingListener {
        void onScrolledHorizontal(int dx);
        void onScrolledVertical(int dy);
    }

    private ScrollingListener mScrollingListener = null;

    public ScrollListener(ScrollingListener scrollingListener) {
        this.mScrollingListener = scrollingListener;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dx != 0)
            mScrollingListener.onScrolledHorizontal(dx);

        if (dy != 0)
            mScrollingListener.onScrolledVertical(dy);
    }

}