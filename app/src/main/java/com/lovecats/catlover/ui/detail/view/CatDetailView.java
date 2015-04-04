package com.lovecats.catlover.ui.detail.view;

import com.lovecats.catlover.ui.detail.data.CommentEntity;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;

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
}
