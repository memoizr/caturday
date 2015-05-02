package com.caturday.app.capsules.drawer.interactor;

import com.caturday.app.models.user.UserEntity;
import com.caturday.app.models.user.repository.UserRepository;

public class NavigationInteractorImpl implements NavigationInteractor {

    private final UserRepository userRepository;

    public NavigationInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String[] provideNavigationItems() {
        return new String[] { "New posts", "Favourites"};
    }

    @Override
    public boolean isUserLoggedIn() {
        return userRepository.userLoggedIn();
    }

    @Override
    public UserEntity getLoggedInUser() {
        return userRepository.getCurrentUser();
    }
}
