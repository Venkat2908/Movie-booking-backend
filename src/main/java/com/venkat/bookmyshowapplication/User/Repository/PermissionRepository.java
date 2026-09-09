package com.venkat.bookmyshowapplication.User.Repository;

import com.venkat.bookmyshowapplication.User.Model.Permission;
import com.venkat.bookmyshowapplication.User.Model.PermissionName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(PermissionName name);
}
