package com.lovecats.catlover.data;

import com.lovecats.catlover.Config;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatPostFetcher {

    public static void fetchCatPosts() {
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        CatPostApi api = adapter.create(CatPostApi.class);


        api.getPosts(new Callback<List<CatPostModel>>() {
            @Override
            public void success(List<CatPostModel> catPostModels, Response response) {
                final List<CatPostModel> mCatPostModels = catPostModels;

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (CatPostModel catPostModel : mCatPostModels) {
                            CatPostModel.createPost(catPostModel);
                        }
                    }
                });
                thread.start();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
