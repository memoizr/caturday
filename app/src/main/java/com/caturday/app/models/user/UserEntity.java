package com.caturday.app.models.user;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserEntity {
    private String id;
    private String email;
    private String username;
    private String info;
    private String firstName;
    private String lastName;
    private String description;
    private Boolean loggedIn;
    private JsonArray favorites;
    private JsonArray following;
    private JsonArray followers;

    @SerializedName("authentication_token") private String authToken;
    @SerializedName("server_id") private String serverId;
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("cover_image_url") private String coverImageUrl;
}
