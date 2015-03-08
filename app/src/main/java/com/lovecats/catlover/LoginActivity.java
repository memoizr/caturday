package com.lovecats.catlover;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lovecats.catlover.data.AuthModel;
import com.lovecats.catlover.data.UserModel;
import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import greendao.Auth;
import greendao.User;

public class LoginActivity extends ActionBarActivity {
    @InjectView(R.id.username_TV) EditText username_TV;
    @InjectView(R.id.password_TV) EditText password_TV;
    @InjectView(R.id.login_submit_B) Button login_submit_B;
    @InjectView(R.id.login_reveal_V) View login_reveal;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.glide_container_V) LinearLayout glide_container;

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

        loginUrl = rootUrl + "/api/v1/user_data.json";

        login_submit_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        login();

        int count = glide_container.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = glide_container.getChildAt(i);
            view.setTranslationY(400);
            view.setAlpha(0f);
            view.animate()
                    .translationYBy(-400)
                    .alpha(1f)
                    .setDuration(400)
                    .setInterpolator(new HyperTanDecelerateInterpolator())
                    .setStartDelay(i * 150 + 500)
                    .start();
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        login_reveal.postDelayed(new Runnable() {
            @Override
            public void run() {
                reveal();
            }
        }, 32);
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

                    if (entity != null) {
                        InputStream is = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ( ( line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        requested = builder.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(requested);
                            String authToken = jsonObject.get("authentication_token").toString();
//                            long userId = Long.parseLong(jsonObject.get("_id").toString());
                            Auth auth = new Auth((long) 0, authToken);
                            AuthModel.insertOrUpdate(getApplicationContext(), auth);
                            login();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }
            }
        });
        thread.start();
    }

//    private Animator anim;

    public void reveal() {
        // previously invisible view

        // get the center for the clipping circle
        int cx = login_reveal.getRight() - 48;
        int cy = login_reveal.getTop() + 96;

        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(login_reveal.getWidth(),2) + Math.pow(login_reveal.getHeight(), 2));

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(login_reveal, cx, cy, 0, finalRadius);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator());

        // make the view visible and start the animation
        login_reveal.setVisibility(View.VISIBLE);
        anim.start();
    }

    public void hide() {
        int cx = login_reveal.getRight() - 48;
        int cy = login_reveal.getTop() + 96;

        // get the final radius for the clipping circle
        int finalRadius = (int) Math.sqrt(Math.pow(login_reveal.getWidth(),2) + Math.pow(login_reveal.getHeight(), 2));

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(login_reveal, cx, cy, finalRadius, 0);
        anim.setDuration(300);
        anim.setInterpolator(new DecelerateInterpolator());

        // make the view visible and start the animation
        // login_reveal.setVisibility(View.INVISIBLE);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                login_reveal.setVisibility(View.INVISIBLE);
                backPressed();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.start();
    }

    public void login() {


        final String requestUrl = loginUrl
                + "?api_key=" + AuthModel.getAllAuths(this).get(0).getToken()
                + "&&user_id=" + "54f1e3a077d5164e63000001";
        System.out.println(AuthModel.getAllAuths(this).get(0).getToken());

        final HttpClient httpclient = new DefaultHttpClient();
        final HttpGet httpget = new HttpGet(requestUrl);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder builder = new StringBuilder();
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
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
                            String id = jsonObject.get("_id").toString();
                            String username = jsonObject.get("username").toString();
                            User user = new User();
                            user.setId((long) 0);
                            user.setUsername(username);

                            UserModel.insertOrUpdate(getApplicationContext(), user);
                            UserModel.logInUser(getApplicationContext(), user.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    @Override
    public void onBackPressed() {
        hide();
    }

    private void backPressed() {
        super.onBackPressed();
    }
}
