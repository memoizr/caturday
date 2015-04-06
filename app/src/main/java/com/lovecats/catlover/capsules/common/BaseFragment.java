package com.lovecats.catlover.capsules.common;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.lovecats.catlover.App;

import java.util.List;

import dagger.ObjectGraph;

public abstract class BaseFragment extends Fragment {
    private ObjectGraph fragmentGraph;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentGraph = ((App) activity.getApplication()).createScopedGraph(getModules().toArray());
        fragmentGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentGraph = null;
    }

    protected abstract List<Object> getModules();
}
