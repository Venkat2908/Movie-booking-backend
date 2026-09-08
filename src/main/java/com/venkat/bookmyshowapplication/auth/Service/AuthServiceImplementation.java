package com.venkat.bookmyshowapplication.auth.Service;


import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidRefreshTokenException;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import com.venkat.bookmyshowapplication.auth.Security.TokenService;
import com.venkat.bookmyshowapplication.auth.model.RefreshToken;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import com.venkat.bookmyshowapplication.auth.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class AuthServiceImplementation implements  AuthService {

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    public AuthServiceImplementation(PasswordEncoder passwordEncoder, TokenService tokenService, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }



    @Override
    public TokenResponse authenticate (String email, String rawPassword) throws InvalidCredentialsException {

        Optional<User> userCredentials=  findByEmail(email);
        validatePassword(rawPassword,userCredentials.get().getPassword());

      TokenResponse tokenResponse = tokenService.issueTokens(userCredentials.get());

      return  tokenResponse;




    }

    @Override
    public TokenResponse refreshTokens(String refreshtokenvalue) throws InvalidRefreshTokenException {


        Optional <RefreshToken> refreshToken = refreshTokenRepository.findByTokenHash(hash(refreshtokenvalue));

        if (refreshToken.isEmpty()){
            throw new InvalidRefreshTokenException("Invalid Refresh Token");
        }


        if (refreshToken.get().isRevoked()){
            throw new InvalidRefreshTokenException("Refresh Token is already revoked");
        }

        tokenService.revoketoken(refreshToken.get());

        TokenResponse tokenResponse = tokenService.issueTokens(refreshToken.get().getUser());

        return  tokenResponse;






    }

    @Override
    public HttpStatus logout(String refreshToken) {
        Optional <RefreshToken> logout_token = refreshTokenRepository.findByTokenHash(hash(refreshToken));
        if (logout_token.isEmpty()){
            throw new InvalidRefreshTokenException("Invalid Refresh Token");
        }

        tokenService.revoketoken(logout_token.get());

        return HttpStatus.NO_CONTENT;



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
    private  String hash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hashedBytes);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable",
                    exception
            );
        }
    }



}
