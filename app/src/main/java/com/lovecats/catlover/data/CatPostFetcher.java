package com.lovecats.catlover.data;

import com.lovecats.catlover.Config;

import org.json.JSONArray;
import org.json.JSONObject;

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

                for (CatPostModel catPostModel : catPostModels) {
                    CatPostModel.createPost(catPostModel);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        System.out.println(CatPostModel.getCount());
    }
}
