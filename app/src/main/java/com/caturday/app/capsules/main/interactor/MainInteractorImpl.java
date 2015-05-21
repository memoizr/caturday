package com.caturday.app.capsules.main.interactor;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.repository.CatPostRepository;
import com.caturday.app.models.gcm.GcmRegistrationEntity;
import com.caturday.app.models.gcm.repository.GcmRepository;
import com.caturday.app.models.user.UserEntity;
import com.caturday.app.models.user.repository.UserRepository;

import java.util.Collection;

import rx.Observable;

public class MainInteractorImpl implements  MainInteractor {

    private final UserRepository userRepository;
    private final CatPostRepository catPostRepository;
    private final GcmRepository gcmRepository;

    public MainInteractorImpl(
            UserRepository userRepository,
            CatPostRepository catPostRepository,
            GcmRepository gcmRepository) {

        this.userRepository = userRepository;
        this.catPostRepository = catPostRepository;
        this.gcmRepository = gcmRepository;
    }

    @Override
    public boolean userLoggedIn() {
        return userRepository.userLoggedIn();
    }

    @Override
    public void performLogout() {
        userRepository.logout();
    }

    @Override
    public UserEntity getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return catPostRepository.getRandomCatPosts(howMany);
    }

    @Override
    public Observable<GcmRegistrationEntity> registerDevice(String regId) {
        return gcmRepository.registerDevice(regId);
    }

}
