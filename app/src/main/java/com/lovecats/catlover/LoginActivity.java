package com.lovecats.catlover;

import android.animation.Animator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
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

public class LoginActivity extends ActionBarActivity {
    @InjectView(R.id.username_TV) EditText username_TV;
    @InjectView(R.id.password_TV) EditText password_TV;
    @InjectView(R.id.login_submit_B) Button login_submit_B;
    @InjectView(R.id.login_reveal_V) View login_reveal;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private String rootUrl;
    private String url;
    private String loginUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        setUpToolbar();

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

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        login_reveal.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                reveal();
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

    public void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = login_reveal.getRight();
        int cy = login_reveal.getTop();

        // get the final radius for the clipping circle
        int finalRadius = Math.max(login_reveal.getWidth(), login_reveal.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(login_reveal, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        login_reveal.setVisibility(View.VISIBLE);
        anim.start();
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

    private void setUpToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
