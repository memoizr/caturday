package com.caturday.app.util.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GsonConverter {

    public static <T> List<T> fromJsonArrayToTypeArray(JsonArray jsonArray, Class<T> toClass){

        Gson gson = new GsonBuilder().create();

        List<T> typeArray = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            T entity = gson.fromJson(element, toClass);
            typeArray.add(entity);
        }
        
        return typeArray;
    }

    public static <T> T fromJsonObjectToEntity(JsonObject jsonObject, Class<T> toClass){

        Gson gson = new GsonBuilder().create();

        T entity = gson.fromJson(jsonObject, toClass);

        return entity;
    }

    public static <T> T fromJsonStringToEntity(String jsonObject, Class<T> toClass){

        Gson gson = new GsonBuilder().create();

        T entity = gson.fromJson(jsonObject, toClass);

        return entity;
    }

    public static <T> String fromEntityToJsonString(T entity) {

        Gson gson = new GsonBuilder().create();

        return gson.toJson(entity).toString();
    }
}
