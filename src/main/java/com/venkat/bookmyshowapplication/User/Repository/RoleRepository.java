package com.venkat.bookmyshowapplication.User.Repository;

import com.venkat.bookmyshowapplication.User.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
