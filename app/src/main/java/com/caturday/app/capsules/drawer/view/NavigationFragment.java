package com.caturday.app.capsules.drawer.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.caturday.app.R;
import com.caturday.app.capsules.common.view.mvp.BaseFragment;
import com.caturday.app.capsules.drawer.NavigationModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NavigationFragment extends BaseFragment implements NavigationPresenter.NavigationView, AdapterView.OnItemClickListener{

    @Inject NavigationPresenter navigationPresenter;
    @InjectView(R.id.navigation_LV) ListView navigationListView;
    @InjectView(R.id.username_TV) TextView username_TV;
    @InjectView(R.id.email_TV) TextView email_TV;
    @InjectView(R.id.user_info) View userInfo;
    @InjectView(R.id.login_view) View loginInfo;

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
        }

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

        return rootView;
    }

    @Override
    public void initializeListView(String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.li_navigation_item, R.id.text1, values);
        navigationListView.setAdapter(adapter);
        navigationListView.setOnItemClickListener(this);
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

        navigationListView.postDelayed(() ->{
        navigationPresenter.onViewCreated();
        }, 100);
    }

    @Override
    public void setSelected(int position) {
        setItemNormal(navigationListView);
        View v = navigationListView.getChildAt(position);
        TextView tv = (TextView) v.findViewById(R.id.text1);
        tv.setTextColor(getResources().getColor(R.color.accent));
        tv.setTypeface(null, Typeface.BOLD);
    }

    @OnClick(R.id.profile_container_V)
    public void clickProfile() {
        navigationPresenter.onProfileClicked(getActivity());
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
    }
}
    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new NavigationModule(this));
    }

    @Override
    public void onDestroy() {
        navigationPresenter.onDestroy();
        super.onDestroy();
    }
}
