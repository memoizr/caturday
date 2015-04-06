package com.lovecats.catlover.models.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserEntity {
    private String id;
    private String email;
    private String username;
    private String authToken;
    private String info;
    private String favorites;
    private String firstName;
    private String lastName;
    private String description;
    private Boolean loggedIn;

    @SerializedName("server_id")private String serverId;
    @SerializedName("image_url") private String imageUrl;
}
