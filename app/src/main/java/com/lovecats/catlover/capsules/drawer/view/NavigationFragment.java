package com.lovecats.catlover.capsules.drawer.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lovecats.catlover.R;
import com.lovecats.catlover.capsules.common.BaseFragment;
import com.lovecats.catlover.capsules.drawer.NavigationModule;
import com.lovecats.catlover.capsules.drawer.NavigationPresenter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by user on 01/03/15.
 */
public class NavigationFragment extends BaseFragment implements NavigationView, AdapterView.OnItemClickListener{

    @Inject NavigationPresenter navigationPresenter;
    @InjectView(R.id.navigation_LV) ListView navigationListView;
    @InjectView(R.id.username_TV) TextView username_TV;
    @InjectView(R.id.email_TV) TextView email_TV;


    public NavigationFragment() {
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

    @OnClick(R.id.profile_container_V)
    public void clickProfile() {
        navigationPresenter.onProfileClicked(getActivity());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int position , long id) {
        navigationPresenter.onNavigationInteraction(getActivity(), position);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.asList(new NavigationModule(this));
    }
}
