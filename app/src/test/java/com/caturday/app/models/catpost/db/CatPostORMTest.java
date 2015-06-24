package com.caturday.app.models.catpost.db;

import com.caturday.app.models.catpost.CatPostEntity;
import com.caturday.app.models.comment.CommentEntity;
import com.caturday.app.models.user.UserEntity;
import com.google.gson.JsonArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.Collection;
import java.util.Collections;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import greendao.CatPost;
import greendao.CatPostDao;
import greendao.DaoSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class CatPostORMTest {

    private CatPostORM catPostORM;
    @Mock
    CatPostDao catPostDao;
    @Mock
    DaoSession daoSession;
    @Mock
    QueryBuilder queryBuilder;

    CatPost catPost;
    CatPostEntity catPostEntity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(daoSession.getCatPostDao()).thenReturn(catPostDao);
        when(catPostDao.queryBuilder()).thenReturn(queryBuilder);
        when(queryBuilder.where(any(WhereCondition.class))).thenReturn(queryBuilder);
        catPost = new CatPost();
        catPost.setServerId("4");
        catPost.setComments(new JsonArray().toString());
        catPost.setTotalVotesCount(4);
        catPost.setCaption("");
        catPost.setCreatedAt("");
        catPost.setImageUrl("");
        catPost.setUser("");
        catPost.setUserId("");
        catPost.setDownloadCount(2);
        catPost.setId(2L);

        catPostEntity = new CatPostEntity();
        catPostEntity.setServerId("4");
        catPostEntity.setComments(new JsonArray());
        catPostEntity.setVotesCount(4);
        catPostEntity.setCaption("");
        catPostEntity.setCreatedAt("");
        catPostEntity.setImageUrl("");
        catPostEntity.setUser(new UserEntity());
        catPostEntity.setUserId("");
        catPostEntity.setDownloadCount("3");

        catPostORM = new CatPostORM(daoSession);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCatPostForServerId() throws Exception {
        CatPostEntity entity = new CatPostEntity();
        entity.setServerId("4");

        when(queryBuilder.unique()).thenReturn(catPost);

        catPostORM.getCatPostForServerId("4")
            .subscribe(r -> assertEquals(r.getServerId(), "4"));
    }

    @Test
    public void testGetPostsForPageAndCategory() throws Exception {

    }

    @Test
    public void testGetPostsForPageAndUserId() throws Exception {

    }

    @Test
    public void testGetCatPostsForServerIds() throws Exception {

    }

    @Test
    public void testGetRandomCatPost() throws Exception {

    }

    @Test
    public void testGetRandomCatPosts() throws Exception {

    }

    @Test
    public void testGetCount() throws Exception {

    }

    @Test
    public void testCreatePost() throws Exception {

    }

    @Test
    public void testCreateMultiplePost() throws Exception {

    }

    @Test
    public void testEraseCache() throws Exception {

    }

    @Test
    public void testUpdateCatPost() throws Exception {
        when(queryBuilder.uniqueOrThrow()).thenReturn(catPost);
        catPostORM.updateCatPost(catPostEntity);
        verify(catPostDao, times(1)).update(any(CatPost.class));
    }

    @Test
    public void testDeletePost() throws Exception {

    }

    @Test
    public void testInsertOrUpdate() throws Exception {

    }

    @Test
    public void testInsertOrUpdateInTx() throws Exception {
        Collection<CatPost> coll = Collections.singletonList(catPost);
        catPostORM.insertOrUpdateInTx(coll);
        verify(catPostDao, times(1)).insertOrReplaceInTx(coll);
    }
}