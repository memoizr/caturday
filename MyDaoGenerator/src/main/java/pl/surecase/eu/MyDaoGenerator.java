package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(4, "greendao");

        Entity catImage = schema.addEntity("CatImage");
        catImage.addIdProperty().autoincrement();
        catImage.addStringProperty("url");
        catImage.addBooleanProperty("favorite");

        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("username");
        user.addStringProperty("firstName");
        user.addStringProperty("lastName");
        user.addStringProperty("description");
        user.addBooleanProperty("loggedIn");

        Entity auth = schema.addEntity("Auth");
        auth.addIdProperty();
        auth.addStringProperty("token");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}