package com.caturday.app.capsules.profile.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.caturday.app.capsules.common.events.UserAvailableEvent;
import com.caturday.app.capsules.common.events.navigation.OnActivityResultEvent;
import com.caturday.app.models.user.UserEntity;
import com.caturday.app.util.helper.ImageUploadHelper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class UserInfoPresenterImpl implements UserInfoPresenter{

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_TAKE_PHOTO = 1;private final UserInfoView userInfoView;

    private final Bus bus;
    private boolean isCurrentUser;
    private Context context;
    private Uri imageUrl;

    public UserInfoPresenterImpl(UserInfoView userInfoView, Bus bus) {
        this.userInfoView = userInfoView;
        this.bus = bus;
    }

    @Override
    public void createView(Context context) {
        bus.register(this);
        this.context = context;
    }

    @Subscribe
    public void onActivityResultEvent(OnActivityResultEvent event) {
        Intent data = event.getData();
        if (data != null) {
            this.imageUrl = data.getData();
            ImageUploadHelper.resizeImage(context, imageUrl);
        }
    }


    @Override
    public void destroyView() {
        bus.unregister(this);
    }

    @Override
    public void onUploadImagePressed() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        ((Activity)context).startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void onTakePhotoPressed() {

    }

    @Override
    public void onLinkImagePressed() {

    }

    @Subscribe
    public void userAvailable(UserAvailableEvent userLoadedEvent) {
        UserEntity userEntity = userLoadedEvent.getUserEntity();
        isCurrentUser = userLoadedEvent.isCurrentUser();
        if (isCurrentUser) {
            initCurrentUser(userEntity);
        } else {
            initOtherUser();
        }
        userInfoView.setUsername(userEntity.getUsername());
        userInfoView.setDescription(userEntity.getDescription());
    }

    private void initCurrentUser(UserEntity userEntity) {
        userInfoView.showProfileImageSettings(true);
        userInfoView.showCoverImageSettings(true);
        userInfoView.setEmail(userEntity.getEmail());
    }

    private void initOtherUser() {
        userInfoView.showProfileImageSettings(false);
        userInfoView.showCoverImageSettings(false);

    }
}
