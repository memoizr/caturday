package com.lovecats.catlover.capsules.profile.following.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseFragment;
import com.lovecats.catlover.capsules.profile.following.FollowingModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FollowingFragment extends BaseFragment implements FollowingView {

    @InjectView(R.id.following_RV) RecyclerView following_RV;

    @Inject FollowingPresenter followingPresenter;

    public FollowingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.following_fragment, container, false);

        ButterKnife.inject(this, rootView);

        followingPresenter.viewCreated(getActivity());

        return rootView;
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        following_RV.setLayoutManager(layoutManager);
        following_RV.setAdapter(new FollowingAdapter(45));
    }

    @Override
    public FollowingAdapter getAdapter() {
        return (FollowingAdapter) following_RV.getAdapter();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new FollowingModule(this));
    }
}
