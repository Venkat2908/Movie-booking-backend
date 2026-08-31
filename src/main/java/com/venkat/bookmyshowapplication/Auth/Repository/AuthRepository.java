package com.venkat.bookmyshowapplication.Auth.Repository;

import com.venkat.bookmyshowapplication.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
