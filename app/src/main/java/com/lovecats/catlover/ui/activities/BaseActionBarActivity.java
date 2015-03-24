package com.lovecats.catlover.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.lovecats.catlover.App;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by user on 24/03/15.
 */
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
