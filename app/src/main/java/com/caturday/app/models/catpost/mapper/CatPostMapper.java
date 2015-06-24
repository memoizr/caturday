package com.caturday.app.models.catpost.mapper;

import com.caturday.app.models.user.UserEntity;
import com.caturday.app.util.data.GsonConverter;
import com.caturday.app.util.data.GsonMapper;
import com.caturday.app.models.catpost.CatPostEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import greendao.CatPost;

public class CatPostMapper {

    public static CatPost fromEntity(CatPostEntity catPostEntity) {

        CatPost catPost = new CatPost();
        catPost.setCaption(catPostEntity.getCaption());
        catPost.setServerId(catPostEntity.getServerId());
        catPost.setCategory(catPostEntity.getCategory());
        catPost.setCreatedAt(catPostEntity.getCreatedAt());
        catPost.setTotalVotesCount(catPostEntity.getVotesCount());
        catPost.setUser(GsonConverter.fromEntityToJsonString(catPostEntity.getUser()));
        catPost.setUserId(GsonConverter.fromEntityToJsonString(catPostEntity.getUserId()));
        catPost.setComments(catPostEntity.getComments().toString());
        catPost.setImageUrl(catPostEntity.getImageUrl());
        catPost.setTotalVotesCount(catPostEntity.getVotesCount());
        return catPost;
    }

    public static Collection<CatPost> fromEntity(Collection<CatPostEntity> catPostEntityCollection) {

        Collection<CatPost> catPostCollection;
        if (catPostEntityCollection != null && !catPostEntityCollection.isEmpty()) {
            catPostCollection = new ArrayList<>();
            for (CatPostEntity catPostEntity : catPostEntityCollection) {
                catPostCollection.add(fromEntity(catPostEntity));
            }
        } else {
            catPostCollection = Collections.emptyList();
        }

        return catPostCollection;
    }

    public static CatPostEntity toEntity(CatPost catPost) {

        CatPostEntity catPostEntity = new CatPostEntity();
        catPostEntity.setCaption(catPost.getCaption());
        catPostEntity.setServerId(catPost.getServerId());
        catPostEntity.setCategory(catPost.getCategory());
        catPostEntity.setCreatedAt(catPost.getCreatedAt());
        catPostEntity.setVotesCount(catPost.getTotalVotesCount());
        catPostEntity.setUser(GsonConverter.fromJsonStringToEntity(catPost.getUser(), UserEntity.class));
        catPostEntity.setUserId(catPost.getUserId());
        catPostEntity.setComments(GsonMapper.toJsonArray(catPost.getComments()));
        catPostEntity.setImageUrl(catPost.getImageUrl());
        catPostEntity.setVotesCount(catPostEntity.getVotesCount());
        return catPostEntity;
    }

    public static Collection<CatPostEntity> toEntity(Collection<CatPost> catPostCollection) {

        Collection<CatPostEntity> catPostEntityCollection;
        if (catPostCollection != null && !catPostCollection.isEmpty()) {
            catPostEntityCollection = new ArrayList<>();
            for (CatPost catPost : catPostCollection) {
                catPostEntityCollection.add(toEntity(catPost));
            }
        } else {
            catPostEntityCollection = Collections.emptyList();
        }
        return catPostEntityCollection;
    }
}
