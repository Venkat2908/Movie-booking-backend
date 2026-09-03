package com.venkat.bookmyshowapplication.auth.model;

import com.venkat.bookmyshowapplication.User.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TokenResponse {
    private User user_details;
private String accesstoken;
private String RefreshToken;
private String accessType;
private long expirydate;

    public TokenResponse(String accesstoken, String refreshToken, String accessType, long expirydate) {
        this.accesstoken = accesstoken;
        RefreshToken = refreshToken;
        this.accessType = accessType;
        this.expirydate = expirydate;
    }
}
