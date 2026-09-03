package com.venkat.bookmyshowapplication.auth.dto;

import com.venkat.bookmyshowapplication.auth.model.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {


    private String email;
    private String accessToken;
    private String refreshtoken;
    private String accessType;
    private long expiry_time_in_Seconds;
  private ResponseStatus responseStatus;
}
