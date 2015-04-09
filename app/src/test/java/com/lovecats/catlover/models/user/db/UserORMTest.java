package com.lovecats.catlover.models.user.db;

import android.content.Context;

import com.google.gson.Gson;
import com.lovecats.catlover.BuildConfig;
import com.lovecats.catlover.CustomRoboelectricRunner;
import com.lovecats.catlover.MockActivity;
import com.lovecats.catlover.models.user.UserEntity;

import org.junit.After;
import org.junit.Before;
import org.robolectric.Robolectric;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.HashSet;

import greendao.DaoMaster;
import greendao.DaoSession;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(CustomRoboelectricRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class UserORMTest {

    private DaoSession daoSession;
    private UserEntity user;
    private UserORM userORM;
    private DaoMaster.DevOpenHelper helper;
    private Gson gson = new Gson();

    @Before
    public void initTest() throws Exception {
        Context context = Robolectric.setupActivity(MockActivity.class);

        helper = new DaoMaster.DevOpenHelper(context, "test-db", null);
        daoSession = MockDaoManager.setupDatabase(helper);
        userORM = new UserORM(daoSession);
    }

    @Test
    public void withNoUsersReturnsFalse() {
        UserORM userORM = new UserORM(daoSession);
        boolean userLoggedin = userORM.userLoggedIn();
        assertThat(userLoggedin, equalTo(false));
    }

    @Test
    public void withUsersReturnsTrue() {
        user = new UserEntity();
        userORM.logInUser(user);

        boolean userLoggedin = userORM.userLoggedIn();
        assertThat(userLoggedin, equalTo(true));
    }

    @Test
    public void returnsTheRightLoggedInUser() {
        user = new UserEntity();
        user.setFirstName("foo");
        user.setUsername("boo");
        userORM.logInUser(user);

        UserEntity userEntity = userORM.getLoggedInUser();
        assertThat(userEntity.getUsername(), equalTo(user.getUsername()));
    }

    @Test
    public void getFavoriteCatPostsReturnsCatPosts() {
        String[] favs = {"foo", "bar"};
        HashSet<String> favorites = arrayToHashSet(favs);
        gson = new Gson();
        String favoritesJSON = gson.toJson(favorites);

        user = new UserEntity();
        user.setFavorites(favoritesJSON);
        userORM.logInUser(user);

        HashSet<String> favoriteList = userORM.getFavoriteCatPosts();

        assert(favoriteList.equals(favorites));
    }

    @Test
    public void getFavoriteCatPostsReturnsEmptyIfNoFavoritesArePresent() {

        user = new UserEntity();
        userORM.logInUser(user);

        HashSet<String> favoriteList = userORM.getFavoriteCatPosts();

        assert(favoriteList.isEmpty());
    }

    @Test
    public void addFavoritesAddsFavoriteToUser() {
        user = new UserEntity();
        userORM.logInUser(user);

        String newFavorites = "12345";

        userORM.addFavorite(newFavorites);

        HashSet<String> favoriteList = userORM.getFavoriteCatPosts();

        assert(favoriteList.contains(newFavorites));
    }

    @Test
    public void removeFavoritesRemoveFavoriteToUser() {
        String[] favs = {"foo", "bar", "12345"};
        String[] favsRemaining = {"foo", "bar"};

        HashSet<String> favorites = arrayToHashSet(favs);
        HashSet<String> favoritesRemaining = arrayToHashSet(favsRemaining);

        String doomedFavorites = "12345";

        String favoritesJSON = gson.toJson(favorites);

        user = new UserEntity();
        user.setFavorites(favoritesJSON);
        userORM.logInUser(user);

        userORM.removeFavorite(doomedFavorites);

        HashSet<String> favoriteList = userORM.getFavoriteCatPosts();

        assert(favoritesRemaining.containsAll(favoriteList));
    }

    @Test
    public void itDoesNotAddDuplicateEntries() {
        String[] favList = {"foo", "bar", "12345"};

        HashSet<String> favorites = arrayToHashSet(favList);

        String newFavorite = "12345";

        String favoritesJSON = gson.toJson(favorites);

        user = new UserEntity();
        user.setFavorites(favoritesJSON);
        userORM.logInUser(user);

        userORM.addFavorite(newFavorite);

        HashSet<String> favoriteList = userORM.getFavoriteCatPosts();

        assert(favorites.containsAll(favoriteList));
    }

    private HashSet<String> arrayToHashSet(String[] array) {

        HashSet<String> favorites = new HashSet<>();
        favorites.addAll(Arrays.asList(array));

        return favorites;
    }

    @Test
    public void itSavesTheUpdatedUserName() {
        user = new UserEntity();
        user.setUsername("oldName");
        userORM.logInUser(user);

        UserEntity oldUser = userORM.getLoggedInUser();

        userORM.updateUserName("newName");

        UserEntity newUser = userORM.getLoggedInUser();

        assertThat(oldUser.getUsername(), equalTo("oldName"));
        assertThat(newUser.getUsername(), equalTo("newName"));
    }

    @After
    public void tearDown() {
        helper.close();
    }
}
