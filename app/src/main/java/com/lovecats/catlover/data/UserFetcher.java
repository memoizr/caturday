package com.lovecats.catlover.data;

import com.lovecats.catlover.common.Config;
import com.lovecats.catlover.api.UserApi;

import greendao.User;
import lombok.Getter;
import lombok.Setter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserFetcher {
    @Setter @Getter private long id;
    @Setter @Getter private String username;
    @Setter @Getter private String email;

    public static void createUser(String authToken) {

        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        UserApi api = adapter.create(UserApi.class);

        api.getUser(authToken, new Callback<UserFetcher>() {
            @Override
            public void success(UserFetcher userFetcher, Response response) {
                User user = new User();
                user.setId(userFetcher.getId());
                user.setUsername(userFetcher.getUsername());
                User newUser = UserModel.insertOrUpdate(user);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
