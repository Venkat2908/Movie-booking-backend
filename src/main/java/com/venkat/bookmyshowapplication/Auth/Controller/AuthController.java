package com.venkat.bookmyshowapplication.Auth.Controller;

import com.venkat.bookmyshowapplication.Auth.Service.AuthService;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import org.springframework.http.ResponseEntity;

public class AuthController {

    private AuthService authService;

    public AuthController(){

    }

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    public ResponseEntity<String> loginrequest(String email,String password) throws LoginCredientialsmismatchException {

        return authService.LoginValidation(email,password);
    }
}
