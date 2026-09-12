package com.venkat.bookmyshowapplication.auth.Security;


import com.venkat.bookmyshowapplication.Common.Exceptions.InvalidRefreshTokenException;
import com.venkat.bookmyshowapplication.User.Dto.TokenGenerateDto;
import com.venkat.bookmyshowapplication.auth.model.RefreshToken;
import com.venkat.bookmyshowapplication.auth.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class RefreshTokenService {

    private static final int TOKEN_SIZE_BYTES = 32;

    private final RefreshTokenRepository refreshTokenRepository;
    private final Duration refreshTokenExpiration;
    private final SecureRandom secureRandom;
    private final Clock clock;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${security.jwt.refresh-token-expiration}")
            Duration refreshTokenExpiration
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.secureRandom = new SecureRandom();
        this.clock = Clock.systemUTC();
    }

    public String generateAndStore(TokenGenerateDto tokenGenerateDto) {
        String rawToken = generateSecureToken();
        String tokenHash = hash(rawToken);

        Instant createdAt = clock.instant();
        Instant expiresAt;

        if (!tokenGenerateDto.isSubsequent()){
             expiresAt = createdAt.plus(refreshTokenExpiration);
        }else {
             expiresAt = tokenGenerateDto.getExpiresAt();
        }


        RefreshToken refreshToken = new RefreshToken(
                tokenHash,
                tokenGenerateDto.getUser(),
                createdAt,
                expiresAt
        );

        refreshTokenRepository.save(refreshToken);

        // The raw token is returned only once.
        return rawToken;
    }

    public RefreshToken validate(String rawToken) {
        String tokenHash = hash(rawToken);

        RefreshToken storedToken = refreshTokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException(
                                "Invalid refresh token"
                        )
                );

        Instant currentTime = clock.instant();

        if (storedToken.isRevoked() || storedToken.isExpired(currentTime)) {
            throw new InvalidRefreshTokenException(
                    "Invalid refresh token"
            );
        }

        return storedToken;
    }

    public void revoke(RefreshToken refreshToken) {
        refreshToken.revoke(clock.instant());
        refreshTokenRepository.save(refreshToken);
    }

    private String generateSecureToken() {
        byte[] randomBytes = new byte[TOKEN_SIZE_BYTES];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    private String hash(String rawToken) {
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