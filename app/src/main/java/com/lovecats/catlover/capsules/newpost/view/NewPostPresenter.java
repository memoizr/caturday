package com.lovecats.catlover.capsules.newpost.view;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

public interface NewPostPresenter {

    void onCreate(Context context);

    void chooseImage();

    void takeNewImage();

    void onActivityResult(Intent data);

    void sendPost(EditText caption, EditText link);
}

