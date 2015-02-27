package com.lovecats.catlover.data;

import android.app.Activity;
import android.content.Context;

import com.lovecats.catlover.helpers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import greendao.CatImage;

public class CatFetcher {
    static Context mContext;
    static FetcherCallback mFetcher;
    private static final String REQUEST_URL = "http://thecatapi.com/api/images/get?format=xml&type=jpg&results_per_page=60";

    public interface FetcherCallback {
        public void onFetchComplete(List<CatImage> catImages);
    }

    public CatFetcher(Context context, FetcherCallback fetcher) {
        mContext = context;
        mFetcher = fetcher;
    }

    private void fetchImages(List<String> urls) {
        final List<String> mUrls = urls;

        Thread thread = new Thread() {
            @Override
            public void run(){
                for (String image : mUrls) {
                    CatImage catImage = new CatImage();
                    catImage.setUrl(image);
                    CatModel.insertOrUpdate(mContext, catImage);

                }

                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFetcher.onFetchComplete(CatModel.getLastFourtyImages(mContext));
                        ((FetcherCallback)mContext).onFetchComplete(CatModel.getLastFourtyImages(mContext));
                    }
                });

            }

        };
        thread.start();
    }

    public void getCatImageUrls() {
        final List<String> urls = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String response;
                    response = XMLParser.getIt(REQUEST_URL);
                    Document doc = XMLParser.getDomElement(response);
                    NodeList nl = doc.getElementsByTagName("url");

                    for (int i = 0; i < nl.getLength(); i++) {
                        Element e = (Element) nl.item(i);
                        urls.add(e.getTextContent());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Finished fetching");
                            fetchImages(urls);
                        }
                    });
                }
            }
        };

        thread.start();
    }
}
