package com.lovecats.catlover.ui.activities.profile;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.lovecats.catlover.BuildConfig;
import com.lovecats.catlover.CustomRoboelectricRunner;
import com.lovecats.catlover.data.user.UserModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@RunWith(CustomRoboelectricRunner.class)
@Config(emulateSdk = 21, reportSdk = 21, constants = BuildConfig.class)
public class ProfileActivityTest {
//    @Inject UserModel userModel;
//
//    @Test
//    public void displaysTheDefaultUsername() {
//        Activity activity =
//                Robolectric.setupActivity(ProfileActivity.class);
//        TextView results =
//                (TextView) activity.findViewById(R.id.user_name_TV);
//        String resultsText = results.getText().toString();
//        assertThat(resultsText, equalTo("User Name"));
//    }
//
//    @Test
//    public void setsUpTheLogoutButton() {
//
//        ProfileActivity activity = Robolectric.buildActivity(MockProfileActivity.class).create().get();
//
//        TextView logout =
//                (Button) activity.findViewById(R.id.logout);
//
//        int resultsText = logout.getVisibility();
//
//        assertThat(resultsText, equalTo(View.VISIBLE));
//    }
}