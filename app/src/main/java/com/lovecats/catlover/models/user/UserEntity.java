package com.lovecats.catlover.models.user;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserEntity {
    private String id;
    private String email;
    private String username;
    private String info;
    private JsonArray favorites;
    private String firstName;
    private String lastName;
    private String description;
    private Boolean loggedIn;

    @SerializedName("authentication_token") private String authToken;
    @SerializedName("server_id") private String serverId;
    @SerializedName("image_url") private String imageUrl;
}
