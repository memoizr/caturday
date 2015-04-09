package com.lovecats.catlover.capsules.profile.interactor;

import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.repository.UserRepository;

/**
 * Created by user on 28/03/15.
 */
public class ProfileInteractorImpl implements ProfileInteractor{

    private UserRepository userRepository;

    public ProfileInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void logout() {
        userRepository.logout();
    }

    @Override
    public boolean userLoggedIn() {
        return userRepository.userLoggedIn();
    }

    @Override
    public UserEntity getUser() {
        return userRepository.getCurrentUser();
    }
}
