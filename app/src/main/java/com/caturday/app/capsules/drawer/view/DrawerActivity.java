package com.caturday.app.capsules.drawer.view;

import com.caturday.app.capsules.common.view.mvp.BaseActionBarActivity;

/**
 * Created by user on 28/03/15.
 */
public abstract class DrawerActivity extends BaseActionBarActivity {
    public abstract void onNavigationItemSelected(int position);
}
