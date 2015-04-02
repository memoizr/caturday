package com.lovecats.catlover.ui.stream.data;


import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.lovecats.catlover.data.DaoManager;

import java.util.List;

import greendao.CatPost;
import greendao.CatPostDao;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CatPostEntity {
    @SerializedName("image_url") private String imageUrl;
    @SerializedName("negative_votes_count") private String negativeVotesCount;
    @SerializedName("positive_votes_count") private String positiveVotesCount;
    @SerializedName("reshares_count") private String resharesCount;
    @SerializedName("server_id") private String serverId;
    @SerializedName("total_votes_count") private int totalVotesCount;
    private JsonArray comments;
    private String commentsJSON;
    private String caption;
    private String category;
    private String id;
    private String user;
}
