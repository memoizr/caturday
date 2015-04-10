package com.lovecats.catlover.capsules.drawer.interactor;

import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.repository.UserRepository;

public class NavigationInteractorImpl implements NavigationInteractor {

    private final UserRepository userRepository;

    public NavigationInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String[] provideNavigationItems() {
        return new String[] {
                "New stuff",
                "Favourites",
                "Wallpapers"};
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
