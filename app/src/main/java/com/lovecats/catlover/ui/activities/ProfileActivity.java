package com.lovecats.catlover.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.lovecats.catlover.R;
import com.lovecats.catlover.data.UserModel;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @OnClick(R.id.logout)
    public void logout() {
        UserModel.flushUsers();
    }
}
