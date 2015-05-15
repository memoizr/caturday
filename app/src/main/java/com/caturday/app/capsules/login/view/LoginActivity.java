package com.caturday.app.capsules.login.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseActionBarActivity;
import com.caturday.app.capsules.login.LoginModule;
import com.caturday.app.util.helper.AnimationHelper;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
    @InjectView(R.id.error_TV) TextView errorTV;

    @Inject LoginPresenter loginPresenter;

    public final static String RIPPLE_ORIGIN_X = "RIPPLE_ORIGIN_X";
    public final static String RIPPLE_ORIGIN_Y = "RIPPLE_ORIGIN_Y";
    private int rippleOriginX;
    private int rippleOriginY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        setUpToolbar();

        setActivityToFullscreen(true);
        new Handler().postDelayed(() -> {
            setActivityToFullscreen(false);
        }, 1000);

        rippleOriginX = getIntent().getExtras().getInt(RIPPLE_ORIGIN_X);
        rippleOriginY = getIntent().getExtras().getInt(RIPPLE_ORIGIN_Y);

        AnimationHelper.glideUp(glide_container);
        showKeyboard();
    }

    private void setActivityToFullscreen(boolean isFullscreen) {
        if (isFullscreen) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            login_reveal.setPadding(
                    0,
                    getResources().getDimensionPixelSize(R.dimen.status_bar_size),
                    0,
                    0);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            login_reveal.setPadding(0, 0, 0, 0);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary));
        }
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

    @OnClick(R.id.signup_B)
    public void clickSignUp() {
        hideKeyboard();

        loginPresenter.performSignup(
                username_TV.getText().toString(),
                email_TV.getText().toString(),
                password_TV.getText().toString());

        AnimationHelper.glideAwayAndHide(glide_container);
        progress_bar.postDelayed(() -> AnimationHelper.glideInAndShow(progress_bar), 400);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        login_reveal.postDelayed(() -> circularReveal(), 32);
    }

    public void circularReveal() {
        int cx = rippleOriginX;
        int cy = rippleOriginY;

        AnimationHelper.circularReveal(login_reveal, cx, cy, null);
    }

    public void circularHide() {
        int cx = rippleOriginX;
        int cy = rippleOriginY;

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

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_larger_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
        setActivityToFullscreen(true);
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
    public void toggleError(boolean showError, String errorMessage) {
        if (showError) {
            errorTV.setVisibility(View.VISIBLE);
            errorTV.setText(errorMessage);
        } else {
            errorTV.setVisibility(View.GONE);
        }
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
