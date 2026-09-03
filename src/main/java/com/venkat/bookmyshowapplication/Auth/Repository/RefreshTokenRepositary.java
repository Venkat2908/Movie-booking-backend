package com.venkat.bookmyshowapplication.Auth.Repository;

import com.venkat.bookmyshowapplication.Auth.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepositary extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
}
