package com.lovecats.catlover.models.user.repository;


import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.db.UserORM;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserEntity getCurrentUser() {
        return new UserORM().getLoggedInUser();
    }
}
