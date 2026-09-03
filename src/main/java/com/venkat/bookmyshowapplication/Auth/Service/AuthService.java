package com.venkat.bookmyshowapplication.Auth.Service;

import com.venkat.bookmyshowapplication.Auth.Model.Token;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;

public interface AuthService {

   Token LoginValidation(String email, String Password) throws LoginCredientialsmismatchException;
}
