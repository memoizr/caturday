package com.lovecats.catlover.capsules.newpost.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;

import com.lovecats.catlover.capsules.newpost.interactor.NewPostInteractor;
import com.lovecats.catlover.models.catpost.CatPostEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewPostPresenterImpl implements NewPostPresenter {

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_TAKE_PHOTO = 1;

    private final NewPostInteractor newPostInteractor;
    private final NewPostView newPostView;
    private Context mContext;
    private Uri imageUri;

    public NewPostPresenterImpl(NewPostView newPostView, NewPostInteractor newPostInteractor) {
        this.newPostView = newPostView;
        this.newPostInteractor = newPostInteractor;
    }

    @Override
    public void onCreate(Context context) {
        this.mContext = context;
        newPostView.initToolbar();
        newPostView.animateIn();
    }

    @Override
    public void chooseImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        ((Activity)mContext).startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    public void takeNewImage() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, doMagic());
        ((Activity)mContext).startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    @DebugLog
    @Override
    public void onActivityResult(Intent data) {
        if (data != null)
            this.imageUri = data.getData();
        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);

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

            File f = new File(getRealPathFromURI(imageUri));
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        newPostView.choiceMade();
        newPostView.setPreview(imageUri);
    }

    @Override
    public void sendPost(EditText caption, EditText link) {

        CatPostEntity catPostEntity = new CatPostEntity();
        catPostEntity.setCaption(caption.getText().toString());

        if (link.getText().length() > 0)
            catPostEntity.setImageUrl(link.getText().toString());

        catPostEntity.setCategory("space");

        sendPostForUri(catPostEntity, imageUri);
    }

    @DebugLog
    private void sendPostForUri(CatPostEntity catPostEntity, Uri uri) {

        Observable<CatPostEntity> uriObservable = Observable.<CatPostEntity>create((observer)-> {
            System.out.println(uri.toString());
            String path = getRealPathFromURI(uri);
            System.out.println(path);
            catPostEntity.setImagePath(path);
            observer.onNext(catPostEntity);
            observer.onCompleted();
        });

        uriObservable.subscribeOn(Schedulers.io())
                .flatMap(newPostInteractor::createPost)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> System.out.println(success.getImageUrl()),
                        error -> error.printStackTrace());
    }

    private Uri doMagic() {

        String timestamp = Long.toString(new Date().getTime());
        String imagePath =  "/sdcard/" + timestamp + ".jpg";

        ContentValues v = new ContentValues();

        v.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        File f = new File(imagePath);
        File parent = f.getParentFile() ;

        String path = parent.toString().toLowerCase() ;
        v.put(MediaStore.Images.ImageColumns.BUCKET_ID, path.hashCode());

        f = null;

        v.put("_data", imagePath) ;

        ContentResolver c = mContext.getContentResolver() ;

        imageUri = c.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v);

        return imageUri;
    }

    @DebugLog
    private String getRealPathFromURI(Uri uri) {
        System.out.println(uri.toString());

        String url = "";
        try {
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);

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
