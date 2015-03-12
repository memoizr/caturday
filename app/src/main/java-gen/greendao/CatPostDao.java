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
        public final static Property UserJson = new Property(4, String.class, "userJson", false, "USER_JSON");
        public final static Property CommentsJson = new Property(5, String.class, "commentsJson", false, "COMMENTS_JSON");
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
                "'USER_JSON' TEXT," + // 4: userJson
                "'COMMENTS_JSON' TEXT);"); // 5: commentsJson
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
 
        String userJson = entity.getUserJson();
        if (userJson != null) {
            stmt.bindString(5, userJson);
        }
 
        String commentsJson = entity.getCommentsJson();
        if (commentsJson != null) {
            stmt.bindString(6, commentsJson);
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
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // userJson
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // commentsJson
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
        entity.setUserJson(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCommentsJson(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
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
