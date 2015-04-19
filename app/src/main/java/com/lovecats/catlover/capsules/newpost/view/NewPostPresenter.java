package com.lovecats.catlover.capsules.newpost.view;

import android.content.Context;
import android.content.Intent;

public interface NewPostPresenter {

    void onCreate(Context context);

    void chooseImage();

    void onActivityResult(Intent data);
}

