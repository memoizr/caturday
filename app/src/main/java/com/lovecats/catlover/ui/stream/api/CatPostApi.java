package com.lovecats.catlover.ui.stream.api;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.squareup.okhttp.Call;

import java.util.Collection;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CatPostApi {
    @GET("/cat_post")
    public List<CatPostEntity> getPosts(@Query("page") int page,
                         @Query("category") String category);

}