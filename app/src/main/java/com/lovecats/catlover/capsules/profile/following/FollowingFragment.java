package com.lovecats.catlover.capsules.profile.following;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.profile.info.UserInfoView;

public class FollowingFragment extends Fragment implements UserInfoView {

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
        return inflater.inflate(R.layout.following_fragment, container, false);
    }
}
