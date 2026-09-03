package com.venkat.bookmyshowapplication.auth.Security;


import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
import com.venkat.bookmyshowapplication.User.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public TokenService(
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public TokenResponse issueTokens(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.generateAndStore(user);

        return new TokenResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpiration().toSeconds()
        );
    }
}