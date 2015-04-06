package com.lovecats.catlover.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class GsonConverter {

    public static <T> List<T> fromJsonArrayToTypeArray(JsonArray jsonArray, Class<T> toClass){

        Gson gson = new GsonBuilder().create();
        List<T> typeArray = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            T foo = gson.fromJson(element, toClass);
            typeArray.add(foo);
        }
        
        return typeArray;
    }
}
