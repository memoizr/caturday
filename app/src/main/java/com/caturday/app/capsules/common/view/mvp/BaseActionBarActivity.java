package com.caturday.app.capsules.common.view.mvp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.caturday.app.App;

import java.util.List;

import dagger.ObjectGraph;

public abstract class BaseActionBarActivity extends ActionBarActivity {
    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
        activityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGraph = null;
    }

    protected abstract List<Object> getModules();
}
