package com.lovecats.catlover.activities.profile;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.lovecats.catlover.R;
import com.lovecats.catlover.ui.activities.Profile.ProfileActivity;

import org.mockito.Mockito;

import java.util.Arrays;


public class ProfileActivityTest extends ActivityInstrumentationTestCase2{

    private ProfileActivity mActivity;
    private Button mButtonLogin;

    public ProfileActivityTest() {
        super(ProfileActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

//        mActivity = (ProfileActivity) getActivity();
        mActivity = Mockito.mock(ProfileActivity.class);
        Mockito.when(mActivity.getModules()).thenReturn(
           Arrays.<Object>asList(new MockProfileModule(mActivity.getApplicationContext()))
        );
        mButtonLogin = (Button) mActivity.findViewById(R.id.logout);
    }

    public void testClickLogout() throws Exception {
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mButtonLogin.performClick();
//            }
//        });

        assertFalse(mButtonLogin.getVisibility() == View.VISIBLE);
    }
}
