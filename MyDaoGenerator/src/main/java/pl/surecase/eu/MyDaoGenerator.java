package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(2, "greendao");

        Entity catImage = schema.addEntity("CatImage");
        catImage.addIdProperty().autoincrement();
        catImage.addStringProperty("url");
        catImage.addBooleanProperty("favorite");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
