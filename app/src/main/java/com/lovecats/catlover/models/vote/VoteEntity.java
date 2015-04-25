package com.lovecats.catlover.models.vote;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VoteEntity {

    public static final String VOTEABLE_TYPE_CAT_POST = "CatPost";

    @SerializedName("user_id") private String userId;
    @SerializedName("voteable_type") private String voteableType;
    @SerializedName("voteable_id") private String voteableId;
    private Boolean positive;
}
