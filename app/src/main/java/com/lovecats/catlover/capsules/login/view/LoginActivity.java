package com.lovecats.catlover.capsules.login.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseActionBarActivity;
import com.lovecats.catlover.capsules.login.LoginModule;
import com.lovecats.catlover.data.LoginHandler;
import com.lovecats.catlover.util.helper.AnimationHelper;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActionBarActivity implements LoginView {
    @InjectView(R.id.username_TV) EditText username_TV;
    @InjectView(R.id.email_TV) EditText email_TV;
    @InjectView(R.id.password_TV) EditText password_TV;
    @InjectView(R.id.login_reveal_V) View login_reveal;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.glide_container_V) LinearLayout glide_container;
    @InjectView(R.id.signup_buttons_V) View signup_buttons;
    @InjectView(R.id.login_buttons_V) View login_buttons;
    @InjectView(R.id.title_TV) TextView title_TV;
    @InjectView(R.id.progress_bar) ProgressBar progress_bar;
    @InjectView(R.id.done_V) View done;
    @InjectView(R.id.reveal_done_V) View reveal_done;

    @Inject LoginPresenter loginPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        setUpToolbar();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        AnimationHelper.glideUp(glide_container);
        showKeyboard();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new LoginModule(this));
    }

    private void showKeyboard() {
        glide_container.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }, 700);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.login_submit_B)
    public void login() {
        hideKeyboard();
        loginPresenter.performLogin(
                email_TV.getText().toString(),
                password_TV.getText().toString());

        AnimationHelper.glideAwayAndHide(glide_container);
        progress_bar.postDelayed(() -> AnimationHelper.glideInAndShow(progress_bar), 400);
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        login_reveal.postDelayed(() -> circularReveal(), 32);
    }

    public void circularReveal() {
        int cx = login_reveal.getRight() - 48;
        int cy = login_reveal.getTop() + 96;

        AnimationHelper.circularReveal(login_reveal, cx, cy, null);
    }

    public void circularHide() {
        int cx = login_reveal.getRight() - 48;
        int cy = login_reveal.getTop() + 96;

        Animator.AnimatorListener listener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                login_reveal.setVisibility(View.INVISIBLE);
                backPressed();
            }

            @Override public void onAnimationStart(Animator animation) { }
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        };

        AnimationHelper.circularHide(login_reveal, cx, cy, listener);
    }

    private void setUpToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_larger_24dp);
    }

    @Override
    public void onBackPressed() {
        AnimationHelper.glideDown(glide_container);
        circularHide();
    }

    private void backPressed() {
        super.onBackPressed();
    }

    @Override
    public void successAnimation() {
        progress_bar.postDelayed(() -> AnimationHelper.zoomOut(progress_bar), 600);
        progress_bar.postDelayed(() -> AnimationHelper.zoomIn(done), 1000);
        progress_bar.postDelayed(() ->
                AnimationHelper.circularReveal(
                        reveal_done, done.getLeft() + done.getWidth() / 2,
                        done.getTop() + done.getWidth() / 2, null), 1400);
        progress_bar.postDelayed(() -> backPressed(), 2200);
    }

    @Override
    public void failureAnimation() {
        progress_bar.postDelayed(() -> AnimationHelper.glideOutAndHide(progress_bar), 500);
        progress_bar.postDelayed(() -> AnimationHelper.glideInAndShow(glide_container), 900);
        progress_bar.postDelayed(() -> {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
            password_TV.startAnimation(animation);
            email_TV.startAnimation(animation);

        }, 1150);
    }
}
