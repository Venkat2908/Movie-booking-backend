package com.venkat.bookmyshowapplication.User.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role extends  BaseModel {
    @Enumerated(EnumType.STRING)
    private RoleName name;
    @ManyToMany
            @JoinTable(
                    name ="role_permissions",
                    joinColumns = @JoinColumn(name ="role_id"),
                    inverseJoinColumns = @JoinColumn(name = "permission_id")
            )
    @Column(unique = true)
    @NotNull
    Set<Permission> permissions = new HashSet<>();
}
