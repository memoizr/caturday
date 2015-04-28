package com.lovecats.catlover.models.session;

import lombok.Data;

@Data
public class SessionEntity {

    private String authToken;
    private String username;
    private String email;
    private String password;
}
