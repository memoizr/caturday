package com.lovecats.catlover.data;

import android.content.Context;

import java.util.List;

import greendao.CatImage;
import greendao.CatImageDao;

/**
 * Created by user on 23/02/15.
 */
public class CatModel {
    public static void insertOrUpdate(Context context, CatImage catImage) {
        getCatImageDao(context).insertOrReplace(catImage);

    }

    private static CatImageDao getCatImageDao(Context c) {
        return DaoManager.DaoLoader(c).getDaoSession().getCatImageDao();

    }

    public static long getCount(Context context) {
        return getCatImageDao(context).count();
    }

    public static List<CatImage> getAllCatImages(Context context) {
        return getCatImageDao(context).loadAll();
    }

    public static List<CatImage> getAllFavoriteCatImages(Context context) {
        List<CatImage> favorites = getCatImageDao(context).queryBuilder()
                .where(CatImageDao.Properties.Favorite.eq(true))
                .list();
            return favorites;
    }

    public static CatImage getCatImageForId(Context context, long id) {
        return getCatImageDao(context).load(id);
    }

    public static void likeCatImage(Context context, long id) {
        getCatImageDao(context).load(id).setFavorite(true);
    }

    public static List<CatImage> getLastFourtyImages(Context context) {
        List<CatImage> latest = getCatImageDao(context).queryBuilder()
                .limit(40)
                .orderDesc(CatImageDao.Properties.Id)
                .list();
        return latest;
    }

    public static void toggleFavoriteCatImage(Context context, long id) {
        CatImage catImage = getCatImageDao(context).load(id);
        if (!catImage.getFavorite()){
            catImage.setFavorite(true);
        } else {
            catImage.setFavorite(false);
        }
    }
}
