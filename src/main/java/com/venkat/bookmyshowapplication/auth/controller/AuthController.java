package com.venkat.bookmyshowapplication.auth.controller;

import com.venkat.bookmyshowapplication.auth.dto.LoginRequestDto;
import com.venkat.bookmyshowapplication.auth.dto.LoginResponseDto;
import com.venkat.bookmyshowapplication.auth.model.ResponseStatus;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import com.venkat.bookmyshowapplication.auth.Service.AuthService;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginrequest(@Valid @RequestBody LoginRequestDto requestdto) throws LoginCredientialsmismatchException {
        TokenResponse token = authService.authenticate(requestdto.getEmail(), requestdto.getPassword());
        LoginResponseDto dto = new LoginResponseDto();

        dto.setAccessToken(token.getAccesstoken());
        dto.setAccessType(token.getAccessType());
        dto.setEmail(token.getUser_details().getEmail());
        dto.setExpiry_time_in_Seconds(token.getExpirydate());
        dto.setRefreshtoken(token.getRefreshToken());
        dto.setResponseStatus(ResponseStatus.LOGIN_SUCCESSFUL);
         return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
