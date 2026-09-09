package com.venkat.bookmyshowapplication.Config;

import com.venkat.bookmyshowapplication.User.Model.Permission;
import com.venkat.bookmyshowapplication.User.Model.PermissionName;
import com.venkat.bookmyshowapplication.User.Model.Role;
import com.venkat.bookmyshowapplication.User.Model.RoleName;
import com.venkat.bookmyshowapplication.User.Repository.PermissionRepository;
import com.venkat.bookmyshowapplication.User.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedRolesAndPermissions(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {
        return args -> {
            Permission movieRead = permissionRepository
                    .findByName(PermissionName.MOVIE_READ)
                    .orElseGet(() -> {
                        Permission permission = new Permission();
                        permission.setName(PermissionName.MOVIE_READ);
                        return permissionRepository.save(permission);
                    });

            Permission bookingCreate = permissionRepository
                    .findByName(PermissionName.BOOKING_CREATE)
                    .orElseGet(() -> {
                        Permission permission = new Permission();
                        permission.setName(PermissionName.BOOKING_CREATE);
                        return permissionRepository.save(permission);
                    });
            roleRepository
                    .findByName(RoleName.USER)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(RoleName.USER);

                        Set<Permission> permissions = new HashSet<>();
                        permissions.add(movieRead);
                        permissions.add(bookingCreate);

                        role.setPermissions(permissions);

                        return roleRepository.save(role);
                    });
            Permission showCreate = permissionRepository
                    .findByName(PermissionName.SHOW_CREATE)
                    .orElseGet(() -> {
                        Permission permission = new Permission();
                        permission.setName(PermissionName.SHOW_CREATE);
                        return permissionRepository.save(permission);
                    });

            Permission showUpdate = permissionRepository
                    .findByName(PermissionName.SHOW_UPDATE)
                    .orElseGet(() -> {
                        Permission permission = new Permission();
                        permission.setName(PermissionName.SHOW_UPDATE);
                        return permissionRepository.save(permission);
                    });

            Permission userManage = permissionRepository
                    .findByName(PermissionName.USER_MANAGE)
                    .orElseGet(() -> {
                        Permission permission = new Permission();
                        permission.setName(PermissionName.USER_MANAGE);
                        return permissionRepository.save(permission);
                    });
            roleRepository
                    .findByName(RoleName.THEATRE_ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(RoleName.THEATRE_ADMIN);

                        Set<Permission> permissions = new HashSet<>();
                        permissions.add(movieRead);
                        permissions.add(showCreate);
                        permissions.add(showUpdate);

                        role.setPermissions(permissions);

                        return roleRepository.save(role);
                    });
            roleRepository
                    .findByName(RoleName.ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(RoleName.ADMIN);

                        Set<Permission> permissions = new HashSet<>();
                        permissions.add(movieRead);
                        permissions.add(bookingCreate);
                        permissions.add(showCreate);
                        permissions.add(showUpdate);
                        permissions.add(userManage);

                        role.setPermissions(permissions);
                        return roleRepository.save(role);
                    });

            // 1. create/find permissions

            // 2. create/find roles

            // 3. attach permissions to roles

        };

    }
}
