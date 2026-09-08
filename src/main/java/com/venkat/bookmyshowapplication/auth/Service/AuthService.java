package com.venkat.bookmyshowapplication.auth.Service;

import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;

public interface AuthService {

   TokenResponse authenticate(String email, String Password) throws InvalidCredentialsException;
}
