package com.lovecats.catlover.capsules.detail.view;

import com.lovecats.catlover.capsules.detail.adapter.CommentsAdapter;
import com.lovecats.catlover.models.comment.CommentEntity;

import java.util.List;

public interface CatDetailView {

    void initRecyclerView();

    void initImageView(String imageUrl);

    void initIMEListener();

    void initToolbar();

    void setRecyclerViewAdapter(List<CommentEntity> commentEntities);

    void initButton();

    void initCaptionHeader();

    void initCompat21();

    void hideKeyboard();

    CommentsAdapter getCommentsAdapter();
}
