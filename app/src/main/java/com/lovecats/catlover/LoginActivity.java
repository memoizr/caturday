package com.lovecats.catlover;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovecats.catlover.data.LoginHandler;
import com.lovecats.catlover.data.UserFetcher;
import com.lovecats.catlover.util.HyperTanAccelerateInterpolator;
import com.lovecats.catlover.util.HyperTanDecelerateInterpolator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends ActionBarActivity {
    @InjectView(R.id.username_TV) EditText username_TV;
    @InjectView(R.id.email_TV) EditText email_TV;
    @InjectView(R.id.password_TV) EditText password_TV;
    @InjectView(R.id.login_submit_B) Button login_submit_B;
    @InjectView(R.id.login_reveal_V) View login_reveal;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.glide_container_V) LinearLayout glide_container;
    @InjectView(R.id.signup_B) Button signup_B;
    @InjectView(R.id.create_account_B) Button create_account_B;
    @InjectView(R.id.existing_account_B) Button existing_account_B;
    @InjectView(R.id.signup_buttons_V) View signup_buttons;
    @InjectView(R.id.login_buttons_V) View login_buttons;
    @InjectView(R.id.title_TV) TextView title_TV;

    private String rootUrl;
    private String url;
    private String loginUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        setUpToolbar();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        glideInAnimation();
        glide_container.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }, 700);
    }

    @OnClick(R.id.login_submit_B)
    public void login() {
        LoginHandler.performLogin(password_TV.getText().toString(), email_TV.getText().toString(), new Callback() {
            @Override
            public void success(Object authString, Response response) {
                UserFetcher.createUser(authString.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @OnClick(R.id.create_account_B)
    public void showSignup() {
        title_TV.setText("Signup");
        username_TV.setVisibility(View.VISIBLE);
        login_buttons.setVisibility(View.GONE);
        signup_buttons.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.existing_account_B)
    public void showLogin() {
        username_TV.setVisibility(View.GONE);
        title_TV.setText("Login");

        signup_buttons.setVisibility(View.GONE);
        login_buttons.setVisibility(View.VISIBLE);
    }


    private void glideInAnimation() {
        int count = glide_container.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = glide_container.getChildAt(i);
            view.setTranslationY(400);
            view.setAlpha(0f);
            view.animate()
                    .translationYBy(-400)
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(new HyperTanDecelerateInterpolator())
                    .setStartDelay(i * 64 + 600)
                    .start();
        }
    }

    private void glideOutAnimation() {
        int count = glide_container.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = glide_container.getChildAt(i);
            view.animate()
                    .translationYBy(400)
                    .alpha(0f)
                    .setDuration(200)
                    .setInterpolator(new HyperTanAccelerateInterpolator())
                    .setStartDelay((count - i) * 64)
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
        anim.setDuration(400);
        anim.setInterpolator(new HyperTanAccelerateInterpolator());

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
        anim.setStartDelay(500);
        anim.setInterpolator(new HyperTanDecelerateInterpolator());

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

    private void setUpToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        glideOutAnimation();
        hide();
    }

    private void backPressed() {
        super.onBackPressed();
    }
}
