package com.lovecats.catlover.capsules.profile.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lovecats.catlover.capsules.profile.following.view.FollowingFragment;
import com.lovecats.catlover.capsules.profile.info.UserInfoFragment;

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
        switch (position) {
            case 0:
                return new UserInfoFragment();

            case 1: {
                Fragment fragment = new FollowingFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(FollowingFragment.EXTRA_FOLLOWING_TYPE,
                        ProfileSections.SECTION_FOLLOWING.ordinal());
                fragment.setArguments(bundle);

                return fragment;
            }

            case 2: {
                Fragment fragment = new FollowingFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(FollowingFragment.EXTRA_FOLLOWING_TYPE,
                        ProfileSections.SECTION_FOLLOWERS.ordinal());
                fragment.setArguments(bundle);

                return fragment;
            }

            default:
                return new UserInfoFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ProfileSections.getProfileSection(position);
    }
}
