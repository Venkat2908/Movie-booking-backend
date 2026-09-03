package com.venkat.bookmyshowapplication.Auth.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
private String accesstoken;
private String RefreshToken;
private String accessType;
private long expirydate;
}
