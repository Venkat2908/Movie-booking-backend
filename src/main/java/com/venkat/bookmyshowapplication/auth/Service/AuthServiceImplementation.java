package com.venkat.bookmyshowapplication.auth.Service;


import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import com.venkat.bookmyshowapplication.auth.Security.TokenService;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
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
    public TokenResponse authenticate (String email, String rawPassword) throws InvalidCredentialsException {

        Optional<User> userCredentials=  findByEmail(email);
        validatePassword(rawPassword,userCredentials.get().getPassword());

      TokenResponse tokenResponse = tokenService.issueTokens(userCredentials.get());

      return  tokenResponse;




    }

    public Optional<User> findByEmail(String Email) throws InvalidCredentialsException {
        Optional<User> Existinguser = userRepository.findByEmail(Email);

        if (Existinguser.isEmpty()){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

        return  Existinguser;

    }

    public  void validatePassword(String Rawpassword,String encodedpassword ) throws InvalidCredentialsException {

       if (! passwordEncoder.matches(Rawpassword,encodedpassword)){
           throw new InvalidCredentialsException("Invalid Email or Password");
       }

    }



}
