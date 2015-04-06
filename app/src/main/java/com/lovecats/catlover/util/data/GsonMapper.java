package com.lovecats.catlover.util.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class GsonMapper {

    final ArrayList<JsonObject> list = new ArrayList<>();

    public static JsonArray toJsonArray(String input) {
        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(input);
        JsonArray array = tradeElement.getAsJsonArray();
        return array;
    }

}
