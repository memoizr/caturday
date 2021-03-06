package com.caturday.app.capsules.profile.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserInfoFragment extends BaseFragment implements UserInfoView {

    @Inject UserInfoPresenter userInfoPresenter;
    @InjectView(R.id.username_TV) TextView username_TV;

    public UserInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        ButterKnife.inject(this, view);

        userInfoPresenter.createView(getActivity());

        return view;
    }

    @Override
    public void onDestroyView() {
        userInfoPresenter.destroyView();
        super.onDestroyView();
    }

    @Override
    protected List<Object> getModules() {
        return Collections.singletonList(new UserInfoModule(this));
    }

    @Override
    public void setEmail(String email) {
    }

    @Override
    public void setUsername(String username) {
        username_TV.setText(username);
    }

    @Override
    public void setDescription(String description) {
        // TODO edit description
    }

    @Override
    public void showCoverImageSettings(boolean isVisible) {
        // TODO edit cover image settings
    }

    @Override
    public void showProfileImageSettings(boolean isVisible) {
        // TODO edit profile image settings
    }
}
