package com.lovecats.catlover.ui.activities.profile;

import com.lovecats.catlover.capsules.profile.ProfileActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 27/03/15.
 */
public class MockProfileActivity extends ProfileActivity {
    @Override
    public List<Object> getModules() {
        return Arrays.<Object>asList(new MockProfileModule(this));
    }
}

