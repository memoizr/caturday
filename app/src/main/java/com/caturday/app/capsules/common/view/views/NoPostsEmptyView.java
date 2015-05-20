package com.caturday.app.capsules.common.view.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.caturday.app.R;
import com.caturday.app.capsules.newpost.view.NewPostActivity;

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
        container.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewPostActivity.class);
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_LEFT, container.getLeft());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_TOP, container.getTop());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_WIDTH, container.getWidth());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_HEIGHT, container.getHeight());
            intent.putExtra(NewPostActivity.EXTRA_ORIGIN_RADIUS, (int) container.getRadius());
            ((Activity) context).startActivityForResult(intent, NewPostActivity.NEW_POST_REQUEST_CODE);
        });
    }
}
