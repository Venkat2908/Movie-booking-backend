package com.venkat.bookmyshowapplication.auth.Security;


import com.venkat.bookmyshowapplication.User.Dto.TokenGenerateDto;
import com.venkat.bookmyshowapplication.auth.model.RefreshToken;
import com.venkat.bookmyshowapplication.auth.model.TokenResponse;
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
    public TokenResponse issueTokens(TokenGenerateDto tokenGenerateDto) {
        String accessToken = jwtService.generateAccessToken(tokenGenerateDto.getUser());
        String refreshToken = refreshTokenService.generateAndStore(tokenGenerateDto);

        return new TokenResponse(
                tokenGenerateDto.getUser().getEmail(),
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpiration().toSeconds()
        );
    }

    public void revoketoken(RefreshToken refreshToken){
          refreshTokenService.revoke(refreshToken);
    }
}