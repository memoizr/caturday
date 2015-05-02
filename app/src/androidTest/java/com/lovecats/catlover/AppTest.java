package com.caturday.app;

import android.test.ApplicationTestCase;

public class AppTest extends ApplicationTestCase<App> {

    public AppTest() {
        super(App.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
    }

    public void testOnCreate() throws Exception {
        assertNotNull(getApplication());
    }

    public void testCreateScopedGraph() throws Exception {

    }
}
