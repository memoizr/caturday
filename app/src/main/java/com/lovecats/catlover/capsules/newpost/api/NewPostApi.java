package com.lovecats.catlover.capsules.newpost.api;

import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface NewPostApi {
    @Multipart
    @POST("/cat_post")
    Observable<String> upload(@Part("image_file") TypedFile file,
                @Part("caption") String caption);
}
