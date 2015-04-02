package com.lovecats.catlover.ui.stream.data.datastore;

import android.os.AsyncTask;

import com.lovecats.catlover.common.Config;
import com.lovecats.catlover.ui.stream.api.CatPostApi;
import com.lovecats.catlover.ui.stream.data.CatPostEntity;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatStreamCloudDataStore extends AsyncTask<List<CatPostEntity>,Integer, String> {

    private static CatStreamCloudDataStore session;

    private List<CatPostEntity> mCatPostEntities;

    private CatStreamCloudDataStore(){
    }

    public static CatStreamCloudDataStore getSession() {
        if (session == null) {
            session = new CatStreamCloudDataStore();
        }
        return session;
    }



    public interface CatPostFetcherCallbacks {
        public void onSuccess(List<CatPostEntity> catPostEntities);
    }

    CatPostFetcherCallbacks mCallback;
    public void fetchCatPosts(final CatPostFetcherCallbacks callback) {
        mCallback = callback;
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        CatPostApi api = adapter.create(CatPostApi.class);

        api.getPosts(new Callback<List<CatPostEntity>>() {
            @Override
            public void success(final List<CatPostEntity> catPostEntities, Response response) {
                execute(catPostEntities);
                mCatPostEntities = catPostEntities;
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    protected String doInBackground(List<CatPostEntity>... params) {
        for (CatPostEntity catpostmodel : params[0]) {
        }
        return "done";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        System.out.println(values[0]);
    }

    protected void onPostExecute(String astring) {
        mCallback.onSuccess(mCatPostEntities);
    }
}
