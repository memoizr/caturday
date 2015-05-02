package com.caturday.app;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
public class SimpleUnitTest {
    @Test
    public void checkJUnitWork() {
        assertThat(true, is(true));
    }
}