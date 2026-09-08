package com.venkat.bookmyshowapplication.auth.controller;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.Common.Utils.TokenResponseMapper;
import com.venkat.bookmyshowapplication.auth.Service.AuthService;
import com.venkat.bookmyshowapplication.auth.dto.LoginRequestDto;
import com.venkat.bookmyshowapplication.auth.dto.LoginResponseDto;
import com.venkat.bookmyshowapplication.auth.dto.RefreshTokenRequestDto;
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
    private  final TokenResponseMapper tokenresponsetoLoginresponseDto;

    public AuthController(AuthService authService, TokenResponseMapper tokenresponsetoLoginresponseDto){
        this.authService = authService;
        this.tokenresponsetoLoginresponseDto = tokenresponsetoLoginresponseDto;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginrequest(@Valid @RequestBody LoginRequestDto requestdto) throws InvalidCredentialsException {
        TokenResponse token = authService.authenticate(requestdto.getEmail(), requestdto.getPassword());


        return ResponseEntity.status(HttpStatus.OK).body(tokenresponsetoLoginresponseDto.convert(token));

    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){

        System.out.println(refreshTokenRequestDto.getRefreshToken());
        TokenResponse tokenResponse = authService.Refreshtokengenerator(refreshTokenRequestDto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(tokenresponsetoLoginresponseDto.convert(tokenResponse));


    }
}
