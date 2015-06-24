package com.caturday.app.capsules.dashboard.stream.view;

import android.app.Activity;
import android.view.View;

import com.caturday.app.models.catpost.CatPostEntity;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

public abstract class CatStreamPresenter implements ObservableScrollViewCallbacks {

    abstract public void onAttach(Activity activity);

    abstract public void onViewCreated(String streamType, String userId, int position);

    abstract public void setAdapter();

    abstract public void onScrollStateChanged(int scrollState);

    abstract public void loadMore(int page, int totalItems);

    public abstract void plusOneClicked(String serverId, int position);

    public abstract void openDetails(int i, View view, CatPostEntity catPostEntity, boolean showComments);

    public abstract void onDestroyView();

    public abstract boolean isCurrentUser(String serverId);

    public abstract void deleteItem(String postId, int position);
}
