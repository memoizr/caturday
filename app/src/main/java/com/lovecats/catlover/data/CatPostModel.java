package com.lovecats.catlover.data;


import com.google.gson.JsonArray;

import java.util.List;

import greendao.CatPost;
import greendao.CatPostDao;
import greendao.User;
import lombok.Getter;
import lombok.Setter;

public class CatPostModel {
    @Getter @Setter private String commentsJSON;
    @Getter @Setter private String image_url;
    @Getter @Setter private String user;
    @Getter @Setter private String caption;
    @Getter @Setter private String id;
    @Getter @Setter private String category;
    @Getter @Setter private String reshares_count;
    @Getter @Setter private String positive_votes_count;
    @Getter @Setter private String negative_votes_count;
    @Getter @Setter private int total_votes_count;
    @Getter @Setter private JsonArray comments;

    public static List<CatPost> getAllCatPosts() {
        return getCatPostDao().loadAll();
    }

    private static CatPostDao getCatPostDao() {
        return DaoManager.getDaoSession().getCatPostDao();
    }

    public static CatPost getCatPostForServerId(String id) {
        return getCatPostDao().queryBuilder().where(CatPostDao.Properties.ServerId.eq(id)).unique();
    }

    public static List<CatPost> getPostsForCategory(String category) {
        return getCatPostDao().queryBuilder().where(CatPostDao.Properties.Category.eq(category)).list();
    }

    public static List<CatPost> getCatPostsForIds(List<String> catPostServerIds) {
        return getCatPostDao().queryBuilder().where(CatPostDao.Properties.ServerId.in(catPostServerIds)).list();
    }

    public static CatPost getRandomCatPost() {
        return getCatPostDao().load((long) Math.ceil(getCount() * Math.random()));
    }

    public static long getCount() {
        return getCatPostDao().count();
    }

    public static void createPost(CatPostModel catPostModel) {
        CatPost catPost = new CatPost();
        catPost.setCaption(catPostModel.caption);
        catPost.setServerId(catPostModel.id);
        catPost.setCategory(catPostModel.category);
        catPost.setComments(catPostModel.comments.toString());
        catPost.setImage_url(catPostModel.image_url);
        catPost.setTotalVotesCount(catPostModel.total_votes_count);

        insertOrUpdate(catPost);
    }

    public static CatPost insertOrUpdate(CatPost catPost) {
        long id = getCatPostDao().insertOrReplace(catPost);
        return getCatPostDao().loadByRowId(id);
    }
}
