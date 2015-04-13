package com.lovecats.catlover.capsules.profile.info;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class UserInfoFragment extends BaseFragment implements UserInfoView {

    @Inject UserInfoPresenter userInfoPresenter;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userInfoPresenter.createView();

        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new UserInfoModule(this));
    }
}
