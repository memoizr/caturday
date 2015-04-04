package com.lovecats.catlover.ui.stream.data.mapper;

import com.lovecats.catlover.common.data.GsonMapper;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;

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
        catPost.setComments(catPostEntity.getComments().toString());
        catPost.setImage_url(catPostEntity.getImageUrl());
        catPost.setTotalVotesCount(catPostEntity.getTotalVotesCount());
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
        catPostEntity.setComments(GsonMapper.toJsonArray(catPost.getComments()));
        catPostEntity.setImageUrl(catPost.getImage_url());
        catPostEntity.setTotalVotesCount(catPostEntity.getTotalVotesCount());
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
