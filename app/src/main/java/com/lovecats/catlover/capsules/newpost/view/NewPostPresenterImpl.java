package com.lovecats.catlover.capsules.newpost.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lovecats.catlover.capsules.newpost.interactor.NewPostInteractor;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewPostPresenterImpl implements NewPostPresenter {
    private static final int SELECT_PHOTO = 100;

    private final NewPostInteractor newPostInteractor;
    private final NewPostView newPostView;
    private Context mContext;

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
    public void onActivityResult(Intent data) {

        Observable<String> uriObservable = Observable.<String>create((observer)-> {
            String path = getRealPathFromURI(data.getData());
            observer.onNext(path);
            observer.onCompleted();
        });

        uriObservable.subscribeOn(Schedulers.io())
                .flatMap((path) -> newPostInteractor.createPost(path))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> System.out.println(success.getImageUrl()),
                        error -> error.printStackTrace() );
    }

    public String getRealPathFromURI(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
