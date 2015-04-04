package com.lovecats.catlover.ui.detail.data;

import com.google.gson.annotations.SerializedName;
import com.lovecats.catlover.data.user.UserModel;

import lombok.Data;

@Data
public class CommentEntity {

    @SerializedName("user_id") private String userId;
    @SerializedName("commentable_type") private String commentableType;
    @SerializedName("commentable_id") private String commentableId;
    private String content;
    private UserModel user;

    public static final String COMMENTABLE_TYPE_CAT_POST = "CatPost";
}
