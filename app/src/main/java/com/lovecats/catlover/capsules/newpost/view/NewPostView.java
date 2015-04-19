package com.lovecats.catlover.capsules.newpost.view;

import android.net.Uri;

public interface NewPostView {
    void initToolbar();
    void animateIn();

    void choiceMade();

    void choiceUnmade();

    void setPreview(Uri uri);
    void setPreview(String url);
}
