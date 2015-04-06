package com.lovecats.catlover.models.user.repository;

import com.lovecats.catlover.models.user.UserEntity;

public interface UserRepository {

    UserEntity getCurrentUser();
}
