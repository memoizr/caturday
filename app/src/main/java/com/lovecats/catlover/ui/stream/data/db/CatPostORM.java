package com.lovecats.catlover.ui.stream.data.db;

import com.lovecats.catlover.common.Config;
import com.lovecats.catlover.data.DaoManager;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;
import com.lovecats.catlover.ui.stream.data.mapper.CatPostMapper;

import java.util.ArrayList;
import java.util.Collection;

import greendao.CatPost;
import greendao.CatPostDao;

public class CatPostORM implements CatPostDb {

    private static CatPostDao getCatPostDao() {
        return DaoManager.getDaoSession().getCatPostDao();
    }

    @Override
    public CatPost getCatPostForServerId(String id) {
        return getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.eq(id))
                .unique();
    }

    @Override
    public Collection<CatPost> getPostsForPageAndCategory(int page, String category) {
        int limit = Config.PAGINATION_LIMIT;
        int offset = page * limit;
        return getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.Category.eq(category))
                .offset(offset)
                .limit(25)
                .list();
    }

    @Override
    public Collection<CatPost> getCatPostsForServerIds(Collection<String> catPostServerIds) {
        return getCatPostDao()
                .queryBuilder()
                .where(CatPostDao.Properties.ServerId.in(catPostServerIds))
                .list();
    }

    @Override
    public CatPost getRandomCatPost() {
        return getCatPostDao().load((long) Math.ceil(getCount() * Math.random()));
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

        catPostCollection = CatPostMapper.transform(catPostEntities);

        insertOrUpdateInTx(catPostCollection);
    }

    private CatPost mapToCatPost(CatPostEntity catPostEntity){

        CatPost catPost = CatPostMapper.transform(catPostEntity);

        return catPost;
    }

    public CatPost insertOrUpdate(CatPost catPost) {
        long id = getCatPostDao().insertOrReplace(catPost);
        return getCatPostDao().loadByRowId(id);
    }

    public static void insertOrUpdateInTx(Collection<CatPost> catPostCollection) {
        getCatPostDao().insertOrReplaceInTx(catPostCollection);
    }
}
