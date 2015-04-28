package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(7, "greendao");

        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("serverId").index().unique();
        user.addStringProperty("username");
        user.addStringProperty("imageUrl");
        user.addStringProperty("coverImageUrl");
        user.addStringProperty("email");
        user.addStringProperty("info");
        user.addStringProperty("favorites");
        user.addStringProperty("firstName");
        user.addStringProperty("lastName");
        user.addStringProperty("description");
        user.addBooleanProperty("loggedIn");

        Entity catPost = schema.addEntity("CatPost");
        catPost.addIdProperty();
        catPost.addStringProperty("serverId").index().unique();
        catPost.addStringProperty("caption");
        catPost.addStringProperty("imageUrl");
        catPost.addStringProperty("user");
        catPost.addStringProperty("comments");
        catPost.addStringProperty("category");
        catPost.addIntProperty("downloadCount");
        catPost.addIntProperty("totalVotesCount");

        Entity session = schema.addEntity("Session");
        session.addStringProperty("authToken");

        Entity comment = schema.addEntity("Comment");
        comment.addIdProperty();
        comment.addStringProperty("content");
        comment.addStringProperty("userId");
        comment.addStringProperty("postId");

        Property idCatPost = comment.addLongProperty("idCatPost").getProperty();
        ToMany catPostComments = catPost.addToMany(comment, idCatPost);
        comment.addToOne(catPost, idCatPost);

        new DaoGenerator().generateAll(schema, args[0]);
    }
}