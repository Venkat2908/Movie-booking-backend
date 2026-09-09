package com.venkat.bookmyshowapplication.User.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role extends  BaseModel {
    @Enumerated(EnumType.STRING)
    @Column(unique = true,nullable = false)
    private RoleName name;
    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(
                    name ="role_permissions",
                    joinColumns = @JoinColumn(name ="role_id"),
                    inverseJoinColumns = @JoinColumn(name = "permission_id")
            )
    Set<Permission> permissions = new HashSet<>();
}
