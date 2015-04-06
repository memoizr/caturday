package com.lovecats.catlover.data;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.api.LoginApi;
import com.lovecats.catlover.data.user.UserModel;

import greendao.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginHandler {

    public static void performLogin(String password, String email, final Callback callback) {

        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        LoginApi api = adapter.create(LoginApi.class);

        LoginWrapper loginWrapper = new LoginWrapper();
        LoginParams loginParams = new LoginParams();
        loginParams.setPassword(password);
        loginParams.setEmail(email);
        loginWrapper.setUser(loginParams);

        api.getToken(loginWrapper, new Callback<AuthModel>() {
            @Override
            public void success(AuthModel authModel, Response response) {
                User user = new User();
                user.setServerId(authModel.getId());
                user.setUsername(authModel.getUsername());
                user.setEmail(authModel.getEmail());
                user.setInfo(authModel.getInfo());
                user.setImage_url(authModel.getImage_url());
                user.setAuthToken(authModel.getAuthToken());
                user.setLoggedIn(true);
                User newUser = UserModel.insertOrUpdate(user);
                Config.setAuthToken(authModel.getAuthToken());
                callback.success(newUser, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(error);
                error.printStackTrace();
            }
        });
    }
}
