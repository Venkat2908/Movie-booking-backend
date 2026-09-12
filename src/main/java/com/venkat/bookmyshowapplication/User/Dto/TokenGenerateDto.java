package com.venkat.bookmyshowapplication.User.Dto;

import com.venkat.bookmyshowapplication.User.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TokenGenerateDto {
    private User user;
    private boolean subsequent = false;
    private Instant expiresAt;
}
