package com.lovecats.catlover.ui.detail.presenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public interface CatDetailPresenter extends Toolbar.OnMenuItemClickListener {

    void create(Bundle extras);

    void sendComment(String comment);

    void downloadImage();

    void favoritePost();
}
