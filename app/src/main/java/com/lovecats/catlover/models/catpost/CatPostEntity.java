package com.lovecats.catlover.models.catpost;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.lovecats.catlover.models.user.UserEntity;

import lombok.Data;

@Data
public class CatPostEntity {
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("reshare_count") private String resharesCount;
    @SerializedName("download_count") private String downloadCount;
    @SerializedName("server_id") private String serverId;
    @SerializedName("vote_count") private int votesCount;
//    @SerializedName("created_at") private int createdAt;

    private String imagePath;
    private JsonArray comments;
    private String commentsJSON;
    private String caption;
    private String category;
    private UserEntity user;
}
