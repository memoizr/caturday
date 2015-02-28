package com.lovecats.catlover;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 27/02/15.
 */
public class LoginActivity extends Activity {
    @InjectView(R.id.username_TV)
    EditText username_TV;
    @InjectView(R.id.password_TV)
    EditText password_TV;
    @InjectView(R.id.confirm_password_TV)
    EditText confirm_password_TV;
    @InjectView(R.id.login_submit_B)
    Button login_submit_B;

    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        url = prefs.getString("example_text", null);
        url = url + "/user";


        login_submit_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });


    }

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
                    nameValuePairs.add(new BasicNameValuePair("id", "12345"));
                    nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
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
