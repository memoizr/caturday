package com.caturday.app;

import android.app.Application;

import com.caturday.app.capsules.common.Config;
import com.caturday.app.models.DaoManager;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class App extends Application {

    private ObjectGraph objectGraph;
    @Inject Config config;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoManager.DaoLoader(this);

        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }
}

