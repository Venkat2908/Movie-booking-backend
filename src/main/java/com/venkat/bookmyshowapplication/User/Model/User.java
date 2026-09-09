package com.venkat.bookmyshowapplication.User.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name ="Users")
public class User extends BaseModel {
    private String name;
    @Email
    @Column(nullable = false,unique = true)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserResponseStatus status;
    private boolean verified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
