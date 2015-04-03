package com.lovecats.catlover.ui.stream.data.mapper;

import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import greendao.CatPost;

public class CatPostMapper {

    public static CatPost transform(CatPostEntity catPostEntity) {
        CatPost catPost = new CatPost();
        catPost.setCaption(catPostEntity.getCaption());
        catPost.setServerId(catPostEntity.getServerId());
        catPost.setCategory(catPostEntity.getCategory());
        catPost.setComments(catPostEntity.getComments().toString());
        catPost.setImage_url(catPostEntity.getImageUrl());
        catPost.setTotalVotesCount(catPostEntity.getTotalVotesCount());
        return catPost;
    }

    public static Collection<CatPost> transform(Collection<CatPostEntity> catPostEntityCollection) {
        Collection<CatPost> catPostCollection;
        if (catPostEntityCollection != null && !catPostEntityCollection.isEmpty()) {
            catPostCollection = new ArrayList<>();
            for (CatPostEntity catPostEntity : catPostEntityCollection) {
                catPostCollection.add(transform(catPostEntity));
            }
        } else {
            catPostCollection = Collections.emptyList();
        }
        return catPostCollection;
    }

}
