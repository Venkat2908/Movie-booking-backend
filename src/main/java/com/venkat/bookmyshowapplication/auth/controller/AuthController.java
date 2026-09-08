package com.venkat.bookmyshowapplication.auth.controller;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.auth.Service.AuthService;
import com.venkat.bookmyshowapplication.auth.dto.LoginRequestDto;
import com.venkat.bookmyshowapplication.auth.dto.LoginResponseDto;
import com.venkat.bookmyshowapplication.auth.model.ResponseStatus;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginrequest(@Valid @RequestBody LoginRequestDto requestdto) throws InvalidCredentialsException {
        TokenResponse token = authService.authenticate(requestdto.getEmail(), requestdto.getPassword());
        LoginResponseDto dto = new LoginResponseDto();

        dto.setAccessToken(token.getAccessToken());
        dto.setAccessType(token.getAccessType());
        dto.setExpiry_time_in_Seconds(token.getExpirydate());
        dto.setRefreshToken (token.getRefreshToken());
        dto.setResponseStatus(ResponseStatus.LOGIN_SUCCESSFUL);
        dto.setEmail(token.getEmail());
         return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }
}
