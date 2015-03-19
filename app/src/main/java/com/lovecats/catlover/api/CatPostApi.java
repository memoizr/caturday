package com.lovecats.catlover.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lovecats.catlover.data.CatPostModel;

import org.json.JSONArray;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

public interface CatPostApi {
    @GET("/cat_post")
    public void getPosts(Callback<List<CatPostModel>> response);
}
