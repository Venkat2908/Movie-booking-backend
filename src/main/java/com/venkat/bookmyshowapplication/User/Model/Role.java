package com.venkat.bookmyshowapplication.User.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Role extends  BaseModel {
    @Enumerated(EnumType.STRING)
    private RoleName name;
    @ManyToMany
    Set<Permission> permissions;
}
