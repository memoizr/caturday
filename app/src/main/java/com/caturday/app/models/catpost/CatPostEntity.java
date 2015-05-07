package com.caturday.app.models.catpost;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.caturday.app.models.user.UserEntity;

import lombok.Data;

@Data
public class CatPostEntity {
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("reshare_count") private String resharesCount;
    @SerializedName("download_count") private String downloadCount;
    @SerializedName("server_id") private String serverId;
    @SerializedName("user_id") private String userId;
    @SerializedName("vote_count") private int votesCount;
//    @SerializedName("created_at") private int createdAt;

    private String imagePath;
    private JsonArray comments;
    private String commentsJSON;
    private String caption;
    private String category;
    private UserEntity user;
}
