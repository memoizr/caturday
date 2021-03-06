package greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.User;
import greendao.CatPost;
import greendao.Session;
import greendao.Comment;

import greendao.UserDao;
import greendao.CatPostDao;
import greendao.SessionDao;
import greendao.CommentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig catPostDaoConfig;
    private final DaoConfig sessionDaoConfig;
    private final DaoConfig commentDaoConfig;

    private final UserDao userDao;
    private final CatPostDao catPostDao;
    private final SessionDao sessionDao;
    private final CommentDao commentDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        catPostDaoConfig = daoConfigMap.get(CatPostDao.class).clone();
        catPostDaoConfig.initIdentityScope(type);

        sessionDaoConfig = daoConfigMap.get(SessionDao.class).clone();
        sessionDaoConfig.initIdentityScope(type);

        commentDaoConfig = daoConfigMap.get(CommentDao.class).clone();
        commentDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        catPostDao = new CatPostDao(catPostDaoConfig, this);
        sessionDao = new SessionDao(sessionDaoConfig, this);
        commentDao = new CommentDao(commentDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(CatPost.class, catPostDao);
        registerDao(Session.class, sessionDao);
        registerDao(Comment.class, commentDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        catPostDaoConfig.getIdentityScope().clear();
        sessionDaoConfig.getIdentityScope().clear();
        commentDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CatPostDao getCatPostDao() {
        return catPostDao;
    }

    public SessionDao getSessionDao() {
        return sessionDao;
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

}
