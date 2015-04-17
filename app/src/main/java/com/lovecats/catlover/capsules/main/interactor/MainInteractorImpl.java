package com.lovecats.catlover.capsules.main.interactor;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.repository.CatPostRepository;
import com.lovecats.catlover.models.user.repository.UserRepository;

import java.util.Collection;

public class MainInteractorImpl implements  MainInteractor {

    private final UserRepository userRepository;
    private final CatPostRepository catPostRepository;

    public MainInteractorImpl(
            UserRepository userRepository,
            CatPostRepository catPostRepository) {

        this.userRepository = userRepository;
        this.catPostRepository = catPostRepository;
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
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        return catPostRepository.getRandomCatPosts(howMany);
    }

}
