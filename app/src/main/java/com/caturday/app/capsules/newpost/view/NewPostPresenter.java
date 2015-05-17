package com.caturday.app.capsules.newpost.view;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Spinner;

public interface NewPostPresenter {

    void onCreate(Context context);

    void chooseImage();

    void takeNewImage();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void sendPost(EditText caption, EditText link, Spinner spinner);
}

