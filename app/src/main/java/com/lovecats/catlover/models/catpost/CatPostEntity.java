package com.lovecats.catlover.models.catpost;


import android.net.Uri;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import retrofit.mime.TypedFile;

@Data
public class CatPostEntity {
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("reshare_count") private String resharesCount;
    @SerializedName("download_count") private String downloadCount;
    @SerializedName("id") private String serverId;
    @SerializedName("votes_count") private int votesCount;

    private String imagePath;
    private JsonArray comments;
    private String commentsJSON;
    private String caption;
    private String category;
    private String user;
}
