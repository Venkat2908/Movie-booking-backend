package com.venkat.bookmyshowapplication.Common.Utils;

import com.venkat.bookmyshowapplication.auth.dto.LoginResponseDto;
import com.venkat.bookmyshowapplication.auth.model.ResponseStatus;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import org.springframework.stereotype.Component;

@Component
public class TokenResponseMapper {

    public LoginResponseDto convert(TokenResponse tokenResponse){
        LoginResponseDto dto = new LoginResponseDto();
        dto.setAccessToken(tokenResponse.getAccessToken());
        dto.setAccessType(tokenResponse.getAccessType());
        dto.setExpiry_time_in_Seconds(tokenResponse.getExpirydate());
        dto.setRefreshToken (tokenResponse.getRefreshToken());
        dto.setResponseStatus(ResponseStatus.LOGIN_SUCCESSFUL);
        dto.setEmail(tokenResponse.getEmail());
        return dto;
    }
}
