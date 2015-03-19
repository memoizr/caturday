package com.lovecats.catlover.data;

import android.os.AsyncTask;

import com.lovecats.catlover.Config;
import com.lovecats.catlover.api.CatPostApi;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatPostFetcher extends AsyncTask<List<CatPostModel>,Integer, String> {

    private static CatPostFetcher session;

    private List<CatPostModel> mCatPostModels;

    private CatPostFetcher(){
    }

    public static CatPostFetcher getSession() {
        if (session == null) {
            session = new CatPostFetcher();
        }
        return session;
    }



    public interface CatPostFetcherCallbacks {
        public void onSuccess(List<CatPostModel> catPostModels);
    }

    CatPostFetcherCallbacks mCallback;
    public void fetchCatPosts(final CatPostFetcherCallbacks callback) {
        mCallback = callback;
        String endpoint = Config.getEndpoint();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        CatPostApi api = adapter.create(CatPostApi.class);

        api.getPosts(new Callback<List<CatPostModel>>() {
            @Override
            public void success(final List<CatPostModel> catPostModels, Response response) {
                System.out.println("success");

                execute(catPostModels);
                mCatPostModels = catPostModels;

                System.out.println(catPostModels.get(0).getTotal_votes_count());
                System.out.println(catPostModels.get(1).getTotal_votes_count());
                System.out.println(catPostModels.get(2).getTotal_votes_count());
                System.out.println(catPostModels.get(4).getUser());
                System.out.println(catPostModels.get(4).getComments());

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }


    @Override
    protected String doInBackground(List<CatPostModel>... params) {
        System.out.println("doing in background");
        for (CatPostModel catpostmodel : params[0]) {
            catpostmodel.createPost(catpostmodel);
        }
        return "done";
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        System.out.println(values[0]);
    }

    protected void onPostExecute(String astring) {
        System.out.println(astring);
        mCallback.onSuccess(mCatPostModels);
    }
}
