package com.lovecats.catlover.ui.stream.api;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface CatPostApi {
    @GET("/cat_post")
    public void getPosts(Callback<List<CatPostEntity>> response);
}
