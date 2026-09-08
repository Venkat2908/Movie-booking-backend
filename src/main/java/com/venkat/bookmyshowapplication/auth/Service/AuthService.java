package com.venkat.bookmyshowapplication.auth.Service;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import org.springframework.http.HttpStatus;

public interface AuthService {

   TokenResponse authenticate(String email, String Password) throws InvalidCredentialsException;

   TokenResponse refreshTokens(String refreshtoken);

   HttpStatus logout(String refreshToken);
}
