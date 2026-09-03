package com.venkat.bookmyshowapplication.auth.Service;

import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;

public interface AuthService {

   TokenResponse authenticate(String email, String Password) throws LoginCredientialsmismatchException;
}
