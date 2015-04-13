package com.lovecats.catlover.capsules.profile.following;

import android.content.Context;

/**
 * Created by Cat#2 on 13/04/15.
 */
public class FollowingPresenterImpl implements FollowingPresenter {

    private final FollowingView followingView;

    public FollowingPresenterImpl(FollowingView followingView) {
        this.followingView = followingView;
    }

    @Override
    public void viewCreated(Context context) {
        followingView.initRecyclerView();
    }
}
