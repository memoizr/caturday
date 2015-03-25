package com.lovecats.catlover.data;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import greendao.User;

public class UserModelTest extends AndroidTestCase {

    User user;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        DaoManager.DaoLoader(context);

        UserModel.flushUsers();

        user = new User();
        user.setUsername("foo");

        UserModel.insertOrUpdate(user);
    }

    public void testUserNotLoggedIn() throws Exception {
        UserModel.flushUsers();
        boolean loggedIn = UserModel.userLoggedIn();
        assertFalse(loggedIn);
    }

    public void testInsertUser() throws Exception {
        assertTrue(UserModel.userLoggedIn());
    }

    public void testGetLoggedInUser() {
        assertTrue(UserModel.getLoggedInUser().getId() == user.getId());
    }

    public void testGetFavoriteCatPosts() {
        String[] favorites = {"foo", "bar"};

        Gson gson = new Gson();
        String favoritesJSON = gson.toJson(favorites);
        user.setFavorites(favoritesJSON);

        List<String> favoriteList = UserModel.getFavoriteCatPosts();

        assertEquals(Arrays.asList(favorites), favoriteList);
    }

    public void testAddFavorite() {
        String[] favorites = {"foo", "bar"};
        String[] favorites2 = {"foo", "bar", "baz"};

        Gson gson = new Gson();
        String favoritesJSON = gson.toJson(favorites);
        user.setFavorites(favoritesJSON);
        UserModel.addFavorite("baz");

        List<String> favoriteList = UserModel.getFavoriteCatPosts();

        assertEquals(Arrays.asList(favorites2), favoriteList);
    }
    public void testRemoveFavorite() {
        String[] favorites = {"foo", "bar", "baz"};
        String[] favorites2 = {"foo", "bar"};

        Gson gson = new Gson();
        String favoritesJSON = gson.toJson(favorites);
        user.setFavorites(favoritesJSON);
        UserModel.removeFavorite("baz");

        List<String> favoriteList = UserModel.getFavoriteCatPosts();

        assertEquals(Arrays.asList(favorites2), favoriteList);
    }
}
