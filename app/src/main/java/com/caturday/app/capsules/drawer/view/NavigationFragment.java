package com.caturday.app.capsules.drawer.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;
import com.caturday.app.capsules.drawer.NavigationModule;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationFragment extends BaseFragment implements NavigationPresenter.NavigationView, AdapterView.OnItemClickListener{

    @Inject NavigationPresenter navigationPresenter;
    @InjectView(R.id.navigation_LV) ListView navigationListView;
    @InjectView(R.id.username_TV) TextView username_TV;
    @InjectView(R.id.email_TV) TextView email_TV;
    @InjectView(R.id.user_info) View userInfo;
    @InjectView(R.id.login_view) View loginInfo;
    @InjectView(R.id.profile_image_IV) ImageView profileImageIV;
    @InjectView(R.id.cover_image_IV) ImageView coverImageIV;
    @InjectView(R.id.profile_container_V) View profileContainerV;

    public NavigationFragment() {
    }

    @Override
    public void userLoggedIn(boolean loggedIn) {
        if (loggedIn) {
            userInfo.setVisibility(View.VISIBLE);
            loginInfo.setVisibility(View.GONE);
        } else {
            userInfo.setVisibility(View.GONE);
            loginInfo.setVisibility(View.VISIBLE);
            coverImageIV.setImageDrawable(getResources()
                    .getDrawable(R.drawable.default_user_profile_cover));
        }
    }

    @Override
    public void setUserProfileImage(String imageUrl) {
        Glide.with(this).load(imageUrl).into(profileImageIV);
    }

    @Override
    public void setUserCoverImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.default_user_profile_cover)
                .into(coverImageIV);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_navigation, container, false);

        ButterKnife.inject(this, rootView);

        navigationPresenter.onCreate();

        profileContainerV.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                navigationPresenter.onProfileClicked(getActivity(), x, y);
            }
            return true;
        });
        return rootView;
    }

    @Override
    public void initializeListView(String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.li_navigation_item, R.id.text1, values);
        navigationListView.setAdapter(adapter);
        navigationListView.setOnItemClickListener(this);
        navigationListView.postDelayed(() -> {
            int[] icons = {R.drawable.ic_whatshot_black_48dp, R.drawable.ic_favorite_black_48dp};
            for (int i = 0; i < 2; i++) {
                View view = navigationListView.getChildAt(i);
                ImageView iv = (ImageView) view.findViewById(R.id.icon);
                iv.setImageResource(icons[i]);
            }
        }, 100);
    }

    @Override
    public void setUserEmail(String userEmail) {
        email_TV.setText(userEmail);
    }

    @Override
    public void setUsername(String username) {
        username_TV.setText(username);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationListView.postDelayed(navigationPresenter::onViewCreated, 100);
    }

    @Override
    public void setSelected(int position) {
        setItemNormal(navigationListView);
        View v = navigationListView.getChildAt(position);
        TextView tv = (TextView) v.findViewById(R.id.text1);
        tv.setTextColor(getResources().getColor(R.color.accent));
        tv.setTypeface(null, Typeface.BOLD);

        ImageView iv = (ImageView) v.findViewById(R.id.icon);
        iv.setColorFilter(R.color.accent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int position , long id) {
        navigationPresenter.onNavigationInteraction(getActivity(), position);
    }

    private void setItemNormal(ListView listview) {
    for (int i=0; i < listview.getChildCount(); i++)
    {
        View v = listview.getChildAt(i);
        TextView txtview = (TextView) v.findViewById(R.id.text1);
        txtview.setTypeface(null, Typeface.NORMAL);
        txtview.setTextColor(getResources().getColor(R.color.black));

        ImageView iv = (ImageView) v.findViewById(R.id.icon);
        iv.setColorFilter(R.color.black);
    }
}
    @Override
    protected List<Object> getModules() {
        return Collections.singletonList(new NavigationModule(this));
    }

    @Override
    public void onDestroy() {
        navigationPresenter.onDestroy();
        super.onDestroy();
    }
}
