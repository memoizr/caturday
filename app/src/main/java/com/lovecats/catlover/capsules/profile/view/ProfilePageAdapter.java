package com.lovecats.catlover.capsules.profile.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lovecats.catlover.capsules.profile.following.FollowingFragment;
import com.lovecats.catlover.capsules.profile.info.UserInfoFragment;

public class ProfilePageAdapter extends FragmentPagerAdapter{
    enum ProfileSections {

        SECTION_INFO("info"),
        SECTION_FOLLOWING("following");

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
                case 1:
                    return "Following";
                default:
                    return "Info";
            }
        }
    }

    private static int NUM_ITEMS = ProfileSections.values().length;

    public ProfilePageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserInfoFragment();
            case 1:
                return new FollowingFragment();
            default:
                return new UserInfoFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ProfileSections.getProfileSection(position);
    }
}
