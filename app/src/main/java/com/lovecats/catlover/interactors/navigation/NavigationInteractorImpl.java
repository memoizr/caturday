package com.lovecats.catlover.interactors.navigation;

/**
 * Created by user on 28/03/15.
 */
public class NavigationInteractorImpl implements NavigationInteractor {

    @Override
    public String[] provideNavigationItems() {
        return new String[] {
                "New stuff",
                "Favourites",
                "Wallpapers"};
    }
}
