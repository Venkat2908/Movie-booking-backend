package com.venkat.bookmyshowapplication.User.Repository;

import com.venkat.bookmyshowapplication.User.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
