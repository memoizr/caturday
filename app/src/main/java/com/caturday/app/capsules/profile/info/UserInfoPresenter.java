package com.caturday.app.capsules.profile.info;

import android.content.Context;

public interface UserInfoPresenter {

    void createView(Context context);

    void destroyView();

    void onUploadImagePressed();

    void onTakePhotoPressed();

    void onLinkImagePressed();
}
