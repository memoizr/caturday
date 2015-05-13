package com.caturday.app.capsules.newpost.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.EditText;

import com.caturday.app.capsules.common.events.OnPostCreatedEvent;
import com.caturday.app.capsules.newpost.interactor.NewPostInteractor;
import com.caturday.app.models.catpost.CatPostEntity;
import com.squareup.otto.Bus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewPostPresenterImpl implements NewPostPresenter {

    private static final int SELECT_PHOTO = 100;
    private static final int REQUEST_TAKE_PHOTO = 1;

    private final NewPostInteractor newPostInteractor;
    private final NewPostView newPostView;
    private final Bus bus;
    private Context mContext;
    private Uri imageUri;

    public NewPostPresenterImpl(NewPostView newPostView,
                                NewPostInteractor newPostInteractor,
                                Bus bus) {
        this.newPostView = newPostView;
        this.newPostInteractor = newPostInteractor;
        this.bus = bus;
    }

    @Override
    public void onCreate(Context context) {
        this.mContext = context;
        newPostView.initToolbar();
        newPostView.animateIn();
        bus.register(this);
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, writeToFile());
        ((Activity)mContext).startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

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

    private void sendPostForUri(CatPostEntity catPostEntity, Uri uri) {

        Observable<CatPostEntity> uriObservable = Observable.create((observer)-> {
            String path = "";
            if (uri != null) {
                path = getRealPathFromURI(uri);
            }
            catPostEntity.setImagePath(path);
            observer.onNext(catPostEntity);
            observer.onCompleted();
        });

        uriObservable.subscribeOn(Schedulers.io())
                .flatMap(newPostInteractor::createPost)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onSendingSuccess,
                        this::onSendingFailure);
    }

    private void onSendingSuccess(CatPostEntity catPostEntity) {
        newPostView.success(catPostEntity);
    }

    private void onSendingFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    private Uri writeToFile() {

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

    private String getRealPathFromURI(Uri uri) {

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
