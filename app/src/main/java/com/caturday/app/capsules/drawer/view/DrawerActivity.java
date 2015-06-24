package com.caturday.app.capsules.drawer.view;

import com.caturday.app.capsules.common.view.mvp.BaseAppCompatActivity;

public abstract class DrawerActivity extends BaseAppCompatActivity {
    public abstract void onNavigationItemSelected(int position);
}
