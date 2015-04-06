package com.lovecats.catlover.models.catpost;


import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CatPostEntity {
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("negative_votes_count") private String negativeVotesCount;
    @SerializedName("positive_votes_count") private String positiveVotesCount;
    @SerializedName("reshares_count") private String resharesCount;
    @SerializedName("id") private String serverId;
    @SerializedName("total_votes_count") private int totalVotesCount;
    private JsonArray comments;
    private String commentsJSON;
    private String caption;
    private String category;
    private String user;
}
