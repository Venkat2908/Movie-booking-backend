package com.venkat.bookmyshowapplication.auth.Service;


import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import com.venkat.bookmyshowapplication.auth.Security.TokenService;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements  AuthService {

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AuthServiceImplementation(PasswordEncoder passwordEncoder, TokenService tokenService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public TokenResponse authenticate (String email, String rawPassword) throws LoginCredientialsmismatchException {

        Optional<User> userCredentials=  findByEmail(email);
        validatePassword(rawPassword,userCredentials.get().getPassword());


      TokenResponse tokenResponse = tokenService.issueTokens(userCredentials.get());
      tokenResponse.setUser_details(userCredentials.get());



      return  tokenResponse;




    }

    public Optional<User> findByEmail(String Email) throws LoginCredientialsmismatchException {
        Optional<User> Existinguser = userRepository.findByEmail(Email);

        if (Existinguser.isEmpty()){
            throw new LoginCredientialsmismatchException("Invalid Email or Password");
        }

        return  Existinguser;

    }

    public  void validatePassword(String Rawpassword,String encodedpassword ) throws LoginCredientialsmismatchException {

       if (! passwordEncoder.matches(Rawpassword,encodedpassword)){
           throw new LoginCredientialsmismatchException("Invalid Email or Password");
       }

    }



}
