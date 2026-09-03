package com.venkat.bookmyshowapplication.User.Service;

import com.venkat.bookmyshowapplication.Common.Exceptions.UserNOtfound;
import com.venkat.bookmyshowapplication.User.Model.User;

public interface  UserService {

    User RegisterUser(String name,String email,String Password);

    User AccountVerification(String email) throws UserNOtfound;
}
