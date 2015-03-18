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

        Entity catImage = schema.addEntity("CatImage");
        catImage.addIdProperty().autoincrement();
        catImage.addStringProperty("url");
        catImage.addBooleanProperty("favorite");

        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("serverId").index().unique();
        user.addStringProperty("username");
        user.addShortProperty("email");
        user.addStringProperty("firstName");
        user.addStringProperty("lastName");
        user.addStringProperty("description");
        user.addBooleanProperty("loggedIn");

        Entity auth = schema.addEntity("Auth");
        auth.addIdProperty();
        auth.addStringProperty("token").unique();


        Entity comment = schema.addEntity("Comment");
        comment.addIdProperty();
        comment.addStringProperty("content");
        comment.addStringProperty("userId");
        comment.addStringProperty("postId");


        Entity catPost = schema.addEntity("CatPost");
        catPost.addIdProperty();
        catPost.addStringProperty("serverId").index().unique();
        catPost.addStringProperty("caption");
        catPost.addStringProperty("image_url");
        catPost.addStringProperty("user");
        catPost.addStringProperty("comments");
        catPost.addStringProperty("category");
        catPost.addIntProperty("downloadCount");
        catPost.addIntProperty("totalVotesCount");

//        Index commentIndex = new Index();
//        commentIndex.addProperty(catPostId);
//        commentIndex.makeUnique();
//        comment.addIndex(commentIndex);
//
//        ToMany catPostToComments = catPost.addToMany(comment, catPostId);
//        catPostToComments.setName("comments");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}