package com.lovecats.catlover.data;

import java.util.List;
import java.util.Random;

import greendao.CatPost;
import greendao.CatPostDao;
import lombok.Getter;
import lombok.Setter;

public class CatPostModel {
    @Getter @Setter private String commentsJSON;
    @Getter @Setter private String image_url;
    @Getter @Setter private String userJSON;
    @Getter @Setter private String caption;

    public static CatPost insertOrUpdate(CatPost catPost) {
        long id = getCatPostDao().insertOrReplace(catPost);
        return getCatPostDao().loadByRowId(id);
    }

    private static CatPostDao getCatPostDao() {
        return DaoManager.getDaoSession().getCatPostDao();
    }

    public static long getCount() {
        return getCatPostDao().count();
    }

    public static List<CatPost> getAllCatPosts() {
        return getCatPostDao().loadAll();
    }

    public static CatPost getCatPostForServerId(String id) {
        return getCatPostDao().queryBuilder().where(CatPostDao.Properties.ServerId.eq(id)).unique();
    }

    public static CatPost getRandomCatPost() {
        return getCatPostDao().load((long) Math.ceil(getCount() * Math.random()));
    }

    public static void createPost(CatPostModel catPostModel){
        CatPost catPost = new CatPost();
        catPost.setCaption(catPostModel.caption);
        catPost.setCommentsJson(catPostModel.commentsJSON);
        catPost.setImage_url(catPostModel.image_url);

        insertOrUpdate(catPost);
    }
}
