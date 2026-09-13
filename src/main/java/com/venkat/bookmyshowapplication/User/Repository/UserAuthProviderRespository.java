package com.venkat.bookmyshowapplication.User.Repository;

import com.venkat.bookmyshowapplication.User.Model.Authprovider;
import com.venkat.bookmyshowapplication.User.Model.User;
import com.venkat.bookmyshowapplication.User.Model.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthProviderRespository extends JpaRepository<UserAuthProvider, Long> {
    Optional<UserAuthProvider> findByProviderAndProviderUserId(
            Authprovider provider,
            String providerUserId
    );

    boolean existsByUserAndProvider(
            User user,
            Authprovider provider
    );

    Optional<UserAuthProvider> findByUserAndProvider(
            User user,
            Authprovider provider
    );



}
