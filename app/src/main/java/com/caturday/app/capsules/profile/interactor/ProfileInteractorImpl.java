package com.caturday.app.capsules.profile.interactor;

import com.caturday.app.models.user.UserEntity;
import com.caturday.app.models.user.repository.UserRepository;

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

    @Override
    public Observable<UserEntity> followUser(String serverId) {
        return userRepository.followUser(serverId);
    }

    @Override
    public Observable<UserEntity> unfollowUser(String serverId) {
        return userRepository.unfollowUser(serverId);
    }
}
