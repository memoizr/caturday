package com.caturday.app.capsules.common.view.views;

import android.content.Context;

import com.caturday.app.R;

public class NoNetworkEmptyView extends EmptyView {

    public NoNetworkEmptyView(Context context) {
        super(context);
    }

    @Override
    protected void setContent() {
        titleTV.setText("No Internet, no cats");
        titleTV.setTextColor(context.getResources().getColor(R.color.primary));
        subTitleTV.setText("Make sure you are connected and try again");
        imageIV.setImageResource(R.drawable.no_connection);
    }

    @Override
    protected void setAction() {
    }
}
