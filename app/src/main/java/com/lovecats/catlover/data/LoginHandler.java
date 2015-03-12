package com.lovecats.catlover.data;

import com.lovecats.catlover.Config;

import java.math.BigInteger;

import greendao.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginHandler {

    public static void performLogin(String password, String email, Callback callback) {

        final Callback mCallback = callback;

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
            public void success(AuthModel authToken, Response response) {
//                System.out.println("auth is " + authToken.getAuthToken().getToken());
                User user = new User();
                user.setServerId(authToken.getId());
                user.setUsername(authToken.getUsername());
                User newUser = UserModel.insertOrUpdate(user);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();

            }
        });
    }
}
