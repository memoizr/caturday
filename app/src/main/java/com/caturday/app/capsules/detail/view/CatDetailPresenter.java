package com.caturday.app.capsules.detail.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.caturday.app.models.comment.CommentEntity;

import java.util.List;

public interface CatDetailPresenter extends Toolbar.OnMenuItemClickListener {

    void create(Bundle extras);

    void sendComment(String comment);

    void favoritePost();

    interface CatDetailView {

        void initRecyclerView();

        void initImageView(String imageUrl);

        void initIMEListener();

        void initToolbar();

        void setRecyclerViewAdapter(List<CommentEntity> commentEntities);

        void updateButton(boolean favorited);

        void scrollToBottom();

        void initCompat21();

        void hideKeyboard();

        void clearCommentET();

        CommentsAdapter getCommentsAdapter();
    }
}
