package com.lovecats.catlover.ui.profile.data.repository;


import com.lovecats.catlover.ui.profile.data.UserEntity;
import com.lovecats.catlover.ui.profile.data.db.UserORM;

import greendao.UserDao;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserEntity getCurrentUser() {
        return new UserORM().getLoggedInUser();
    }
}
