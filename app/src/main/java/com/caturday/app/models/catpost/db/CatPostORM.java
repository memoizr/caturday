package com.caturday.app.models.catpost.db;

import com.caturday.app.capsules.common.Config;
import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.catpost.mapper.CatPostMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import greendao.CatPost;
import greendao.CatPostDao;
import greendao.DaoSession;
import rx.Observable;

public class CatPostORM implements CatPostDb {

    private final DaoSession daoSession;

    public CatPostORM(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    private CatPostDao getCatPostDao() {
        return daoSession.getCatPostDao();
    }

    @Override
    public Observable<CatPostEntity> getCatPostForServerId(String id) {
        CatPost catPost = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.eq(id))
                .unique();

        return Observable.just(CatPostMapper.toEntity(catPost));
    }

    @Override
    public Collection<CatPostEntity> getPostsForPageAndCategory(int page, String category) {
        int limit = Config.PAGINATION_LIMIT;
        int offset = page * limit;
        Collection<CatPost> catPostCollection = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.Category.eq(category))
                .offset(offset)
                .limit(25)
                .list();

        return CatPostMapper.toEntity(catPostCollection);
    }

    @Override
    public Collection<CatPostEntity> getPostsForPageAndUserId(int page, String userId) {
        int limit = Config.PAGINATION_LIMIT;
        int offset = page * limit;
        Collection<CatPost> catPostCollection = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.UserId.eq(userId))
                .offset(offset)
                .limit(25)
                .list();

        return CatPostMapper.toEntity(catPostCollection);
    }


    @Override
    public Collection<CatPostEntity> getCatPostsForServerIds(HashSet<String> catPostServerIds) {
        Collection<CatPost> catPostCollection = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.in(catPostServerIds))
                .list();

        return CatPostMapper.toEntity(catPostCollection);
    }

    @Override
    public CatPostEntity getRandomCatPost() {
        CatPost catPost = getCatPostDao().loadAll().get((int) Math.ceil(getCount() * Math.random()));
        return CatPostMapper.toEntity(catPost);
    }

    @Override
    public Collection<CatPostEntity> getRandomCatPosts(int howMany) {
        Collection<CatPostEntity> catPostEntities = new ArrayList<>();

        List<CatPost> catPosts = getCatPostDao()
                .loadAll();

        for (int i = 0; i < howMany; i++) {
            CatPost randomPost = catPosts.get((int) Math.ceil(catPosts.size() * Math.random()));
            catPostEntities.add(CatPostMapper.toEntity(randomPost));
        }

        return catPostEntities;
    }

    @Override
    public long getCount() {
        return getCatPostDao().count();
    }

    @Override
    public CatPostEntity createPost(CatPostEntity catPostEntity) {

        CatPost catPost = mapToCatPost(catPostEntity);

        insertOrUpdate(catPost);

        return catPostEntity;
    }

    @Override
    public void createMultiplePost(Collection<CatPostEntity> catPostEntities) {
        Collection<CatPost> catPostCollection;

        catPostCollection = CatPostMapper.fromEntity(catPostEntities);

        insertOrUpdateInTx(catPostCollection);
    }

    @Override
    public void eraseCache() {
        getCatPostDao().deleteAll();
    }

    @Override
    public CatPostEntity updateCatPost(CatPostEntity catPostEntity) {
        CatPost oldPost = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.eq(catPostEntity.getServerId())).uniqueOrThrow();
        CatPost newPost = CatPostMapper.fromEntity(catPostEntity);
        newPost.setId(oldPost.getId());
        getCatPostDao().update(newPost);
        return catPostEntity;
    }

    @Override
    public void deletePost(String postId) {
        CatPost catPost = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.eq(postId)).uniqueOrThrow();

        catPost.delete();
    }

    private CatPost mapToCatPost(CatPostEntity catPostEntity){

        CatPost catPost = CatPostMapper.fromEntity(catPostEntity);

        return catPost;
    }

    public CatPost insertOrUpdate(CatPost catPost) {
        long id = getCatPostDao().insertOrReplace(catPost);
        return getCatPostDao().loadByRowId(id);
    }

    public void insertOrUpdateInTx(Collection<CatPost> catPostCollection) {
        getCatPostDao().insertOrReplaceInTx(catPostCollection);
    }
}
