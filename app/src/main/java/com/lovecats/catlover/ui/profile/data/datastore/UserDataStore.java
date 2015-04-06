package com.lovecats.catlover.ui.profile.data.datastore;

import com.lovecats.catlover.ui.profile.data.UserEntity;

/**
 * Created by Cat#2 on 05/04/15.
 */
public interface UserDataStore {

    boolean userSignedIn();

    UserEntity getCurrentUser();
}
