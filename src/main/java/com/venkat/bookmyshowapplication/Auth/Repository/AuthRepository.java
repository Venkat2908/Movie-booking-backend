package com.venkat.bookmyshowapplication.Auth.Repository;

import com.venkat.bookmyshowapplication.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,Long> {


}
