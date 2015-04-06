package com.lovecats.catlover.capsules.dashboard.stream.api;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import java.util.List;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CatPostApi {
    @GET("/cat_post")
    public List<CatPostEntity> getPosts(@Query("page") int page,
                         @Query("category") String category);

}
