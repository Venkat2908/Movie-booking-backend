package com.venkat.bookmyshowapplication.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TokenResponse {
    private String email;
    private String accessToken;
private String refreshToken;
private String accessType;
private long expirydate;

    public TokenResponse( String email, String accessToken, String refreshToken, String accessType, long expirydate) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessType = accessType;
        this.expirydate = expirydate;
    }
}
