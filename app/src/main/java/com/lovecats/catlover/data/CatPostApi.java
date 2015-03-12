package com.lovecats.catlover.data;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

public interface CatPostApi {
    @GET("/cat_post.json")
    public void getPosts(Callback<List<CatPostModel>> response);
}
