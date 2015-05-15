package com.caturday.app.util.helper;


import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class NetworkError {
    private String message;
    private JsonObject messages;
}
