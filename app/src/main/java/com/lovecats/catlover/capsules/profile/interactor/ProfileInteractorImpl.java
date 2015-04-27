package com.lovecats.catlover.capsules.profile.interactor;

import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.repository.UserRepository;

import rx.Observable;

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
    public void updateUserName(String userName) {
//        userRepository.updateUserName(userName);
    }

    @Override
    public Observable<UserEntity> getUserForId(String serverId) {
        return userRepository.getUserForId(serverId);
    }
}
