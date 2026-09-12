package com.venkat.bookmyshowapplication.auth.Service;


import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidCredentialsException;
import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidRefreshTokenException;
import com.venkat.bookmyshowapplication.User.Dto.TokenGenerateDto;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Repository.UserRepository;
import com.venkat.bookmyshowapplication.auth.Security.TokenService;
import com.venkat.bookmyshowapplication.auth.model.RefreshToken;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import com.venkat.bookmyshowapplication.auth.repository.RefreshTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
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

        TokenGenerateDto tokenGenerateDto = new TokenGenerateDto();
        tokenGenerateDto.setUser(userCredentials.get());

      TokenResponse tokenResponse = tokenService.issueTokens(tokenGenerateDto);

      return  tokenResponse;


    }



    @Override
    @Transactional
    public TokenResponse refreshTokens(String refreshtokenvalue) throws InvalidRefreshTokenException {



        Optional <RefreshToken> refreshToken = refreshTokenRepository.findByTokenHash(hash(refreshtokenvalue));

        if (refreshToken.isEmpty()){
            throw new InvalidRefreshTokenException("Invalid Refresh Token");
        }

        if (refreshToken.get().getExpiresAt().isBefore(Instant.now()) ||
                refreshToken.get().getExpiresAt().equals(Instant.now() ))
        {
            throw  new InvalidRefreshTokenException("Refresh Token is Expired");

        }

        if (refreshToken.get().isRevoked()){
            throw new InvalidRefreshTokenException("Refresh Token is already revoked");
        }

        tokenService.revoketoken(refreshToken.get());

        TokenGenerateDto tokenGenerateDto = new TokenGenerateDto();
       tokenGenerateDto.setUser(refreshToken.get().getUser());
       tokenGenerateDto.setSubsequent(true);

       tokenGenerateDto.setExpiresAt(refreshToken.get().getExpiresAt());
        TokenResponse tokenResponse = tokenService.issueTokens(tokenGenerateDto);

        return  tokenResponse;


    }

    @Transactional
    @Override
    public void logout(String refreshToken) {
        Optional <RefreshToken> logout_token = refreshTokenRepository.findByTokenHash(hash(refreshToken));
        if (logout_token.isEmpty()){
            throw new InvalidRefreshTokenException("Invalid Refresh Token");
        }

        tokenService.revoketoken(logout_token.get());

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
