package com.caturday.app.capsules.common.view.views;

import android.content.Context;

import com.caturday.app.R;

public class NoNetworkEmptyView extends EmptyView {

    public NoNetworkEmptyView(Context context) {
        super(context);
    }

    @Override
    protected void setContent() {
        titleTV.setText(R.string.connection_error_title);
        titleTV.setTextColor(context.getResources().getColor(R.color.primary));
        subTitleTV.setText(R.string.connection_error_info);
        imageIV.setImageResource(R.drawable.no_connection);
    }

    @Override
    protected void setAction() {
    }
}
