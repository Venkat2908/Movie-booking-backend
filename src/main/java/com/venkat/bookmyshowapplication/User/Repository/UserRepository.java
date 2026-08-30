package com.venkat.bookmyshowapplication.User.Repository;

import com.venkat.bookmyshowapplication.User.Model.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.invoke.LambdaConversionException;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);



}
