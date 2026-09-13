package com.venkat.bookmyshowapplication.User.Repository;

import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Model.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.AuthProvider;
import java.util.Optional;

public interface UserAuthProviderRespository extends JpaRepository<UserAuthProvider, Long> {
    Optional<UserAuthProvider> findByProviderAndProviderUserId(
            AuthProvider provider,
            String providerUserId
    );

    boolean existsByUserAndProvider(
            User user,
            AuthProvider provider
    );

    Optional<UserAuthProvider> findByUserAndProvider(
            User user,
            AuthProvider provider
    );



}
