package com.venkat.bookmyshowapplication.Auth.Dto;

import com.venkat.bookmyshowapplication.Auth.Model.ResponseStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {


    private  long id;
    private String email;
    private String accessToken;
    private String refreshtoken;
    private String accessType;
    private long expiry_time_in_Seconds;
    @Enumerated(EnumType.STRING)
  private ResponseStatus responseStatus;
}
