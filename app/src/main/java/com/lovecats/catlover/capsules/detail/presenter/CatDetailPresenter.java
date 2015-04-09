package com.lovecats.catlover.capsules.detail.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public interface CatDetailPresenter extends Toolbar.OnMenuItemClickListener {

    void create(Context context, Bundle extras);

    void sendComment(String comment);

    void favoritePost();
}