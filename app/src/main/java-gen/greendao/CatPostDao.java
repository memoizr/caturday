package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.CatPost;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CAT_POST.
*/
public class CatPostDao extends AbstractDao<CatPost, Long> {

    public static final String TABLENAME = "CAT_POST";

    /**
     * Properties of entity CatPost.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ServerId = new Property(1, String.class, "serverId", false, "SERVER_ID");
        public final static Property Caption = new Property(2, String.class, "caption", false, "CAPTION");
        public final static Property Image_url = new Property(3, String.class, "image_url", false, "IMAGE_URL");
        public final static Property User = new Property(4, String.class, "user", false, "USER");
        public final static Property Comments = new Property(5, String.class, "comments", false, "COMMENTS");
        public final static Property Category = new Property(6, String.class, "category", false, "CATEGORY");
        public final static Property DownloadCount = new Property(7, Integer.class, "downloadCount", false, "DOWNLOAD_COUNT");
        public final static Property TotalVotesCount = new Property(8, Integer.class, "totalVotesCount", false, "TOTAL_VOTES_COUNT");
    };


    public CatPostDao(DaoConfig config) {
        super(config);
    }
    
    public CatPostDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CAT_POST' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SERVER_ID' TEXT UNIQUE ," + // 1: serverId
                "'CAPTION' TEXT," + // 2: caption
                "'IMAGE_URL' TEXT," + // 3: image_url
                "'USER' TEXT," + // 4: user
                "'COMMENTS' TEXT," + // 5: comments
                "'CATEGORY' TEXT," + // 6: category
                "'DOWNLOAD_COUNT' INTEGER," + // 7: downloadCount
                "'TOTAL_VOTES_COUNT' INTEGER);"); // 8: totalVotesCount
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_CAT_POST_SERVER_ID ON CAT_POST" +
                " (SERVER_ID);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CAT_POST'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CatPost entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(2, serverId);
        }
 
        String caption = entity.getCaption();
        if (caption != null) {
            stmt.bindString(3, caption);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(4, image_url);
        }
 
        String user = entity.getUser();
        if (user != null) {
            stmt.bindString(5, user);
        }
 
        String comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(6, comments);
        }
 
        String category = entity.getCategory();
        if (category != null) {
            stmt.bindString(7, category);
        }
 
        Integer downloadCount = entity.getDownloadCount();
        if (downloadCount != null) {
            stmt.bindLong(8, downloadCount);
        }
 
        Integer totalVotesCount = entity.getTotalVotesCount();
        if (totalVotesCount != null) {
            stmt.bindLong(9, totalVotesCount);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CatPost readEntity(Cursor cursor, int offset) {
        CatPost entity = new CatPost( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // serverId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // caption
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // image_url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // user
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // comments
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // category
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // downloadCount
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8) // totalVotesCount
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CatPost entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setServerId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCaption(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImage_url(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUser(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setComments(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCategory(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDownloadCount(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setTotalVotesCount(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CatPost entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CatPost entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
