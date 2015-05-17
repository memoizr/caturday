package com.caturday.app.capsules.newpost.view;

import android.net.Uri;

import com.caturday.app.models.catpost.CatPostEntity;

public interface NewPostView {

    void initToolbar();

    void animateIn();

    void choiceMade();

    void choiceUnmade();

    void setPreview(Uri uri);

    void setPreview(String url);

    void onSendPostSuccess(CatPostEntity catPostEntity);

    void onSendPostProcessing();

    void onSendPostFailure();
}
