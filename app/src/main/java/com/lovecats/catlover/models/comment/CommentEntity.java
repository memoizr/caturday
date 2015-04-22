package com.lovecats.catlover.models.comment;

import com.google.gson.annotations.SerializedName;
import com.lovecats.catlover.models.user.UserEntity;

import lombok.Data;

@Data
public class CommentEntity {

    public static final String COMMENTABLE_TYPE_CAT_POST = "CatPost";

    @SerializedName("user_id") private String userId;
    @SerializedName("commentable_type") private String commentableType;
    @SerializedName("commentable_id") private String commentableId;
    @SerializedName("user") private UserEntity userEntity;
    private String content;
}
