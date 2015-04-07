package com.lovecats.catlover.models.catpost.db;

import com.lovecats.catlover.capsules.common.Config;
import com.lovecats.catlover.models.catpost.CatPostEntity;
import com.lovecats.catlover.models.catpost.mapper.CatPostMapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import greendao.CatPost;
import greendao.CatPostDao;
import greendao.DaoSession;

public class CatPostORM implements CatPostDb {

    private final DaoSession daoSession;

    public CatPostORM(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    private CatPostDao getCatPostDao() {
        return daoSession.getCatPostDao();
    }

    @Override
    public CatPostEntity getCatPostForServerId(String id) {
        CatPost catPost = getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.eq(id))
                .uniqueOrThrow();
        return CatPostMapper.toEntity(catPost);
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

        System.out.println(catPostCollection.size());

        return CatPostMapper.toEntity(catPostCollection);
    }

    @Override
    public CatPostEntity getRandomCatPost() {
        CatPost catPost = getCatPostDao().load((long) Math.ceil(getCount() * Math.random()));
        return CatPostMapper.toEntity(catPost);
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

        catPostCollection = CatPostMapper.fromEntity(catPostEntities);

        insertOrUpdateInTx(catPostCollection);
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
