package com.lovecats.catlover.models.catpost.db;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.mapper.CatPostMapper;

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
                .uniqueOrThrow();

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
    public void createPost(CatPostEntity catPostEntity) {

        CatPost catPost = mapToCatPost(catPostEntity);

        insertOrUpdate(catPost);
    }

    @Override
    public void createMultiplePost(Collection<CatPostEntity> catPostEntities) {
        Collection<CatPost> catPostCollection;
        System.out.println("multiple from orm");

        catPostCollection = CatPostMapper.fromEntity(catPostEntities);

        insertOrUpdateInTx(catPostCollection);
    }

    @Override
    public void eraseCache() {
        getCatPostDao().deleteAll();
    }

    @Override
    public Observable<CatPostEntity> updateCatPost(CatPostEntity catPostEntity) {

        CatPost catPost = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.eq(catPostEntity.getServerId()))
                .uniqueOrThrow();

        catPostEntity.setId(catPost.getId());

        getCatPostDao().update(CatPostMapper.fromEntity(catPostEntity));

        return Observable.just(catPostEntity);
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
