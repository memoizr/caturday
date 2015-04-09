package com.lovecats.catlover.capsules.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ProfileActivity extends BaseActionBarActivity implements ProfileView {
    @InjectView(R.id.logout) Button logout;
    @InjectView(R.id.user_name_TV) TextView user_name_TV;
    @Inject ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        profilePresenter.onCreate();
    }

    @Override
    public List<Object> getModules() {
        return Arrays.<Object>asList(new ProfileModule(this));
    }

    @Override
    public void showButton(Boolean visible) {
        if (visible) {
            logout.setVisibility(View.VISIBLE);
        } else {
            logout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserName(String string) {
        user_name_TV.setText(string);
    }

    @OnClick(R.id.logout)
    public void clickLogout() {
        profilePresenter.logout();
    }

    @Override
    public void onPostLogout() {
        logout.setVisibility(View.GONE);
    }
}
