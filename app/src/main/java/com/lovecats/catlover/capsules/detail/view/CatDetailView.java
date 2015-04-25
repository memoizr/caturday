package com.lovecats.catlover.capsules.detail.view;

import com.lovecats.catlover.models.comment.CommentEntity;

import java.util.List;

public interface CatDetailView {

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
