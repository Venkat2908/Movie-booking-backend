package com.venkat.bookmyshowapplication.Auth.Security;

import com.venkat.bookmyshowapplication.User.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final String issuer;
    private final Duration accessTokenExpiration;
    private final Clock clock;

    public JwtService(
            @Value("${security.jwt.secret}") String base64Secret,
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.access-token-expiration}") Duration accessTokenExpiration
    ) {
        byte[] decodedSecret = Decoders.BASE64.decode(base64Secret);

        if (decodedSecret.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must contain at least 32 bytes"
            );
        }

        this.signingKey = Keys.hmacShaKeyFor(decodedSecret);
        this.issuer = issuer;
        this.accessTokenExpiration = accessTokenExpiration;
        this.clock = Clock.systemUTC();
    }

    public String generateAccessToken(User user) {
        Instant issuedAt = clock.instant();
        Instant expiresAt = issuedAt.plus(accessTokenExpiration);

        return Jwts.builder()
                .issuer(issuer)
                .subject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .id(UUID.randomUUID().toString())
                .signWith(signingKey)
                .compact();
    }

    public Claims validateAndExtractClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public Long extractUserId(String accessToken) {
        String subject = validateAndExtractClaims(accessToken).getSubject();
        return Long.valueOf(subject);
    }

    public Duration getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}
