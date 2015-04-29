package com.lovecats.catlover.capsules.profile.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.view.mvp.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserInfoFragment extends BaseFragment implements UserInfoView {

    @Inject UserInfoPresenter userInfoPresenter;
    @InjectView(R.id.username_TV) TextView username_TV;
    @InjectView(R.id.email_TV) TextView email_TV;
    @InjectView(R.id.tagline_TV) TextView tagline_TV;

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

        userInfoPresenter.createView();

        return view;
    }

    @Override
    public void onDestroyView() {
        userInfoPresenter.destroyView();
        super.onDestroyView();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new UserInfoModule(this));
    }

    @Override
    public void setEmail(String email) {
        email_TV.setText(email);
    }

    @Override
    public void setUsername(String username) {
        username_TV.setText(username);
    }

    @Override
    public void setDescription(String description) {
        tagline_TV.setText(description);
    }
}
