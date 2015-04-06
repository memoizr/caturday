package com.lovecats.catlover.models.user.datastore;

import com.lovecats.catlover.models.user.UserEntity;

/**
 * Created by Cat#2 on 05/04/15.
 */
public interface UserDataStore {

    boolean userSignedIn();

    UserEntity getCurrentUser();
}
