package com.caturday.app.capsules.profile.info;

public interface UserInfoView {

    void setEmail(String email);

    void setUsername(String username);

    void setDescription(String description);

    void showCoverImageSettings(boolean b);

    void showProfileImageSettings(boolean b);
}
