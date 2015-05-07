package com.caturday.app.util.helper;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUploadHelper {

    public static void resizeImage(Context context, Uri imageUri) {
        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

            final int maxSize = 768;
            int outWidth;
            int outHeight;
            int inWidth = bitmap.getWidth();
            int inHeight = bitmap.getHeight();
            if(inWidth > inHeight){
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth;
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight;
            }

            bitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

            File f = new File(getRealPathFromURI(context, imageUri));
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRealPathFromURI(Context context, Uri uri) {

        String url = "";
        try {
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();
            url = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }
}
