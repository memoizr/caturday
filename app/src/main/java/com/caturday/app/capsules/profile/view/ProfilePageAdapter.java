package com.caturday.app.capsules.profile.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.caturday.app.capsules.profile.info.UserInfoFragment;

public class ProfilePageAdapter extends FragmentPagerAdapter{

    private static int NUM_ITEMS = ProfileSections.values().length;

    public ProfilePageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    UserInfoFragment userInfoFragment;

    private Fragment provideUserInfoFragment() {

        if (userInfoFragment == null)
            userInfoFragment = new UserInfoFragment();
        return userInfoFragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO add sections for following and followers
        switch (position) {
            case 0:
                return new UserInfoFragment();
            default:
                return new UserInfoFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ProfileSections.getProfileSection(position);
    }

    public static enum ProfileSections {

        SECTION_INFO("info");
//        SECTION_FOLLOWING("following"),
//        SECTION_FOLLOWERS("followers");

        private final String section;

        ProfileSections(final String section) {
            this.section = section;
        }

        @Override
        public String toString() {
            return section;
        }

        public static CharSequence getProfileSection(int position) {
            switch (position) {
                case 0:
                    return "Info";
//                case 1:
//                    return "Following";
//                case 2:
//                    return "Followers";
                default:
                    return "Info";
            }
        }
    }
}
