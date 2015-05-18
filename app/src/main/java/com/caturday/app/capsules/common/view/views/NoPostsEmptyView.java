package com.caturday.app.capsules.common.view.views;

import android.content.Context;

import com.caturday.app.R;

public class NoPostsEmptyView extends EmptyView {

    public NoPostsEmptyView(Context context) {
        super(context);
    }

    @Override
    protected void setContent() {
        titleTV.setText("Nothing to show");
        titleTV.setTextColor(context.getResources().getColor(R.color.primary));
        subTitleTV.setText("Create your first post");
        imageIV.setImageResource(R.drawable.no_posts);
    }

    @Override
    protected void setAction() {

    }
}
