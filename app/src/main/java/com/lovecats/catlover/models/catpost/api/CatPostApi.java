package com.lovecats.catlover.models.catpost.api;

import com.lovecats.catlover.models.catpost.CatPostEntity;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface CatPostApi {
    @GET("/cat_post")
    List<CatPostEntity> getPosts(@Query("page") int page,
                         @Query("category") String category);

//    @Multipart
//    @POST("/cat_post")
//    Observable<CatPostEntity> upload(@Part("image_file") TypedFile file,
//                              @Part("caption") String caption);

    @Multipart
    @POST("/cat_post")
    Observable<CatPostEntity> upload(@Part("image_file") TypedFile file,
                                     @Part("category") String category,
                                     @Part("caption") String caption);
    @POST("/cat_post")
    Observable<CatPostEntity> uploadWithUrl(@Body CatPostEntity catPostEntity);
}
