package com.lovecats.catlover.data;

import com.lovecats.catlover.data.user.UserModel;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 21/03/15.
 */
public class CommentModel {
    @Getter @Setter private String user_id;
    @Getter @Setter private String commentable_type;
    @Getter @Setter private String commentable_id;
    @Getter @Setter private String content;
    @Getter @Setter private UserModel user;

    public static final String COMMENTABLE_TYPE_CAT_POST = "CatPost";
}
