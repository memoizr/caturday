package com.lovecats.catlover.models.user;

import lombok.Data;

@Data
public class LoginEntity {
    private String username;
    private String email;
    private String password;
}
