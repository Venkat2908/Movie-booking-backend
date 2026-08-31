package com.venkat.bookmyshowapplication.Auth.Service;


import com.venkat.bookmyshowapplication.Auth.Repository.AuthRepository;
import com.venkat.bookmyshowapplication.Common.Exceptions.LoginCredientialsmismatchException;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements  AuthService {

    private final PasswordEncoder passwordEncoder;

    private AuthRepository authRepository;
    private UserRepository userRepository;

    public AuthServiceImplementation(AuthRepository authRepository,PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Override
    public ResponseEntity<String> LoginValidation(String email, String Password) throws LoginCredientialsmismatchException {

        try {
            Optional<User> userCredientials=  findbyemail(email);
            PasswordValidation(Password,userCredientials.get().getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Login Successful");
        }
        catch (LoginCredientialsmismatchException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

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
