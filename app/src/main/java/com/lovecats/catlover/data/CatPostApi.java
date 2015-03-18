package com.lovecats.catlover.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

public interface CatPostApi {
    @GET("/cat_post")
    public void getPosts(Callback<List<CatPostModel>> response);
}
