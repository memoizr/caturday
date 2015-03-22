package com.lovecats.catlover.data;

import com.lovecats.catlover.Config;

import retrofit.RestAdapter;

/**
 * Created by user on 21/03/15.
 */
public abstract class BaseRequest {



    protected static RestAdapter getRestAdapter() {
        String endpoint = Config.getEndpoint();

        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();
    }
}
