package com.caturday.app.capsules.dashboard.interactor;

import com.caturday.app.models.user.repository.UserRepository;

public class DashboardInteractorImpl implements DashboardInteractor {

    private final UserRepository userRepository;

    public DashboardInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isUserLoggedIn() {
        return userRepository.userLoggedIn();
    }
}
