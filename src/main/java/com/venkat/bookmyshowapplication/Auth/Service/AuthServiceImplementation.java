package com.venkat.bookmyshowapplication.Auth.Service;


import com.venkat.bookmyshowapplication.Auth.Model.Token;
import com.venkat.bookmyshowapplication.Auth.Security.TokenService;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.venkat.bookmyshowapplication.Auth.Model.TokenResponse;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements  AuthService {

    private final PasswordEncoder passwordEncoder;

    private TokenService tokenService;

    private UserRepository userRepository;

    public AuthServiceImplementation(PasswordEncoder passwordEncoder, TokenService tokenService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public Token LoginValidation(String email, String Password) throws LoginCredientialsmismatchException {

        Optional<User> userCredientials=  findbyemail(email);
         PasswordValidation(Password,userCredientials.get().getPassword());


      TokenResponse tokenResponse = tokenService.issueTokens(userCredientials.get());

      Token newtoken = new Token();
      newtoken.setAccesstoken(tokenResponse.getAccesstoken());
      newtoken.setUser_details(userCredientials.get());
      newtoken.setExpriy_time(tokenResponse.getExpirydate());
      newtoken.setRefreshtoken(tokenResponse.getRefreshToken());
      newtoken.setAccessType(tokenResponse.getAccessType());

      return  newtoken;




    }

    public Optional<User> findbyemail(String Email) throws LoginCredientialsmismatchException {
        Optional<User> Existinguser = userRepository.findByEmail(Email);

        if (Existinguser.isEmpty()){
            throw new LoginCredientialsmismatchException("Invalid Email or Password");
        }

        return  Existinguser;

    }

    public  void PasswordValidation(String Rawpassword,String encodedpassword ) throws LoginCredientialsmismatchException {

       if (! passwordEncoder.matches(Rawpassword,encodedpassword)){
           throw new LoginCredientialsmismatchException("Invalid Email or Password");
       }

    }



}
