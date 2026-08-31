package com.venkat.bookmyshowapplication.Auth.Service;

import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> LoginValidation(String email, String Password) throws LoginCredientialsmismatchException;
}
