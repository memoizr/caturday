package com.caturday.app.models.catpost.api;

import com.caturday.app.models.catpost.CatPostEntity;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface CatPostApi {
    @GET("/cat_post")
    Observable<List<CatPostEntity>> getPostsForPageAndCategory(@Query("page") int page,
                                                               @Query("category") String category,
                                                               @Query("user_id") String userId);

    @GET("/cat_post/{id}")
    Observable<CatPostEntity> getPostForId(@Path("id") String serverId);

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

    @DELETE("/cat_post/{id}")
    Observable<Object> deleteCatPost(@Path("id") String postId);
}
