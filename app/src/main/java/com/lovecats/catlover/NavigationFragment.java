package com.lovecats.catlover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by user on 01/03/15.
 */
public class NavigationFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView navigationListView;
    private OnFragmentInteractionListener mListener;
    @InjectView(R.id.profile_container_V) View profileContainer;

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

        navigationListView = (ListView) rootView.findViewById(R.id.navigation_LV);
        final String[] values = new String[] { "What's Hot"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.li_navigation_item, R.id.text1, values);
        navigationListView.setAdapter(adapter);
        navigationListView.setOnItemClickListener(this);

        return rootView;
    }

    @OnClick(R.id.profile_container_V)
    public void clickProfile() {
        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
        getActivity().startActivity(profileIntent);
    }


    public void onItemClick(AdapterView<?> adapterView, View v, int position , long id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int position);
    }}
