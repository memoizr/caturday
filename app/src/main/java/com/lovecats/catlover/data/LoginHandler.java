package com.lovecats.catlover.data;

import retrofit.RestAdapter;
import retrofit.http.POST;

/**
 * Created by user on 10/03/15.
 */
public class LoginHandler {
    private static final String API_URL = "http://localhost:8080/";

//    public interface loginApi {
//        @POST "/api/v1/token.json?"
//        public Observable
//    }
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(API_URL)
        .build();
}
