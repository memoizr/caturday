package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER.
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ServerId = new Property(1, String.class, "serverId", false, "SERVER_ID");
        public final static Property Username = new Property(2, String.class, "username", false, "USERNAME");
        public final static Property Image_url = new Property(3, String.class, "image_url", false, "IMAGE_URL");
        public final static Property AuthToken = new Property(4, String.class, "authToken", false, "AUTH_TOKEN");
        public final static Property Email = new Property(5, String.class, "email", false, "EMAIL");
        public final static Property Info = new Property(6, String.class, "info", false, "INFO");
        public final static Property FirstName = new Property(7, String.class, "firstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(8, String.class, "lastName", false, "LAST_NAME");
        public final static Property Description = new Property(9, String.class, "description", false, "DESCRIPTION");
        public final static Property LoggedIn = new Property(10, Boolean.class, "loggedIn", false, "LOGGED_IN");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SERVER_ID' TEXT UNIQUE ," + // 1: serverId
                "'USERNAME' TEXT," + // 2: username
                "'IMAGE_URL' TEXT," + // 3: image_url
                "'AUTH_TOKEN' TEXT," + // 4: authToken
                "'EMAIL' TEXT," + // 5: email
                "'INFO' TEXT," + // 6: info
                "'FIRST_NAME' TEXT," + // 7: firstName
                "'LAST_NAME' TEXT," + // 8: lastName
                "'DESCRIPTION' TEXT," + // 9: description
                "'LOGGED_IN' INTEGER);"); // 10: loggedIn
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_USER_SERVER_ID ON USER" +
                " (SERVER_ID);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindString(2, serverId);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(3, username);
        }
 
        String image_url = entity.getImage_url();
        if (image_url != null) {
            stmt.bindString(4, image_url);
        }
 
        String authToken = entity.getAuthToken();
        if (authToken != null) {
            stmt.bindString(5, authToken);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(6, email);
        }
 
        String info = entity.getInfo();
        if (info != null) {
            stmt.bindString(7, info);
        }
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(8, firstName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(9, lastName);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(10, description);
        }
 
        Boolean loggedIn = entity.getLoggedIn();
        if (loggedIn != null) {
            stmt.bindLong(11, loggedIn ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // serverId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // username
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // image_url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // authToken
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // email
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // info
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // firstName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // lastName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // description
            cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0 // loggedIn
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setServerId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUsername(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImage_url(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAuthToken(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setEmail(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setInfo(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFirstName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLastName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDescription(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setLoggedIn(cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(User entity) {
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
