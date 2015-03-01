package com.lovecats.catlover;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 27/02/15.
 */
public class LoginActivity extends Activity {
    @InjectView(R.id.username_TV) EditText username_TV;
    @InjectView(R.id.password_TV) EditText password_TV;
    @InjectView(R.id.login_submit_B) Button login_submit_B;

    private String rootUrl;
    private String url;
    private String loginUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        rootUrl = prefs.getString("example_text", null);
        url = rootUrl + "/api/v1/token.json";

        loginUrl = rootUrl + "/api/v1/user.json";


        login_submit_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });


    }

    String requested;
    public void postData() {
        // Create a new HttpClient and Post Header
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(url);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("email", username_TV.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("password", password_TV.getText().toString()));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    StringBuilder builder = new StringBuilder();
                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    System.out.println(entity);

                    if (entity != null) {
                        InputStream is = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ( ( line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        requested = builder.toString();
                        System.out.println(requested);

                        try {
                            JSONObject jsonObject = new JSONObject(requested);
                            System.out.println(jsonObject.get("description"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        login();
                    }

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e)

                {
                    // TODO Auto-generated catch block
                }
            }
        });
        thread.start();
    }

    public void login() {
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(url);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    StringBuilder builder = new StringBuilder();
                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        InputStream is = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ( ( line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        System.out.println(builder.toString());
                    }

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e)

                {
                    // TODO Auto-generated catch block
                }
            }
        });
        thread.start();

    }


}
