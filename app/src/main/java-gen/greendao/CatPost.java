package greendao;

import java.util.List;
import greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CAT_POST.
 */
public class CatPost {

    private Long id;
    private String serverId;
    private String caption;
    private String imageUrl;
    private String user;
    private String comments;
    private String category;
    private String userId;
    private Integer downloadCount;
    private Integer totalVotesCount;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient CatPostDao myDao;

    private List<Comment> commentList;

    public CatPost() {
    }

    public CatPost(Long id) {
        this.id = id;
    }

    public CatPost(Long id, String serverId, String caption, String imageUrl, String user, String comments, String category, String userId, Integer downloadCount, Integer totalVotesCount) {
        this.id = id;
        this.serverId = serverId;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.user = user;
        this.comments = comments;
        this.category = category;
        this.userId = userId;
        this.downloadCount = downloadCount;
        this.totalVotesCount = totalVotesCount;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCatPostDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getTotalVotesCount() {
        return totalVotesCount;
    }

    public void setTotalVotesCount(Integer totalVotesCount) {
        this.totalVotesCount = totalVotesCount;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Comment> getCommentList() {
        if (commentList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentDao targetDao = daoSession.getCommentDao();
            List<Comment> commentListNew = targetDao._queryCatPost_CommentList(id);
            synchronized (this) {
                if(commentList == null) {
                    commentList = commentListNew;
                }
            }
        }
        return commentList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCommentList() {
        commentList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
