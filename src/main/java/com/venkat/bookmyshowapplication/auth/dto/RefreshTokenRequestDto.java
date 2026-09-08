package com.venkat.bookmyshowapplication.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequestDto {
    private String refreshToken;
}
