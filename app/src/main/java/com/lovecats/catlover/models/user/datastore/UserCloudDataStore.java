package com.lovecats.catlover.models.user.datastore;

import android.util.Base64;

import com.lovecats.catlover.models.user.LoginEntity;
import com.lovecats.catlover.models.user.UserEntity;
import com.lovecats.catlover.models.user.api.UserApi;

import java.io.UnsupportedEncodingException;

import rx.Observable;

public class UserCloudDataStore implements UserDataStore {

    private final UserApi userApi;

    public UserCloudDataStore(UserApi userApi) {
        this.userApi = userApi;

    }

    public Observable<UserEntity> login(String email, String password) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setEmail(email);
        try {
            loginEntity.setPassword(Base64.encodeToString(password.getBytes("UTF8"), Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userApi.login(loginEntity);
    }

    public Observable<UserEntity> signup(String username, String email, String password) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUsername(username);
        loginEntity.setEmail(email);
        try {
            loginEntity.setPassword(Base64.encodeToString(password.getBytes("UTF8"), Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userApi.signup(loginEntity);
    }

    @Override
    public Observable<UserEntity> updateUser(UserEntity userEntity) {
        return userApi.updateUser(userEntity);
    }

    @Override
    public Observable<UserEntity> getUserForId(String serverId) {
        return userApi.getUserForId(serverId);
    }
}
