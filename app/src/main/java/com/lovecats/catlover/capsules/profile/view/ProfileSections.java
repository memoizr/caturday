package com.lovecats.catlover.capsules.profile.view;

public enum ProfileSections {

    SECTION_INFO("info"),
    SECTION_FOLLOWING("following"),
    SECTION_FOLLOWERS("followers");

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
            case 2:
                return "Followers";
            default:
                return "Info";
        }
    }
}
