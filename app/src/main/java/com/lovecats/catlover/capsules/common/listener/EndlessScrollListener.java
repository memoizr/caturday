package com.lovecats.catlover.capsules.common.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    public abstract void onLoadMore(int page, int previousTotalItemCount);

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {

    }

    @Override
    public void onScrolled(RecyclerView recycerView, int dx, int dy) {
        super.onScrolled(recycerView, dx, dy);

        visibleItemCount = recycerView.getChildCount();
        RecyclerView.LayoutManager layoutManager = recycerView.getLayoutManager();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }
    }

}
