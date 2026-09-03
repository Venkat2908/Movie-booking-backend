package com.venkat.bookmyshowapplication.auth.model;

import com.venkat.bookmyshowapplication.User.Model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(
                        name = "idx_refresh_token_hash",
                        columnList = "token_hash",
                        unique = true
                ),
                @Index(
                        name = "idx_refresh_token_user",
                        columnList = "user_id"
                )
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_hash", nullable = false, unique = true, length = 64)
    private String tokenHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    public RefreshToken() {
    }

    public RefreshToken(
            String tokenHash,
            User user,
            Instant createdAt,
            Instant expiresAt
    ) {
        this.tokenHash = tokenHash;
        this.user = user;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired(Instant currentTime) {
        return expiresAt.isBefore(currentTime)
                || expiresAt.equals(currentTime);
    }

    public boolean isRevoked() {
        return revokedAt != null;
    }

    public void revoke(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }
}
