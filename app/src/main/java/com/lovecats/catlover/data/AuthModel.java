package com.lovecats.catlover.data;

import android.content.Context;

import java.util.List;

import greendao.Auth;
import greendao.AuthDao;
import lombok.Getter;
import lombok.Setter;

public class AuthModel {
    @Setter @Getter private String username;
    @Setter @Getter private String id;
    @Setter @Getter private String email;
    @Setter @Getter private String info;
    @Setter @Getter private String authToken;
    @Setter @Getter private String loggedIn;
    @Setter @Getter private String image_url;
}
