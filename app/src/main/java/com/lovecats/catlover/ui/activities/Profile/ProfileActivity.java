package com.lovecats.catlover.ui.activities.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lovecats.catlover.R;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.ui.activities.BaseActionBarActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ProfileActivity extends BaseActionBarActivity {
    @InjectView(R.id.logout) Button logout;
    @Inject UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        toggleButton();
    }

    public boolean isUserLoggedIn(){
        return userModel.userLoggedIn();
    }

    @Override
    public List<Object> getModules() {
        return Arrays.<Object>asList(new ProfileModule(this));
    }

    private void toggleButton() {
        System.out.println(isUserLoggedIn());
        if (userModel.userLoggedIn()) {
            logout.setVisibility(View.VISIBLE);
        } else {
            logout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.logout)
    public void logout() {
        userModel.flushUsers();
        toggleButton();
    }
}
